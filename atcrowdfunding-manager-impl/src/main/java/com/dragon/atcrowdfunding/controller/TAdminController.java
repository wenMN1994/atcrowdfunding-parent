package com.dragon.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.service.TAdminService;
import com.dragon.atcrowdfunding.util.AppDateUtils;
import com.dragon.atcrowdfunding.util.Const;
import com.dragon.atcrowdfunding.util.MD5Util;
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
	
	Logger log = LoggerFactory.getLogger(TAdminController.class);

	@Autowired
	TAdminService adminService;
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum, 
			@RequestParam(value="pageSize", required=false, defaultValue="2")Integer pageSize,
			Model model) {
		log.debug("跳转用户列表。。。");
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
		
		model.addAttribute("page", page);
		return "admin/index";
	}
	
	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		log.debug("跳转用户添加界面。。。");
		return "admin/add";
	}
	
	@RequestMapping("/admin/addUser")
	public String addUser(TAdmin admin) {
		log.debug("添加用户。。。");
		admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
		admin.setCreatetime(AppDateUtils.getFormatTime());
		adminService.saveUser(admin);
		return "redirect:/admin/index";
	}
	
	@RequestMapping("/admin/toEdit")
	public String toEdit(Integer id, Model model) {
		log.debug("跳转用户修改界面。。。");
		TAdmin admin = adminService.getTAdminById(id);
		model.addAttribute("admin", admin);
		return "admin/edit";
	}
	
	@RequestMapping("/admin/editUser")
	public String editUser(TAdmin admin, Integer pageNum) {
		log.debug("修改用户。。。");
		adminService.editUser(admin);
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id, Integer pageNum) {
		log.debug("删除用户。。。");
		adminService.deleteUser(id);
		return "redirect:/admin/index?pageNum="+pageNum;
	}
}