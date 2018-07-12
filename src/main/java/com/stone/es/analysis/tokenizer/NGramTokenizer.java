package com.stone.es.analysis.tokenizer;

public class NGramTokenizer  implements Tokenizer{
	private String type = "nGram";
	private Integer minGram; //default 1
	private Integer maxGram; //default 2
	private String[] tokenChars;	//letter,digit,whitespace,punctuantion,symbol——将分割不属于任何这些类的字符
	
	private String name;
	public NGramTokenizer(){};
	
	public NGramTokenizer(String name){
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMinGram() {
		return minGram;
	}

	public void setMinGram(Integer minGram) {
		this.minGram = minGram;
	}

	public Integer getMaxGram() {
		return maxGram;
	}

	public void setMaxGram(Integer maxGram) {
		this.maxGram = maxGram;
	}

	public String[] getTokenChars() {
		return tokenChars;
	}

	public void setTokenChars(String[] tokenChars) {
		this.tokenChars = tokenChars;
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
