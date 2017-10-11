package com.stone.webapp.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent>{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void onApplicationEvent(ServletRequestHandledEvent event) {
		log.info(event.getDescription());
//		logger.info(event.getStatusCode());
		
	}

}
