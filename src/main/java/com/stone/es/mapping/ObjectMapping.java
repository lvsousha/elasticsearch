package com.stone.es.mapping;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapping {

	private String type = "object";
	private Boolean dynamic = true;
	private Boolean enabled = true;			//是否索引json数据
	private List<AbstractMapping> properties = new ArrayList<>();
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
	public List<AbstractMapping> getProperties() {
		return properties;
	}
	public void setProperties(List<AbstractMapping> properties) {
		this.properties = properties;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
