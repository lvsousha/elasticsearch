package com.stone.es;

import java.util.ArrayList;
import java.util.List;

public class Mapping {
	
	private String type = "string";		//字段类型
	private String index = "analyzed";		//analyzed，not_analyzed，no
	private String format;					//用于类型是date
	private String analyzer;				//分词器
	private boolean store = true;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getAnalyzer() {
		return analyzer;
	}
	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}
	public boolean isStore() {
		return store;
	}
	public void setStore(boolean store) {
		this.store = store;
	}

}
