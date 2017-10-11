package com.stone.es.http;

import org.apache.log4j.Logger;

public class ESHttp {

	private Logger log = Logger.getLogger(this.getClass());
	public static void main(String[] args) {
		ESHttp http = new ESHttp();
		http.getIndices();
	}
	
	public void getIndices(){
		String result = HttpClientUtil.get("http://localhost:9200/_cat/indices?v");
		log.info(result);
		System.out.println(result);
	}
	
	
	
}
