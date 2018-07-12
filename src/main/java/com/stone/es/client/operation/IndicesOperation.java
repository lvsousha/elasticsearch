package com.stone.es.client.operation;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import com.stone.es.mapping.Mapping;
import lombok.Data;

/**
 * 创建索引
 * 删除索引
 * 判断索引是否存在
 * 添加映射
 * 添加别名
 * 移除别名
 * 判断别名是否存在
 * 修改配置
 * @author zhengchanglin
 *
 */
public class IndicesOperation {

  private Logger log = Logger.getRootLogger();

  /**
   * 创建索引
   * 
   * @param client RestHighLevelClient
   * @param model model.index,model.mappings
   * @return
   * @throws Exception
   */
  public Boolean createIndex(RestHighLevelClient client, IndicesModel model) throws Exception {
    XContentBuilder builder = XContentFactory.jsonBuilder();
    CreateIndexRequest request = new CreateIndexRequest(model.getIndex());
//    request.settings(
//        Settings.builder().put("index.number_of_shards", 5).put("index.number_of_replicas", 1));
    if(model.getSettings() != null){
      request.settings(model.getSettings());
    }
    if (model.getAlias() != null) {
      request.alias(new Alias(model.getAlias()));
    }
    decorateMappings(model.getType(), builder, model.getMappings());
    request.mapping(model.getType(), builder);
    CreateIndexResponse response = client.indices().create(request);
    return response.isAcknowledged();
  }

  /**
   * 删除索引
   * 
   * @param client
   * @param model   model.index
   * @throws IOException
   */
  public Boolean deleteIndex(RestHighLevelClient client, IndicesModel model) throws IOException {
    DeleteIndexRequest request = null;
    DeleteIndexResponse response = null;
    try {
      request = new DeleteIndexRequest(model.getIndex());
      response = client.indices().delete(request);
    } catch (ElasticsearchException exception) {
      if (exception.status() == RestStatus.NOT_FOUND) {
        log.error("索引不存在，删除失败");
      }
      return false;
    }
    return response.isAcknowledged();
  }

  /**
   * 判断索引是否存在
   * 
   * @param client
   * @param model   model.index
   * @return
   * @throws IOException
   */
  public Boolean existsIndex(RestHighLevelClient client, IndicesModel model) throws IOException {
    GetIndexRequest request = new GetIndexRequest();
    request.indices(model.getIndex());
    return client.indices().exists(request);
  }

  /**
   * 添加或映射（只能添加，我发修改）
   * 
   * @param client
   * @param model   model.index,model.mappings
   * @return
   * @throws Exception
   */
  public Boolean putMapping(RestHighLevelClient client, IndicesModel model) throws Exception {
    XContentBuilder builder = XContentFactory.jsonBuilder();
    PutMappingRequest request = new PutMappingRequest(model.getIndex());
    request.type(model.getType());
    decorateMappings(model.getType(), builder, model.getMappings());
    PutMappingResponse response = client.indices().putMapping(request);
    Boolean acknowledged = response.isAcknowledged();
    return acknowledged;
  }

  /**
   * 添加别名
   * @param client
   * @param model       model.index,model.alias
   * @throws IOException 
   */
  public Boolean addAlias(RestHighLevelClient client, IndicesModel model) throws IOException {
    IndicesAliasesRequest request = new IndicesAliasesRequest();
    AliasActions aliasAction =
        new AliasActions(AliasActions.Type.ADD).index(model.getIndex()).alias(model.getAlias());
    request.addAliasAction(aliasAction);
    IndicesAliasesResponse response = client.indices().updateAliases(request);
    Boolean acknowledged = response.isAcknowledged();
    return acknowledged;
  }

  /**
   * 移除别名
   * @param client
   * @param model   model.index,model.alias
   * @throws IOException 
   */
  public Boolean removeAlias(RestHighLevelClient client, IndicesModel model) throws IOException {
    IndicesAliasesRequest request = new IndicesAliasesRequest();
    AliasActions aliasAction =
        new AliasActions(AliasActions.Type.REMOVE).index(model.getIndex()).alias(model.getAlias());
    request.addAliasAction(aliasAction);
    IndicesAliasesResponse response = client.indices().updateAliases(request);
    Boolean acknowledged = response.isAcknowledged();
    return acknowledged;
  }
  
  /**
   * 判断别名是否存在
   * @param client
   * @param model   model.alias
   * @return
   * @throws IOException
   */
  public Boolean existsAlias(RestHighLevelClient client, IndicesModel model) throws IOException{
    GetAliasesRequest request = new GetAliasesRequest(model.getAlias());
    Boolean exists = client.indices().existsAlias(request);
    return exists;
  }
  
  /**
   * 更新配置
   * @param client
   * @param model   model.settings,model.index
   * @return
   * @throws IOException
   */
  public Boolean updateSettings(RestHighLevelClient client, IndicesModel model) throws IOException{
    UpdateSettingsRequest request = new UpdateSettingsRequest(model.getIndex());
    request.settings(model.getSettings());
    UpdateSettingsResponse response = client.indices().putSettings(request);
    Boolean acknowledged = response.isAcknowledged();
    return acknowledged;
  }
  
  public void decorateMappings(String type, XContentBuilder builder, List<Mapping> mappings)
      throws Exception {
    startMapping(type, builder);
    for (Mapping mapping : mappings) {
      mapping.string(builder);
    }
    endMapping(type, builder);
  }

  private void startMapping(String type, XContentBuilder builder) throws Exception {
    builder.startObject().startObject(type).startObject("properties");
  }

  private void endMapping(String type, XContentBuilder builder) throws Exception {
    builder.endObject().endObject().endObject();
  }

  @Data
  public static class IndicesModel {
    private String index;
    private String type = "doc";
    private String alias;
    private List<Mapping> mappings;
    private Settings settings;

    IndicesModel(String index) {
      this.index = index;
    }

    public IndicesModel(String index, String alias) {
      this.index = index;
      this.alias = alias;
    }

    @Deprecated
    public void setType(String type) {
      this.type = type;
    }
  }


}
