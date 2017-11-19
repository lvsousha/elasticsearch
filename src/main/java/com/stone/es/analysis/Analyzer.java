package com.stone.es.analysis;

import java.util.List;

import com.stone.es.tokenizer.Tokenizer;

public class Analyzer {

	private String type = "custom";
	private String name;
	private List<CharFilter> charFilters;	//字符过滤器——字符过滤器 用来 整理 一个尚未被分词的字符串
	private Tokenizer tokenizer;	//分词器——一个分析器 必须 有一个唯一的分词器。 分词器把字符串分解成单个词条或者词汇单元
	private List<Filter> filters;	//词单元过滤器——经过分词，作为结果的 词单元流 会按照指定的顺序通过指定的词单元过滤器 
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
	public List<CharFilter> getCharFilters() {
		return charFilters;
	}
	public void setCharFilters(List<CharFilter> charFilters) {
		this.charFilters = charFilters;
	}
	public Tokenizer getTokenizer() {
		return tokenizer;
	}
	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
}
