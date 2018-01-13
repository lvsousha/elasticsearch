package com.stone.es.model;

import com.alibaba.fastjson.JSONObject;

public class ESIndex {

	private String index;
	private Long docs;
	private Long size;//mb
	private JSONObject mappings;
	private JSONObject settings;
	private ESIndexStatus status;
	private ESIndexMetadata metadata;
	
	public ESIndex(){
		
	}
	
	public ESIndex(String index, Long docs, Long size, JSONObject mappings, JSONObject settings){
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

	public Long getDocs() {
		return docs;
	}

	public void setDocs(Long docs) {
		this.docs = docs;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
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

	public ESIndexStatus getStatus() {
		return status;
	}

	public void setStatus(ESIndexStatus status) {
		this.status = status;
	}

	public ESIndexMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ESIndexMetadata metadata) {
		this.metadata = metadata;
	}
	
	
	
	
}
