package com.dragon.atcrowdfunding.controller;
/**
 * 
 * <p>Title: TRoleController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月1日
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dragon.atcrowdfunding.bean.TRole;
import com.dragon.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TRoleController {

	Logger log = LoggerFactory.getLogger(TRoleController.class);
	
	@Autowired
	TRoleService roleService;
	
	@RequestMapping("/role/index")
	public String index(Model model) {
		log.debug("跳转角色管理界面");
		model.addAttribute("titleName", "角色维护");
		return "role/index";
	}
	
	@RequestMapping("/role/loadData")
	@ResponseBody
	public PageInfo<TRole> loadData(@RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum, 
			@RequestParam(value="pageSize",required=false,defaultValue="2") Integer pageSize,
			@RequestParam(value="condition",required=false,defaultValue="") String condition) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("condition", condition);
		PageInfo<TRole> page = roleService.listRolePage(paramMap);
		return page;
	}
	
	@RequestMapping("/role/addRole")
	@ResponseBody
	public String addRole(TRole role) {
		log.debug("新增角色。。。");
		roleService.saveTRole(role);
		return "ok";
	}
	
	@RequestMapping("/role/getRoleById")
	@ResponseBody
	public TRole getRoleById(Integer id) {
		log.debug("根据角色Id查询角色。。。");
		TRole role = roleService.getRoleById(id);
		return role;
	}
	
	@RequestMapping("/role/updateRole")
	@ResponseBody
	public String updateRole(TRole role) {
		log.debug("修改角色。。。");
		roleService.updateTRole(role);
		return "ok";
	}
	
	@RequestMapping("/role/doDelete")
	@ResponseBody
	public String doDelete(Integer id) {
		log.debug("删除角色。。。");
		roleService.deleteTRole(id);
		return "ok";
	}
	
	@RequestMapping("/role/deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids) {
		log.debug("删除角色。。。");
		List<Integer> idList = new ArrayList<Integer>();
		String[] split = ids.split(",");
		for (String idStr : split) {
			int id = Integer.parseInt(idStr);
			idList.add(id);
		}
		log.debug("idList={}",idList);
		roleService.deleteTRole(idList);
		return "ok";
	}
}
