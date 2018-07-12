package com.stone.es.analysis.tokenizer;

/**
 * 字母的分词器，以非字母分隔文本。 也就是说，它将令牌定义为相邻字母的最大字符串
 * @author zhengchanglin
 *
 */
public class LetterTokenizer  implements Tokenizer{
	
	private String type = "letter";

	private String name;
	public LetterTokenizer(){};
	
	public LetterTokenizer(String name){
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
