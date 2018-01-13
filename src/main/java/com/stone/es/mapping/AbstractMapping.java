package com.stone.es.mapping;

public abstract class AbstractMapping implements Mapping{

	private Boolean isDefalut = true;
	private String type;
	private String name;
	public Boolean getIsDefalut() {
		return isDefalut;
	}
	public void setIsDefalut(Boolean isDefalut) {
		this.isDefalut = isDefalut;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
