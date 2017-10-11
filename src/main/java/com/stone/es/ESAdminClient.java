package com.stone.es;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

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
//		esdc.getIndices(client);
		esdc.createIndex(client);
		

		client.close();
	}
	
	public JSONObject createIndex(Client client){
		JSONObject result = new JSONObject();
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CreateIndexResponse response = indicesAdminClient.prepareCreate("yuhuan")  
				        				  .setSettings(
				        						  Settings.builder()                             
				        						  			.put("index.number_of_shards", 5)    
				        						  			.put("index.number_of_replicas", 1)
				        				  
				        				  )
				        				  .addMapping("yanpan", createMappings("yanpan"))
				        				  .get();
		log.info(response.isAcknowledged());
		return result;
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
	
	public JSONObject getIndices(Client client){
		JSONObject result = new JSONObject();
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		GetIndexResponse response = indicesAdminClient.prepareGetIndex().get();
		ImmutableOpenMap<Object, Object> iom = response.getContext();
		Iterator<ObjectObjectCursor<Object, Object>> iterator = iom.iterator();
		while(iterator.hasNext()){
			log.info(iterator.next().key);
		}
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
