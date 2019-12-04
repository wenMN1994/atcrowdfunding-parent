package com.dragon.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dragon.atcrowdfunding.bean.TMenu;
import com.dragon.atcrowdfunding.service.TMenuService;

/**
 * 
 * <p>Title: TMenuController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月2日
 */
@Controller
public class TMenuController {

	@Autowired
	TMenuService menuService;
	
	@RequestMapping("/menu/index")
	public String index() {
		
		return "menu/index";
	}
	
	@RequestMapping("/menu/loadTree")
	@ResponseBody
	public List<TMenu> loadTree() {
		List<TMenu> list = menuService.listMenuAllTree();
		return list;
	}
	
	@RequestMapping("/menu/addMenu")
	@ResponseBody
	public String addMenu(TMenu menu) {
		menuService.saveTMenu(menu);
		return "ok";
	}
	
	@RequestMapping("/menu/getMenuById")
	@ResponseBody
	public TMenu getMenuById(Integer id) {
		return menuService.getMenuById(id);
	}
	
	@RequestMapping("/menu/updateMenu")
	@ResponseBody
	public String updateMenu(TMenu menu) {
		menuService.updateTMenu(menu);
		return "ok";
	}
	
	@RequestMapping("/menu/deleteMenu")
	@ResponseBody
	public String deleteMenu(Integer id) {
		menuService.deleteTMenu(id);
		return "ok";
	}
}
