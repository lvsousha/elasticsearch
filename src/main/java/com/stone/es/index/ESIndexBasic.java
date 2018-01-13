package com.stone.es.index;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.alibaba.fastjson.JSONObject;
import com.stone.es.mapping.AbstractMapping;
import com.stone.es.mapping.Mapping;

public class ESIndexBasic implements ESIndex{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Boolean createIndex(Client client, String index, String type, List<Mapping> mappings) throws Exception {
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CreateIndexResponse response = indicesAdminClient
											.prepareCreate(index) 
					        				.addMapping(type, getMappings(type, mappings))
					        				.get();
		log.info("创建索引名为："+index+",类型名为："+type+"："+response.isAcknowledged());
		return response.isAcknowledged();
	}
	
	private String getMappings(String typeName, List<Mapping> mappings) throws Exception{
		XContentBuilder  builder  =  XContentFactory.jsonBuilder();
		builder.startObject().startObject(typeName).startObject("properties");
		for(Mapping mapping : mappings){
			mapping.string(builder);
		}
		builder.endObject().endObject().endObject();
		log.info(builder.prettyPrint().string());
		return builder.string();
	}
	
	@Override
	public Boolean deleteIndices(Client client, String... indices) {
		for(String index : indices){
			IndicesExistsResponse ier = client.admin().indices().prepareExists(index).get();
			if(ier.isExists()){
				DeleteIndexResponse response = client.admin().indices().prepareDelete(index).get();
				log.info("删除索引名为： "+index+" ： "+response.isAcknowledged());
			}else{
				log.info("索引名为："+index+" 不存在");
			}
			
		}
		return true;
	}

	@Override
	public Boolean createAlias(Client client, String index, String alias) {
		IndicesAliasesRequest iarb = new IndicesAliasesRequest();
		IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(AliasAction.Type.ADD,index,alias);
		iarb.addAliasAction(aliasAction);
		IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
		log.info(iar.isAcknowledged());
		return iar.isAcknowledged();
	}

	@Override
	public Boolean deleteAlias(Client client, String index, String alias) {
		IndicesAliasesRequest iarb = new IndicesAliasesRequest();
		IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(AliasAction.Type.REMOVE,index,alias);
		iarb.addAliasAction(aliasAction);
		IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
		log.info(iar.isAcknowledged());
		return iar.isAcknowledged();
	}

	@Override
	public JSONObject getIndexStatus(Client client, String index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getIndexMetadata(Client client, String index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean appendMapping(Client client, String index, String type, List<Mapping> append) throws Exception {
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		PutMappingResponse response = indicesAdminClient.preparePutMapping(index)
				.setType(type)
				.setSource(getMappings(type, append))
				.get();
		log.info("添加映射："+response.isAcknowledged());
		return response.isAcknowledged();
	}

}
