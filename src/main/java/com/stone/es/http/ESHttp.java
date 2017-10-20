package com.stone.es.http;

import org.apache.log4j.Logger;

public class ESHttp {

	private static Logger log = Logger.getRootLogger();
	public static void main(String[] args) {
		ESHttp http = new ESHttp();
		http.analyze();
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
	
	public void analyze(){
		String result = HttpClientUtil.post("http://localhost:9287/flfg/_analyze", "{\"field\": \"neirong\",\"text\": \"中华人民共和国\"}");
		log.info(result);
		System.out.println(result);
	}
	
	public static void explain(String message){
		String result = HttpClientUtil.post("http://localhost:9287/flfg/flfg/_validate/query?explain", message);
		log.info(result);
		System.out.println(result);
	}
	
	public static void search(String message){
		String result = HttpClientUtil.post("http://localhost:9287/flfg/flfg/_search", message);
		log.info(result);
		System.out.println(result);
	}
	
	
	
}
