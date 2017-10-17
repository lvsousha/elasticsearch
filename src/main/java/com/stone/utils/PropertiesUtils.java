package com.stone.utils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtils {

	private static Properties properties;
	
	static{
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("config.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		if (properties.containsKey(key)) {
	        return properties.getProperty(key);
	    }
	    return null;
	}
	
	/**
	 * 取出String类型的Property，如果都Null则抛出异常.
	 */
	public static String getString(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return value;
	}
	
	/**
	 * 取出String类型的Property.如果为Null则返回Default值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}
}
