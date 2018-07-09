package com.stone.es.constants;

public class AnalyzersConstants {

  /**
   * 所述standard分析器将文本分为在字边界条件，由Unicode的文本分割算法所定义的。它删除了大多数标点符号，小写术语，并支持删除停用词
   */
  public static final String STANDARD = "standard";

  /**
   * 该simple分析仪将文本分为方面每当遇到一个字符是不是字母
   */
  public static final String SIMPLE = "simple";

  /**
   * whitespace只要遇到任何空格字符 ，分析器就会将文本划分为术语
   */
  public static final String WHITESPACE = "whitespace";

  /**
   * 
   */
  public static final String STOP = "stop";

  /**
   * 所述pattern分析仪使用一个正则表达式的文本
   */
  public static final String PATTERN = "pattern";

  /**
   *
   */
  public static final String KEYWORD = "keyword";

  /**
   * 许多特定于语言的分析器
   */
  public static final String LANGUAGE = "english";

  /**
   *
   */
  public static final String FINGERPRINT = "fingerprint";

}
