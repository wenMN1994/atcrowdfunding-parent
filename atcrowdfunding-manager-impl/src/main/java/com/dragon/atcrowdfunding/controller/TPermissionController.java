package com.dragon.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String index(Model model) {
		model.addAttribute("titleName", "许可维护");
		return "permission/index";
	}
	
	@RequestMapping("/permission/loadTree")
	@ResponseBody
	public List<TPermission> loadTree() {
		List<TPermission> list = permissionService.listPermissionAllTree();
		return list;
	}
	
	@RequestMapping("/permission/doAdd")
	@ResponseBody
	public String doAdd(TPermission permission) {
		permissionService.saveTPermission(permission);
		return "ok";
	}
	
	@RequestMapping("/permission/getPermissionById")
	@ResponseBody
	public TPermission getPermissionById(Integer id) {
		return permissionService.getPermissionById(id);
	}
	
	@RequestMapping("/permission/doUpdate")
	@ResponseBody
	public String doUpdate(TPermission permission) {
		permissionService.updateTPermission(permission);
		return "ok";
	}
	
	@RequestMapping("/permission/doDetele")
	@ResponseBody
	public String doDetele(Integer id) {
		permissionService.updateTPermission(id);
		return "ok";
	}
}
