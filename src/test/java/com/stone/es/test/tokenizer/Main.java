package com.stone.es.test.tokenizer;

import org.apache.log4j.Logger;

import com.stone.es.tokenizer.ClassicTokenizer;
import com.stone.es.tokenizer.Tokenizer;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger log = Logger.getRootLogger();
		Tokenizer ct = new ClassicTokenizer();
		log.info(ct.getDefalut());
	}

}
