package com.stone.es.analysis.tokenizer;

/**
 * 经典类型的分词器提供基于语法的分词器，是英语语言文档的一个很好的分词器。 
 * 这个标记器对首字母缩略词，公司名称，电子邮件地址和互联网主机名称进行了特殊处理。 
 * 但是，这些规则并不总是有效，对于除英语之外的大多数语言，标记器不起作用。
 * @author zhengchanglin
 *
 */
public class ClassicTokenizer implements Tokenizer{

	private String type = "classic";
	private Integer maxTokenLength;	//default 255
	
	private String name;
	public ClassicTokenizer(){};
	
	public ClassicTokenizer(String name){
		this.name = name;
	}

	@Override
	public String getDefalut() {
		return "ClassicTokenizer";
	}

	@Override
	public String getCustome() {
		return "ClassicTokenizer";
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

	public Integer getMaxTokenLength() {
		return maxTokenLength;
	}

	public void setMaxTokenLength(Integer maxTokenLength) {
		this.maxTokenLength = maxTokenLength;
	}
}
