package com.stone.es.model;

import lombok.Data;

@Data
public class ESData {
	
	private String index;
	private String type = "doc";
	private String id;
	private String source;
	
	public ESData(String index, String source){
		this.index = index;
		this.source = source;
	}
	
	public ESData(String index, String id, String source){
		this.index = index;
		this.id = id;
		this.source = source;
	}

}
