package com.stone.es.operation;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;

import com.stone.es.ESClient;
import com.stone.es.model.ESData;

public class ESOperationTest {

	private Logger log = Logger.getRootLogger();
	
	public static void main(String[] args) throws Exception {
		ESOperationTest test = new ESOperationTest();
		Client client = ESClient.createClientBySetting();
		test.testInsertSingle(client);
	}
	
	public void testInsertSingle(Client client) throws Exception{
		ESInsert esi = new ESInsert();
		for(File file : new File("/JSON").listFiles()){
			ESData data = new ESData("flfg", "flfg", FileUtils.readFileToString(file,"utf-8"));
			log.info(data.getSource());
			esi.insertSingle(client, data);
		}
	}
	
}
