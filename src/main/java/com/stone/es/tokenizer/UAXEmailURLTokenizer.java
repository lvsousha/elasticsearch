package com.stone.es.tokenizer;

/**
 * 工作方式与标准标记器完全相同，但将电子邮件和网址标记为单个标记
 * @author zhengchanglin
 *
 */
public class UAXEmailURLTokenizer implements Tokenizer{

	private String type = "uax_url_email";
	private Integer maxTokenLength;	//default 255
	
	private String name;
	public UAXEmailURLTokenizer(){};
	
	public UAXEmailURLTokenizer(String name){
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
