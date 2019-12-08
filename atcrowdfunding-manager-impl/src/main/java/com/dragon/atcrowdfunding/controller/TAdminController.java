package com.dragon.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.bean.TRole;
import com.dragon.atcrowdfunding.service.TAdminService;
import com.dragon.atcrowdfunding.service.TRoleService;
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
	
	@Autowired
	TRoleService roleService;
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum, 
			@RequestParam(value="pageSize", required=false, defaultValue="2")Integer pageSize,
			String condition,
			Model model) {
		log.debug("跳转用户列表。。。");
		log.debug("condition={}", condition);
		model.addAttribute("titleName", "用户维护");
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("condition", condition);
		
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
	
	@RequestMapping("/admin/deleteBatch")
	public String deleteBatch(String ids, Integer pageNum) {
		log.debug("批量删除用户={}",ids);
		List<Integer> idList = new ArrayList<Integer>();
		String[] split = ids.split(",");
		for (String idStr : split) {
			int id = Integer.parseInt(idStr);
			idList.add(id);
		}
		log.debug("idList={}",idList);
		adminService.deleteBatchUser(idList);
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	
	@RequestMapping("/admin/toAssign")
	public String toAssign(String id, Model model) {
		//1.查询所有角色
		List<TRole> allList =  roleService.listAllRole();
		//2.根据用户id查询已经拥有的id
		List<Integer> roleIdList = roleService.getRoleByAdminId(id);
		//3.将所有角色进行划分
		List<TRole> assignList = new ArrayList<TRole>();
		List<TRole> unAssignList = new ArrayList<TRole>();
		
		model.addAttribute("titleName", "用户维护");
		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);
		
		for (TRole role : allList) {
			if(roleIdList.contains(role.getId())) {//4.已分配角色
				assignList.add(role);
			} else {//5.未分配的角色
				unAssignList.add(role);
			}
		}
		return "admin/assignRole";
	}
	
	@RequestMapping("/admin/doAssign")
	@ResponseBody
	public String doAssign(Integer[] roleId, Integer adminId) {
		log.debug("adminId={}",adminId);
		for (Integer rid : roleId) {
			log.debug("roleId={}",rid);
		}
		roleService.saveAdminAndRoleRelationship(roleId,adminId);
		return "ok";
	}
	
	@RequestMapping("/admin/doUnAssign")
	@ResponseBody
	public String doUnAssign(Integer[] roleId, Integer adminId) {
		log.debug("adminId={}",adminId);
		for (Integer rid : roleId) {
			log.debug("roleId={}",rid);
		}
		roleService.deleteAdminAndRoleRelationship(roleId,adminId);
		return "ok";
	}
	
	
}
