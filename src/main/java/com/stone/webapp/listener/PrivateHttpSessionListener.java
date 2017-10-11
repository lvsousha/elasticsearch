package com.stone.webapp.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class PrivateHttpSessionListener implements HttpSessionListener {

	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.info(se.getSession().getCreationTime()+":session 创建");
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.info(se.getSession().getCreationTime()+":session 销毁");
	}

}
