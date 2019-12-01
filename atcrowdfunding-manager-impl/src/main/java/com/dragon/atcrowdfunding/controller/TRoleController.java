package com.dragon.atcrowdfunding.controller;
/**
 * 
 * <p>Title: TRoleController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月1日
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String index() {
		log.debug("跳转角色管理界面");
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
}
