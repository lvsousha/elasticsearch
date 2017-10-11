package com.stone.test.springtest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-applicationContext.xml","classpath:spring/spring-servlet.xml"}) //加载配置文件  
public class Basic  extends AbstractJUnit4SpringContextTests{

}
