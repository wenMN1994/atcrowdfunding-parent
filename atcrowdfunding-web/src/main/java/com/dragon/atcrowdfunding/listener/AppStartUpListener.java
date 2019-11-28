package com.dragon.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dragon.atcrowdfunding.util.Const;
/**
 * 
 * @author DragonWen
 *
 * 应用启动监听器
 * 应用启动的时候保存一些共享的数据信息
 */
public class AppStartUpListener implements ServletContextListener {

	  Logger log = LoggerFactory.getLogger(getClass());  
	  /**
	   * 项目启动调用
	   */
	  @Override
	  public void contextInitialized(ServletContextEvent sce) {
	    ServletContext servletContext = sce.getServletContext();        
	    String contextPath = servletContext.getContextPath();//   /atcrowdfunding-web
	    //保存项目路径
	    servletContext.setAttribute(Const.PATH, contextPath);
	    log.info("项目启动完成....");
	  }
	  /**
	   * 项目停止调用
	   */
	  @Override
	  public void contextDestroyed(ServletContextEvent sce) {
		  log.info("项目销毁....");
	  }  

}
