package com.stone.es.model;

import java.util.List;
import java.util.Map;

import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;

public class ESIndexMetadata {

	private String index;
	private List<MappingMetaData> mappings;
	private List<AliasMetaData> alias;
	private Map<String,String> settings;
	public Map<String, String> getSettings() {
		return settings;
	}
	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public List<MappingMetaData> getMappings() {
		return mappings;
	}
	public void setMappings(List<MappingMetaData> mappings) {
		this.mappings = mappings;
	}
	public List<AliasMetaData> getAlias() {
		return alias;
	}
	public void setAlias(List<AliasMetaData> alias) {
		this.alias = alias;
	}
	
	
}
