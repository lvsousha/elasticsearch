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
		ESIndex esIndex = new ESIndexBasic();
		Client client = ESClient.createClientBySetting();
		String index = "test";
		String type = "test";
		esIndex.deleteIndices(client, index);
		List<Mapping> mappings = new ArrayList<>();
		mappings.add(new DateMapping("createDate"));
		mappings.add(new StringMapping("keyword", true));
		mappings.add(new StringMapping("content", false));
		mappings.add(new NumbericMapping("user", NumbericMapping.LONG));
		esIndex.createIndex(client, index, type, mappings);
		
		List<Mapping> appends = new ArrayList<>();
		appends.add(new StringMapping("append", true));
		esIndex.appendMapping(client, index, type, appends);
		
		client.close();
	}

}
