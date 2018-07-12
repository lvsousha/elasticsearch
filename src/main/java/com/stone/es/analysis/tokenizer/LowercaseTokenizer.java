package com.stone.es.analysis.tokenizer;

/**
 *一个小写字母的标记器，一起执行Letter Tokenizer和小写标记过滤器的功能。 
 *它将文本分成非字母，并将其转换为小写。 
 *虽然它在功能上等同于Letter Tokenizer和小写令牌过滤器的组合，但同时执行两个任务会有性能优势，
 *因此这是（冗余的）实现
 * @author zhengchanglin
 *
 */
public class LowercaseTokenizer  implements Tokenizer{

	private String type = "lowercase ";

	private String name;
	public LowercaseTokenizer(){};
	
	public LowercaseTokenizer(String name){
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
