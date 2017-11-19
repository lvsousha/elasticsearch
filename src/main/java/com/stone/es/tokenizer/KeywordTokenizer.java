package com.stone.es.tokenizer;

/**
 * 将整个输入作为单个输出发送
 * @author zhengchanglin
 *
 */
public class KeywordTokenizer  implements Tokenizer{

	private String type = "keyword ";
	private Integer bufferSize;	//	default 256
	
	private String name;
	public KeywordTokenizer(){};
	
	public KeywordTokenizer(String name){
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
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
