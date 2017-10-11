//package com.stone.test.springtest;
//
//import javax.servlet.ServletContext;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.stone.webapp.controller.PrivateController;
//
//public class BasicControllerTest extends Basic{
//	@Autowired  
//	PrivateController privateController;  
//	  
//	@Autowired  
//	ServletContext context;  
//	  
//	MockMvc mockMvc;  
//	@Before  
//	public void setup(){  
//	    mockMvc = MockMvcBuilders.standaloneSetup(privateController).build();  
//	}
//	
//	@Test
//	public void test() throws Exception{
//		ResultActions resultActions = this.mockMvc
//											.perform(
//													MockMvcRequestBuilders
//													.post("/jwms/private/getSubjects")
//													.contentType(MediaType.APPLICATION_JSON)
//													.accept(MediaType.APPLICATION_JSON)
//													.characterEncoding("UTF-8")
//													.content(" ")
//											); 
//		MvcResult mvcResult = resultActions.andReturn();  
//		String result = mvcResult.getResponse().getContentAsString(); 
//		System.out.println(mvcResult.getResponse().getCharacterEncoding());
//		System.out.println("=====客户端获得反馈数据:" + result);  
//	}
//}
