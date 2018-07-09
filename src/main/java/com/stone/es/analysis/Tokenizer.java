package com.stone.es.analysis;

/**
 * 
 * @author zhengchanglin
 *
 */
public class Tokenizer {

  public static final String STANDARD = "standard"; // 基于Unicode文本分割算法
  public static final String LETTER = "letter";// 字母记录器每当遇到一个不是字母的字符时就把文本分解成术语。它对大多数欧洲语言来说都是合理的工作，但是对于一些亚洲语言来说，这是一项糟糕的工作，语言中的词没有空格分隔。
  public static final String LOWERCASE = "lowercase";
  public static final String WHITESPACE = "whitespace";
  public static final String UAX_URL_EMAIL = "uax_url_email";
  public static final String CLASSIC = "classic";
  public static final String NGRAM = "ngram"; // NGRG标记器在遇到指定字符列表中的一个时，首先将文本分解成单词，然后发出指定长度的每个单词的n-克。
  public static final String EDGE_NGRAM = "edge_ngram"; 
  public static final String PATTERN = "pattern"; 
  public static final String SIMPLE_PATTERN = "simple_pattern"; 
  public static final String SIMPLE_PATTERN_SPLIT = "simple_pattern_split"; 
  public static final String PATH_HIERARCHY = "path_hierarchy"; 


}
