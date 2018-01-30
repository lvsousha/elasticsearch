package com.stone.es.mapping;

import java.util.ArrayList;
import java.util.List;

public class NestedMapping {

	private String type = "nested";
	private Boolean dynamic = true;
	private List<Mapping> properties = new ArrayList<>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getDynamic() {
		return dynamic;
	}
	public void setDynamic(Boolean dynamic) {
		this.dynamic = dynamic;
	}
	public List<Mapping> getProperties() {
		return properties;
	}
	public void setProperties(List<Mapping> properties) {
		this.properties = properties;
	}
	
	
}
