package com.dragon.atcrowdfunding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * <p>Title: DispatcherController.java</p>  
 * <p>Description: </p>
 * @author Dragon.Wen
 * @date 2019年11月28日  
 * @version 1.0
 */
@Controller
public class DispatcherController {

	Logger log = LoggerFactory.getLogger(DispatcherController.class);
	
	@RequestMapping("/index")
	public String index() {
		log.debug("跳转到系统主页面。。。");
		return "index";
	}
	
	
	@RequestMapping("/login")
	public String login() {
		log.debug("跳转到登录主页面。。。");
		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(String loginacct, String userpswd) {
		log.debug("开始登录。。。");
		
		log.debug("loginacct={}", loginacct);
		log.debug("userpswd={}", userpswd);
		
		return "main";
	}
}
