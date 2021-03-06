package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class NumbericMapping implements Mapping{

	private String type;	//long,integer,short,byte,double,float
	private String name;
	private Boolean coerce = true;		//是否转换string为number
	private Double boost = 1.0;			//权重
	private Boolean doc_values = true;
	private Boolean ignore_malformed = false;
	private Boolean include_in_all;
	private String index = "not_analyzed";
	private String null_value = "null";
	private Integer precision_step;
	private Boolean store = false;
	
	public static final String INTEGER = "integer";
	public static final String LONG = "long";
	public static final String SHORT = "short";
	public static final String BYTE = "byte";
	public static final String DOUBLE = "double";
	public static final String FLOAT = "float";
	
	public NumbericMapping(String name){
		this.name = name;
		this.type = NumbericMapping.INTEGER;
	}
	
	public NumbericMapping(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	@Override
	public void string(XContentBuilder builder) throws Exception {
		builder.startObject(this.name);
		builder.field("type", this.type);
		builder.endObject();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getCoerce() {
		return coerce;
	}
	public void setCoerce(Boolean coerce) {
		this.coerce = coerce;
	}
	public Double getBoost() {
		return boost;
	}
	public void setBoost(Double boost) {
		this.boost = boost;
	}
	public Boolean getDoc_values() {
		return doc_values;
	}
	public void setDoc_values(Boolean doc_values) {
		this.doc_values = doc_values;
	}
	public Boolean getIgnore_malformed() {
		return ignore_malformed;
	}
	public void setIgnore_malformed(Boolean ignore_malformed) {
		this.ignore_malformed = ignore_malformed;
	}
	public Boolean getInclude_in_all() {
		return include_in_all;
	}
	public void setInclude_in_all(Boolean include_in_all) {
		this.include_in_all = include_in_all;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getNull_value() {
		return null_value;
	}
	public void setNull_value(String null_value) {
		this.null_value = null_value;
	}
	public Integer getPrecision_step() {
		return precision_step;
	}
	public void setPrecision_step(Integer precision_step) {
		this.precision_step = precision_step;
	}
	public Boolean getStore() {
		return store;
	}
	public void setStore(Boolean store) {
		this.store = store;
	}
	
	
}
