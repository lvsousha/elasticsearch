package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public interface Mapping {

	public void string(XContentBuilder builder) throws Exception;
	
}
