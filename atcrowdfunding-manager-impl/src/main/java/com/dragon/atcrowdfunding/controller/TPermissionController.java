package com.dragon.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dragon.atcrowdfunding.bean.TMenu;
import com.dragon.atcrowdfunding.bean.TPermission;
import com.dragon.atcrowdfunding.service.TPermissionService;

/**
 * 
 * <p>Title: TPermissionController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月4日
 */
@Controller
public class TPermissionController {

	@Autowired
	TPermissionService permissionService;
	
	@RequestMapping("/permission/index")
	public String index() {
		
		return "permission/index";
	}
	
	@RequestMapping("/permission/loadTree")
	@ResponseBody
	public List<TPermission> loadTree() {
		List<TPermission> list = permissionService.listPermissionAllTree();
		return list;
	}
}
