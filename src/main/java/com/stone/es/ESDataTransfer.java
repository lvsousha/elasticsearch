package com.stone.es;

import org.elasticsearch.client.Client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ESDataTransfer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESAdminClient esdc = new ESAdminClient();
		Client sourceClient = ESClient.createClientBySetting("elasticsearchZgy", "yp-zcl-node-2", "39.106.37.249:9387");
		Client targerClient = ESClient.createClientBySetting("yp-zcl-app", "yp-zcl-node-1", "39.106.37.249:9300");
		JSONArray indices = esdc.getIndices(sourceClient);
		for(int i=0; i < indices.size(); i++){
			JSONObject index = indices.getJSONObject(i);
			JSONObject types = index.getJSONObject("mappings");
			System.out.println(index.getString("index"));
			System.out.println(types.toString());
			for(String type : types.keySet()){
				System.out.println(type);
				esdc.createIndex(targerClient, index.getString("index"), type, types.getString(type));
			}
			
		}
		
	}

}
