package com.stone.es.model;

public class ESIndexStatus {
	private String index;
	private Long docs;
	private Long size;//mb
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
	
}
