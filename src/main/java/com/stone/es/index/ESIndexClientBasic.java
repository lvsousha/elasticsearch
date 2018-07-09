package com.stone.es.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.stone.es.mapping.Mapping;
import com.stone.es.model.ESIndex;
import com.stone.es.model.ESIndexMetadata;
import com.stone.es.model.ESIndexStatus;

public class ESIndexClientBasic implements ESIndexClient {

  private Logger log = Logger.getLogger(this.getClass());

  @Override
  public Boolean createIndex(Client client, String index, String type, List<Mapping> mappings)
      throws Exception {
    if (isExist(client, index)) {
      log.info("创建索引名为：" + index + "失败，索引已经存在");
      return false;
    }
    IndicesAdminClient indicesAdminClient = client.admin().indices();
    CreateIndexResponse response = indicesAdminClient.prepareCreate(index)
        .addMapping(type, buildMappings(type, mappings)).get();
    log.info("创建索引名为：" + index + ",类型名为：" + type + "：" + response.isAcknowledged());
    return response.isAcknowledged();
  }

  private String buildMappings(String typeName, List<Mapping> mappings) throws Exception {
    XContentBuilder builder = XContentFactory.jsonBuilder();
    builder.startObject().startObject(typeName).startObject("properties");
    for (Mapping mapping : mappings) {
      mapping.string(builder);
    }
    builder.endObject().endObject().endObject();
    log.info(builder.prettyPrint().toString());
    return builder.toString();
  }

  @Override
  public Boolean deleteIndices(Client client, String... indices) {
    for (String index : indices) {
      if (isExist(client, index)) {
        DeleteIndexResponse response = client.admin().indices().prepareDelete(index).get();
        log.info("删除索引名为： " + index + " ： " + response.isAcknowledged());
        continue;
      }
      log.info("索引名为：" + index + " 不存在");
    }
    return true;
  }

  @Override
  public Boolean createAlias(Client client, String index, String alias) {
    IndicesAliasesRequest iarb = new IndicesAliasesRequest();
    IndicesAliasesRequest.AliasActions aliasAction =
        new IndicesAliasesRequest.AliasActions(AliasActions.Type.ADD);
    aliasAction.index(index).alias(alias);
    iarb.addAliasAction(aliasAction);
    IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
    log.info(iar.isAcknowledged());
    return iar.isAcknowledged();
  }

  @Override
  public Boolean deleteAlias(Client client, String index, String alias) {
    IndicesAliasesRequest iarb = new IndicesAliasesRequest();
    IndicesAliasesRequest.AliasActions aliasAction =
        new IndicesAliasesRequest.AliasActions(AliasActions.Type.REMOVE);
    aliasAction.index(index).alias(alias);
    iarb.addAliasAction(aliasAction);
    IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
    log.info(iar.isAcknowledged());
    return iar.isAcknowledged();
  }

  @Override
  public List<ESIndexStatus> getIndexStatus(Client client, String... indices) {
    List<String> indicesList = Arrays.asList(indices);
    IndicesAdminClient iac = client.admin().indices();
    List<ESIndexStatus> list = new ArrayList<>();
    IndicesStatsResponse isr = iac.prepareStats().get();
    // log.info(isr.toString());
    for (IndexStats indexStats : isr.getIndices().values()) {
      if (indices != null && indices.length > 0 && !indicesList.contains(indexStats.getIndex())) {
        continue;
      }
      ESIndexStatus model = new ESIndexStatus();
      model.setIndex(indexStats.getIndex());
      model.setDocs(indexStats.getTotal().getDocs().getCount());
      model.setSize(indexStats.getTotal().getStore().getSize().getMb());
      log.info("文件数量：" + indexStats.getTotal().getDocs().getCount());
      list.add(model);
    }
    return list;
  }

  @Override
  public List<ESIndexMetadata> getIndexMetadata(Client client, String... indices) throws Exception {
    List<String> indicesList = Arrays.asList(indices);
    List<ESIndexMetadata> list = new ArrayList<>();
    ClusterAdminClient cac = client.admin().cluster();
    ClusterStateResponse csr = cac.prepareState().get();
    MetaData metaData = csr.getState().getMetaData();
    // log.info(csr.getState().toString());
    ImmutableOpenMap<String, IndexMetaData> iom = metaData.getIndices();
    for (ObjectCursor<String> key : iom.keys()) {
      if (indices != null && indices.length > 0 && !indicesList.contains(key.value)) {
        continue;
      }
      ESIndexMetadata model = new ESIndexMetadata();
      IndexMetaData indexMetaData = iom.get(key.value);
      model.setIndex(key.value);
      model.setMappings(getMappings(indexMetaData.getMappings().iterator()));
      model.setAlias(getAliases(indexMetaData.getAliases().iterator()));
      model.setSettings(indexMetaData.getSettings());
      list.add(model);
    }

    return list;
  }

  private List<AliasMetaData> getAliases(
      Iterator<ObjectObjectCursor<String, AliasMetaData>> iooca) {
    List<AliasMetaData> list = new ArrayList<>();
    while (iooca.hasNext()) {
      ObjectObjectCursor<String, AliasMetaData> ooca = iooca.next();
      AliasMetaData amd = ooca.value.get();
      log.info(ooca.key);
      log.info("别名：" + amd.toString());
      list.add(amd);
    }
    return list;
  }

  private List<MappingMetaData> getMappings(
      Iterator<ObjectObjectCursor<String, MappingMetaData>> ioocm) throws Exception {
    List<MappingMetaData> list = new ArrayList<>();
    while (ioocm.hasNext()) {
      ObjectObjectCursor<String, MappingMetaData> oocm = ioocm.next();
      MappingMetaData mmd = oocm.value.get();
      log.info("映射:" + mmd.source().string());
      list.add(mmd);
    }
    return list;
  }

  @Override
  public Boolean appendMapping(Client client, String index, String type, List<Mapping> append)
      throws Exception {
    IndicesAdminClient indicesAdminClient = client.admin().indices();
    PutMappingResponse response = indicesAdminClient.preparePutMapping(index).setType(type)
        .setSource(buildMappings(type, append)).get();
    log.info("添加映射：" + response.isAcknowledged());
    return response.isAcknowledged();
  }

  public List<ESIndex> getIndices(Client client, String... indices) throws Exception {
    List<ESIndex> list = new ArrayList<>();
    List<ESIndexStatus> status = getIndexStatus(client, indices);
    List<ESIndexMetadata> metadatas = getIndexMetadata(client, indices);
    for (ESIndexStatus statu : status) {
      ESIndex model = new ESIndex();
      model.setIndex(statu.getIndex());
      model.setStatus(statu);
      for (ESIndexMetadata metadata : metadatas) {
        if (metadata.getIndex().equals(statu.getIndex())) {
          model.setMetadata(metadata);
          break;
        }
      }
      list.add(model);
    }
    return list;
  }

  private Boolean isExist(Client client, String index) {
    IndicesExistsResponse ier = client.admin().indices().prepareExists(index).get();
    return ier.isExists();
  }

}
