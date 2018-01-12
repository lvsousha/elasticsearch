package com.stone.es;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.es.model.ESData;

public class ES {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Client client = ESClient.createClientBySetting();
//		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.248.3:9500");
		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.247.180:9300");
		ESInsert esi = new ESInsert();
		File fold = new File("/JSON/20180105/");
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String index = "flfg-v2";
		String type = "flfg-v2";
		List<ESData> datas = new ArrayList<>();
		for(File file : fold.listFiles()){
			JSONObject o = JSON.parseObject(FileUtils.readFileToString(file, "UTF-8"));
			JSONObject d = new JSONObject();
			d.put("title", o.getString("name"));
			d.put("content", o.getString("child"));
			d.put("createDate", sdf.format(new Date()));
			ESData data = new ESData(index, type, d.toString());
			datas.add(data);
		}
		System.out.println(datas.size());
		esi.insertBulk(client, datas);
	}

}
