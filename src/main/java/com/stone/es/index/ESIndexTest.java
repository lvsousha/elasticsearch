package com.stone.es.index;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;

import com.stone.es.ESClient;
import com.stone.es.mapping.DateMapping;
import com.stone.es.mapping.Mapping;
import com.stone.es.mapping.NumbericMapping;
import com.stone.es.mapping.StringMapping;

public class ESIndexTest {

	public static void main(String[] args) throws Exception {
		ESIndexTest esit = new ESIndexTest();
		Client client = ESClient.createClientBySetting();
		
		
		
		esit.testGetIndexState(client);
//		esit.testDeleteCreateIndexAndAppendMapping(client);
		
		client.close();
	}
	
	public void testGetIndexState(Client client, String... indices) throws Exception{
		ESIndexClient esIndex = new ESIndexClientBasic();
//		esIndex.getIndexMetadata(client,"flfg");
//		esIndex.getIndexStatus(client,"flfg");
		esIndex.getIndices(client,"flfg");
	}
	
	
	/**
	 * 测试创建索引，删除索引，添加映射
	 * @param client
	 * @throws Exception
	 */
	public void testDeleteCreateIndexAndAppendMapping(Client client) throws Exception{
		ESIndexClient esIndex = new ESIndexClientBasic();
		List<Mapping> mappings = new ArrayList<>();
		String index = "test";
		String type = "test";
		
		esIndex.deleteIndices(client, index);
		
		mappings.add(new DateMapping("createDate"));
		mappings.add(new StringMapping("keyword", true));
		mappings.add(new StringMapping("content", false));
		mappings.add(new NumbericMapping("user", NumbericMapping.LONG));
		esIndex.createIndex(client, index, type, mappings);
		
		List<Mapping> appends = new ArrayList<>();
		appends.add(new StringMapping("append", true));
		esIndex.appendMapping(client, index, type, appends);
	}

}
