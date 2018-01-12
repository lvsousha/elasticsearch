package com.stone.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.elasticsearch.cluster.metadata.AliasOrIndex;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.stone.es.model.ESIndex;
import com.stone.es.model.Mapping;

/**
 * 创建Index，添加Mapping，获取和修改Setting，获取集群信息
 * @author zhengchanglin
 *
 */
public class ESAdminClient {

	private Logger log = Logger.getRootLogger();

	public static void main(String[] args) throws Exception {
		ESAdminClient esdc = new ESAdminClient();
//		Client client = ESClient.createClientBySetting();
		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.248.3:9500");
//		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.247.180:9300");
		String index = "history-v2";
		String type = "history-v2";
		String alias = "history-v";
		esdc.deleteAlias(client, index, alias);
//		esdc.deleteIndices(client,"history-v2");
//		List<Mapping> mappings = new ArrayList<>();
//		Mapping createDate = new Mapping();
//		Mapping keyword = new Mapping();
//		Mapping user = new Mapping();
//		Mapping searchType = new Mapping();
//		createDate.setName("createDate");createDate.setType("date");
//		keyword.setName("keyword");keyword.setType("string");keyword.setFields(true);
//		searchType.setName("type");searchType.setType("string");searchType.setFields(true);
//		user.setName("user");user.setType("long");
//		mappings.add(createDate);mappings.add(keyword);mappings.add(searchType);mappings.add(user);
//		XContentBuilder  xcb = esdc.createMappingsByXCB(type, mappings);
//		esdc.createIndex(client, index, type, xcb.string());
//		esdc.getHealth(client);
//		esdc.getIndices(client);
//		esdc.createAlias(client, index, alias);
//		esdc.deleteAlias(client, index, alias);

		client.close();
	}
	
	/**
	 * 获取索引信息，包括索引名，文件数量，占用空间，映射（Mapping），配置（settings）
	 * @param client
	 * @return
	 * @throws IOException
	 */
	public List<ESIndex> getIndices(Client client) throws IOException{
		IndicesAdminClient iac = client.admin().indices();
		GetIndexResponse response = iac.prepareGetIndex().get();
		String[] indices = response.getIndices();
		List<ESIndex> list = new ArrayList<>();
		for(String index : indices){
			ESIndex obj = new ESIndex();
			IndicesStatsResponse isr = iac.prepareStats(index).get();
			log.info(isr.toString());
			CommonStats cs = isr.getTotal();
			obj.setIndex(index);
			obj.setDocs(String.valueOf(cs.getDocs().getCount()));
			obj.setSize(cs.getStore().getSize().getMb()+"mb");
			log.info("索引名："+index);
//			log.info(isr.toString());
//			log.info("文件数量："+cs.getDocs().getCount());
//			log.info("占用空间:"+cs.getStore().getSize().getMb()+"mb");
			
			GetMappingsResponse gmr = iac.prepareGetMappings(index).get();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> ioocip = gmr.mappings().iterator();
			while(ioocip.hasNext()){
				JSONObject mappings = new JSONObject();
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> oocim = ioocip.next();
				Iterator<ObjectObjectCursor<String, MappingMetaData>> ioocm = oocim.value.iterator();
				while(ioocm.hasNext()){
					ObjectObjectCursor<String, MappingMetaData> oocm = ioocm.next();
					MappingMetaData mmd = oocm.value.get();
					mappings.put(oocm.key, mmd.source().string());
//					log.info("映射:"+mmd.source().string());
					
				}
				obj.setMappings(mappings);
			}
			
			GetSettingsResponse gsr = iac.prepareGetSettings(index).get();
			
			Iterator<ObjectObjectCursor<String, Settings>>  ioocs = gsr.getIndexToSettings().iterator();
			while(ioocs.hasNext()){
				JSONObject settings = new JSONObject();
				ObjectObjectCursor<String, Settings> oocs = ioocs.next();
				settings.put(oocs.key, oocs.value.getAsMap());
//				log.info(JSON.toJSON("配置:"+oocs.key+"："+oocs.value.getAsMap()));
				obj.setSettings(settings);
			}
			list.add(obj);
		}
		Set<String> headers = response.getHeaders();
		for(String header : headers){
			log.info(header);
		}
//		log.info(array);
		return list;
	}
	
	/**
	 * 创建索引
	 * @param client
	 * @param index				索引名
	 * @param type				类型名
	 * @param mappings			映射
	 * @return
	 * @throws IOException
	 */
	public JSONObject createIndex(Client client, String index, String type, String mappings) throws IOException{
		JSONObject result = new JSONObject();
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CreateIndexResponse response = indicesAdminClient.prepareCreate(index)  
				        				  .setSettings(
				        						  Settings.builder()                             
				        						  			.put("index.number_of_shards", 5)    
				        						  			.put("index.number_of_replicas", 1)
				        				  
				        				  )
				        				  .addMapping(type, mappings)
				        				  .get();
		log.info("Create Index "+index+" Is "+response.isAcknowledged());
		return result;
	}
	
