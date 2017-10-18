package com.stone.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
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

/**
 * 创建Index，添加Mapping，获取和修改Setting，获取集群信息
 * @author zhengchanglin
 *
 */
public class ESAdminClient {

	private Logger log = Logger.getRootLogger();

	public static void main(String[] args) throws Exception {
		ESAdminClient esdc = new ESAdminClient();
		Client client = ESClient.createClientBySetting();
		
//		esdc.getHealth(client);
		esdc.getIndices(client);
//		esdc.createIndex(client);
//		esdc.createMappings("test");
		

		client.close();
	}
	
	public List<ESIndex> getIndices(Client client) throws IOException{
		IndicesAdminClient iac = client.admin().indices();
		GetIndexResponse response = iac.prepareGetIndex().get();
		String[] indices = response.getIndices();
		List<ESIndex> list = new ArrayList<>();
		for(String index : indices){
			ESIndex obj = new ESIndex();
			IndicesStatsResponse isr = iac.prepareStats(index).get();
			CommonStats cs = isr.getTotal();
			obj.setIndex(index);
			obj.setDocs(String.valueOf(cs.getDocs().getCount()));
			obj.setSize(cs.getStore().getSize().getMb()+"mb");
//			log.info("索引名："+index);
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
		log.info(response.isAcknowledged());
		return result;
	}
	
	public XContentBuilder createMappingsByXCB(String typeName) throws IOException{
		XContentBuilder  builder  =  XContentFactory.jsonBuilder().startObject()
				.startObject(typeName)
					.startObject("properties")
						.startObject("name")
							.field("type", "string")
							.field("index", "analyzed")
						.endObject()
						.startObject("sex")
							.field("type", "string")
							.field("index", "analyzed")
						.endObject()
						.startObject("birth")
							.field("type", "date")
							.field("index", "analyzed")
							.field("format", "strict_date_optional_time||epoch_millis")
						.endObject()
						.startObject("year")
							.field("type", "string")
							.field("index", "analyzed")
						.endObject()
					.endObject()
				.endObject().endObject();
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

}
