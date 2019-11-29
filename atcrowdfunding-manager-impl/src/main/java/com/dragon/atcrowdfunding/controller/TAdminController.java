package com.dragon.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.service.TAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * <p>Title: TAdminController</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年11月29日
 */
@Controller
public class TAdminController {

	@Autowired
	TAdminService adminService;
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum, 
			@RequestParam(value="pageSize", required=false, defaultValue="2")Integer pageSize,
			Model model) {
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
		
		model.addAttribute("page", page);
		return "admin/index";
	}
}