	/**
	 * 若索引存在，删除索引
	 * @param client
	 * @param indices		索引
	 */
	public void deleteIndices(Client client, String... indices){
		for(String index : indices){
			IndicesExistsResponse ier = client.admin().indices().prepareExists(index).get();
			if(ier.isExists()){
				DeleteIndexResponse response = client.admin().indices().prepareDelete(index).get();
				log.info("Delete "+index+" is "+response.isAcknowledged());
			}
			
		}
	}
	
	/**
	 * 
	 * @param typeName		类型名称
	 * @param mappings
	 * @return
	 * @throws IOException
	 */
	public XContentBuilder createMappingsByXCB(String typeName, List<Mapping> mappings) throws IOException{
		XContentBuilder  builder  =  XContentFactory.jsonBuilder().startObject().startObject(typeName).startObject("properties");
		for(Mapping mapping : mappings){
			builder.startObject(mapping.getName());
			if(mapping.getType().equals("string")){
				builder.field("type", mapping.getType());
				if(mapping.getFields()){
					builder.startObject("fields")
								.startObject("raw")
									.field("type", mapping.getType())
									.field("index", "not_analyzed")
									.field("store","true")
								.endObject()
							.endObject();
				}
				builder.endObject();
			}else if(mapping.getType().equals("date")){
				builder.field("type", mapping.getType())
						.field("format", mapping.getFormat());
				builder.endObject();
			}else{
				builder.field("type", mapping.getType());
				builder.endObject();
			}
			
		}
		builder.endObject().endObject().endObject();
		log.info(builder.string());
		return builder;
	}
	
	public JSONObject createMappings(String typeName){
		
		JSONObject result = new JSONObject();
		JSONObject type = new JSONObject();
		JSONObject properties = new JSONObject();
		JSONObject name = new JSONObject();
		JSONObject sex = new JSONObject();
		JSONObject birth = new JSONObject();
		JSONObject year = new JSONObject();
		name.put("type", "string");
		name.put("index", "analyzed");
		sex.put("type", "string");
		sex.put("index", "analyzed");
		birth.put("type", "date");
		birth.put("index", "analyzed");
		birth.put("format", "strict_date_optional_time||epoch_millis");
		year.put("type", "string");
		year.put("index", "not_analyzed");
		
		properties.put("name", name);
		properties.put("sex", sex);
		properties.put("birth", birth);
		properties.put("year", year);
		
		type.put("properties", properties);
		result.put(typeName, type);
		return result;
		
	}

	public JSONObject getHealth(Client client) {
		JSONObject result = new JSONObject();
		ClusterAdminClient clusterAdminClient = client.admin().cluster();
		ClusterHealthResponse healths = clusterAdminClient.prepareHealth().get();
		String clusterName = healths.getClusterName();
		int numberOfDataNodes = healths.getNumberOfDataNodes();
		int numberOfNodes = healths.getNumberOfNodes();

		JSONArray array = new JSONArray();
		for (ClusterIndexHealth health : healths.getIndices().values()) {
			JSONObject h = new JSONObject();
			String index = health.getIndex();
			int numberOfShards = health.getNumberOfShards();
			int numberOfReplicas = health.getNumberOfReplicas();
			ClusterHealthStatus status = health.getStatus();
			h.put("索引名称", index);
			h.put("分片数", numberOfShards);
			h.put("复制分片数", numberOfReplicas);
			h.put("索引状态", JSON.toJSON(status));
			array.add(h);
		}
		
		result.put("集群名称", clusterName);
		result.put("数据节点数量", numberOfDataNodes);
		result.put("节点数量", numberOfNodes);
		result.put("索引情况", array);
		log.info(result.toString());
		log.info(healths.toString());
		return result;
	}
	
	public void createAlias(Client client, String index, String alias){
		IndicesAliasesRequest iarb = new IndicesAliasesRequest();
		IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(AliasAction.Type.ADD,index,alias);
		iarb.addAliasAction(aliasAction);
		IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
		log.info(iar.isAcknowledged());
	}
	
	public void deleteAlias(Client client, String index, String alias){
		IndicesAliasesRequest iarb = new IndicesAliasesRequest();
		IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(AliasAction.Type.REMOVE,index,alias);
		iarb.addAliasAction(aliasAction);
		IndicesAliasesResponse iar = client.admin().indices().aliases(iarb).actionGet();
		log.info(iar.isAcknowledged());
	}

}
