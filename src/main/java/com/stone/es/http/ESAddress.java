package com.stone.es.http;

public class ESAddress {

	public final static String CAT_INDICES = "http://39.106.37.249:9200/_cat/indices?v"; //获取全部索引的信息，包括 健康状态，分片数量，记录数量，占用空间
	
	public final static String BULK = "http://39.106.37.249:9200/{index}/{type}/_bulk";
	
}
