package com.stone.es.analysis.tokenizer;

/**
 * 标准类型的标记器，提供基于语法的标记器，对于大多数欧洲语言文档来说，它是一个很好的标记器
 * @author zhengchanglin
 *
 */
public class StandardTokenizer  implements Tokenizer{

	private String type = "standard";
	private Integer maxTokenLength;	//default 255
	
	private String name;
	public StandardTokenizer(){};
	
	public StandardTokenizer(String name){
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMaxTokenLength() {
		return maxTokenLength;
	}

	public void setMaxTokenLength(Integer maxTokenLength) {
		this.maxTokenLength = maxTokenLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDefalut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustome() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
