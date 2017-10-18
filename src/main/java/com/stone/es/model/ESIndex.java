package com.stone.es.model;

import com.alibaba.fastjson.JSONObject;

public class ESIndex {

	private String index;
	private String docs;
	private String size;//mb
	private JSONObject mappings;
	private JSONObject settings;
	
	public ESIndex(){
		
	}
	
	public ESIndex(String index, String docs, String size, JSONObject mappings, JSONObject settings){
		this.index = index;
		this.docs = docs;
		this.size = size;
		this.mappings = mappings;
		this.settings = settings;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public JSONObject getMappings() {
		return mappings;
	}

	public void setMappings(JSONObject mappings) {
		this.mappings = mappings;
	}

	public JSONObject getSettings() {
		return settings;
	}

	public void setSettings(JSONObject settings) {
		this.settings = settings;
	}
	
	
	
	
}
