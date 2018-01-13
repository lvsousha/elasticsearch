package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder.FieldCaseConversion;
import org.elasticsearch.common.xcontent.XContentBuilderString;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.testXContentBuilder();
	}
	
	public void testXContentBuilder() throws Exception{
		XContentBuilder builder = XContentFactory.jsonBuilder();
		JSONObject param = new JSONObject();
		builder.startObject().endObject();
		builder = print(builder);
		builder.startObject("type").endObject();
		builder = print(builder);
		param.put("name", "zcl");param.put("password", "123456");
		builder.startObject().field("type").value("flfg").endObject();
		builder = print(builder);
		builder.startArray();
		builder = print(builder);
	}
	
	public XContentBuilder print(XContentBuilder builder) throws Exception{
		System.out.println(builder.string());
		builder = XContentFactory.jsonBuilder();
		return builder;
	}

}
