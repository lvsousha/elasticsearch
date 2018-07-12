package com.stone.es.analysis.tokenizer;

/**
 * ?????????????????????
 * 一种类型模式的标记器，可以通过正则表达式将文本灵活地分离成词项。
 * @author zhengchanglin
 *
 */
public class PatternTokenizer  implements Tokenizer{

	private String type = "pattern";
	private String pattern;
	private String flag;
	private Integer group;
	
	private String name;
	public PatternTokenizer(){};
	
	public PatternTokenizer(String name){
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
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
