package com.stone.es.analysis.tokenizer;

public class WhitespaceTokenizer  implements Tokenizer{

	private String type = "whitespace";
	
	private String name;
	public WhitespaceTokenizer(){};
	
	public WhitespaceTokenizer(String name){
		this.name = name;
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
