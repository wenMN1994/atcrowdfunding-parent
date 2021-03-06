package com.dragon.atcrowdfunding.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dragon.atcrowdfunding.bean.TMenu;
import com.dragon.atcrowdfunding.service.TMenuService;
import com.dragon.atcrowdfunding.util.Datas;

/**
 * 
 * <p>Title: TMenuController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月2日
 */
@Controller
public class TMenuController {
	
	Logger log = LoggerFactory.getLogger(TMenuController.class);

	@Autowired
	TMenuService menuService;
	
	@RequestMapping("/menu/index")
	public String index(Model model) {
		model.addAttribute("titleName", "菜单维护");
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
	
	@RequestMapping("/menu/doAssignPermissionToMenu")
	@ResponseBody
	public String doAssignPermissionToMenu(Integer menuId, Datas ds) {
		log.debug("menuId={}",menuId);
		log.debug("permission={}",ds.getIds());
		menuService.saveAssignPermissionToMenu(menuId,ds.getIds());
		return "ok";
	}
	
	@RequestMapping("/menu/listPermissionIdByMenuId")
	@ResponseBody
	public List<Integer> listPermissionIdByMenuId(Integer menuId) {
		log.debug("menuId={}",menuId);

		List<Integer> list = menuService.listPermissionIdByMenuId(menuId);
		return list;
	}
}
