package com.stone.es;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;

import com.alibaba.fastjson.JSONObject;
import com.stone.es.model.ESData;
import com.stone.es.model.ESIndex;

public class ESDataTransfer {
	
	
//	private Logger log = Logger.getLogger(this.getClass());
	private ESAdminClient esdc = new ESAdminClient();
	private ESSearch ess = new ESSearch();
	private ESInsert esi = new ESInsert();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESAdminClient esdc = new ESAdminClient();
		ESSearch ess = new ESSearch();
		ESInsert esi = new ESInsert();
		Client sourceClient = ESClient.createClientBySetting("elasticsearchZgy", "yp-zcl-node-2", "localhost:9387");
		Client targetClient = ESClient.createClientBySetting("yp-zcl-app", "yp-zcl-node-1", "39.106.37.249:9300");
//		Client targetClient = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.248.3:9500");
		esdc.deleteIndices(targetClient, "dxal","flfg");
		List<ESIndex> indices = esdc.getIndices(sourceClient);
		for(ESIndex index : indices){
			JSONObject types = index.getMappings();
			for(String type : types.keySet()){
				List<ESData> datas = ess.searchAll(sourceClient, index.getIndex(), type, QueryBuilders.matchAllQuery());
				esdc.createIndex(targetClient, index.getIndex(), type, types.getString(type));
//				esi.insertBulk(targetClient, datas);
				esi.insertBulkProcessor(targetClient, datas);
				for(ESData data : datas){
					FileUtils.writeStringToFile(new File("/ES/json/"+data.getIndex()+"/"+data.getType()+"/"+data.getId()+".json"), data.getSource(),"utf-8");
				}
			}
		}
		esi.getBulkProcessor().close();
		
		while(true){
			
		}
//		sourceClient.close();
//		targetClient.close();
		
	}
	
	public void transferData(Client sourceClient, Client targetClient, List<String> indexNames, List<String> typeNames) throws IOException{
		List<ESIndex> indices = esdc.getIndices(sourceClient);
		for(ESIndex index : indices){
			if(indexNames != null && indexNames.size() > 0 && !indexNames.contains(index.getIndex())){
				continue;
			}
			JSONObject types = index.getMappings();
			for(String type : types.keySet()){
				if(typeNames != null && typeNames.size() > 0 && !typeNames.contains(type)){
					continue;
				}
				List<ESData> datas = ess.searchAll(sourceClient, index.getIndex(), type, QueryBuilders.matchAllQuery());
				esdc.createIndex(targetClient, index.getIndex(), type, types.getString(type));
				esi.insertBulk(targetClient, datas);
			}
		}
		
	}
	
}
