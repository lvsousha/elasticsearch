package com.stone.es.http;

import org.apache.log4j.Logger;

public class ESHttp {

	private Logger log = Logger.getLogger(this.getClass());
	public static void main(String[] args) {
		ESHttp http = new ESHttp();
		http.bulkRequest();
	}
	
	public void getIndices(){
		String result = HttpClientUtil.get("http://localhost:9200/_cat/indices?v");
		log.info(result);
		System.out.println(result);
	}
	
	public void bulkRequest(){
		String result = HttpClientUtil.post("http://39.106.37.249:9200/my_store/products/_bulk",
				"{ \"index\": { \"_id\": 9 }}\n{ \"price\" : 10, \"productID\" : \"XHDK-A-1293-#fJ3\" }\n");
		log.info(result);
		System.out.println(result);
	}
	
	
	
}
