package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class StringMapping implements Mapping{

	private String type = "string";
	private String name;
	private Boolean fields;

	public StringMapping(String name, Boolean fields){
		this.name = name;
		this.fields = fields;
	}
	
	@Override
	public void string(XContentBuilder builder) throws Exception {
		builder.startObject(this.name).field("type", this.type);
		if(this.fields){
			builder.startObject("fields")
						.startObject("raw")
							.field("type", this.type)
							.field("index", "not_analyzed")
							.field("store","true")
						.endObject()
					.endObject();
		}
		builder.endObject();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getFields() {
		return fields;
	}

	public void setFields(Boolean fields) {
		this.fields = fields;
	}
	
}
