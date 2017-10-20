package com.stone.es;

public class Mapping {
	
	private String name;					//字段名
	private String type = "string";		//字段类型
	private String index = "analyzed";		//analyzed，not_analyzed，no
	private String format = "strict_date_optional_time||epoch_millis";					//用于类型是date
	private String analyzer;				//分词器
	private Boolean fields = false;				//当字段类型是string时可以使用，可以针对同一个字段指定是否分词和不同的分词器
	private Boolean isDefault = true;
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
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Boolean getFields() {
		return fields;
	}
	public void setFields(Boolean fields) {
		this.fields = fields;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
