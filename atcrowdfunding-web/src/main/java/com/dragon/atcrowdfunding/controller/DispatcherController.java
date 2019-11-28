package com.dragon.atcrowdfunding.controller;

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

	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
	
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
}
