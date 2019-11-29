package com.dragon.atcrowdfunding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * <p>Title: TAdminController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年11月29日
 */
@Controller
public class TAdminController {

	@RequestMapping("/admin/index")
	public String index() {
		
		return "admin/index";
	}
}
