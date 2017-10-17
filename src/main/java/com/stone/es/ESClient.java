package com.stone.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.shield.ShieldPlugin;

import com.stone.utils.PropertiesUtils;

public class ESClient {
	
	public static void main(String[] args) throws Exception {
//		Client client = ESClient.createClientBySetting();
		Client client = ESClient.createClientShield();
//		XContentBuilder  builder  =  XContentFactory.jsonBuilder()
//		        .startObject()
//		                .field("user",  "kimchy")
//		                .field("postDate",  new  Date())
//		                .field("message",  "trying  out  Elasticsearch")
//		        .endObject();
//		IndexResponse  response  =  client.prepareIndex("twitter",  "tweet")
//                .setSource(builder.string())
//                .get();
		client.close();
		
	}
	
	public static Client createClient() throws Exception{
		TransportClient client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		return client;
	}
	
	public static Client createClientBySetting() throws Exception{
		String clusterName = PropertiesUtils.getString("cluster.name");
		String nodeNamee = PropertiesUtils.getString("node.name");
		String url = PropertiesUtils.getString("es.url");
		Settings settings  =  Settings.settingsBuilder()
                					.put("cluster.name",  clusterName)
                					.put("node.name",  nodeNamee)
//                					.put("client.transport.sniff",  "true")
//                					.put("client.transport.ignore_cluster_name",  "true")		//设置为true可忽略连接的节点的集群名称验证
//                					.put("client.transport.ping_timeout",  "1000")	//等待来自节点的ping响应的时间。 默认为5秒。
//                					.put("client.transport.nodes_sampler_interval",  "1000")	//定时对列出和连接的节点进行抽样/ ping操作。 默认为5秒。
                					.build();
		Client client  =  TransportClient.builder()
										.settings(settings)
										.build()
										.addTransportAddress(
												new InetSocketTransportAddress(
														InetAddress.getByName(url.split(":")[0]), 
														Integer.parseInt(url.split(":")[1]))
										);
		return client;
	}
	
	public static Client createClientShield() throws NumberFormatException, UnknownHostException{
		String clusterName = PropertiesUtils.getString("cluster.name");
//		String nodeNamee = PropertiesUtils.getString("node.name");
		String url = PropertiesUtils.getString("es.url");
		String shieldUser = PropertiesUtils.getString("shield.user");
		Settings settings  =  Settings.settingsBuilder()
                					.put("cluster.name",  clusterName)
//                					.put("node.name",  nodeNamee)
                					.put("shield.user", shieldUser)
//                					.put("client.transport.sniff",  "true")
//                					.put("client.transport.ignore_cluster_name",  "true")		//设置为true可忽略连接的节点的集群名称验证
//                					.put("client.transport.ping_timeout",  "1000")	//等待来自节点的ping响应的时间。 默认为5秒。
//                					.put("client.transport.nodes_sampler_interval",  "1000")	//定时对列出和连接的节点进行抽样/ ping操作。 默认为5秒。
                					.build();
		Client client  =  TransportClient.builder()
										.addPlugin(ShieldPlugin.class)
										.settings(settings)
										.build()
										.addTransportAddress(
												new InetSocketTransportAddress(
														InetAddress.getByName(url.split(":")[0]), 
														Integer.parseInt(url.split(":")[1]))
										);
		return client;
	}
}
