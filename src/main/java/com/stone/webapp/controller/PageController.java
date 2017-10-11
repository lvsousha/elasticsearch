package com.stone.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/page")
public class PageController {
	
	private Logger log = Logger.getLogger(this.getClass());
//	private Gson gson = new GsonBuilder()  
//			  				.setDateFormat("yyyy-MM-dd HH:mm:ss")  
//			  				.create();
	
	@RequestMapping(value={"/index",""})
	public String index(HttpServletRequest request, Model model, RedirectAttributes ra) throws IOException {
		log.info("controller——index");
		return "index";
	}
	
}
