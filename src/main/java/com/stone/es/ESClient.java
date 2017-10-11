package com.stone.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class ESClient {

	public static void main(String[] args) throws Exception {
		Client client = ESClient.createClientBySetting();
		
		XContentBuilder  builder  =  XContentFactory.jsonBuilder()
		        .startObject()
		                .field("user",  "kimchy")
		                .field("postDate",  new  Date())
		                .field("message",  "trying  out  Elasticsearch")
		        .endObject();
		IndexResponse  response  =  client.prepareIndex("twitter",  "tweet")
                .setSource(builder.string())
                .get();
		client.close();
		
	}
	
	public static Client createClient() throws Exception{
		TransportClient client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		return client;
	}
	
	public static Client createClientBySetting() throws Exception{
		Settings settings  =  Settings.settingsBuilder()
                					.put("cluster.name",  "yp-zcl-app")
                					.put("node.name",  "yp-zcl-node-1")
//                					.put("client.transport.sniff",  "true")
//                					.put("client.transport.ignore_cluster_name",  "true")		//设置为true可忽略连接的节点的集群名称验证
//                					.put("client.transport.ping_timeout",  "1000")	//等待来自节点的ping响应的时间。 默认为5秒。
//                					.put("client.transport.nodes_sampler_interval",  "1000")	//定时对列出和连接的节点进行抽样/ ping操作。 默认为5秒。
                					.build();
		Client client  =  TransportClient.builder()
										.settings(settings)
										.build()
										.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		return client;
	}
}
