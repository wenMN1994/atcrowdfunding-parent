package com.dragon.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.bean.TMenu;
import com.dragon.atcrowdfunding.service.TAdminService;
import com.dragon.atcrowdfunding.service.TMenuService;
import com.dragon.atcrowdfunding.util.Const;

/**
 * 
 * <p>Title: DispatcherController.java</p>  
 * <p>Description: </p>
 * @author Dragon.Wen
 * @date 2019年11月28日  
 * @version 1.0
 */
@Controller
public class DispatcherController {

	Logger log = LoggerFactory.getLogger(DispatcherController.class);
	
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TMenuService menuService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		log.debug("跳转到系统主页面。。。");
		return "index";
	}
	
	
	@RequestMapping("/login")
	public String login() {
		log.debug("跳转到登录主页面。。。");
		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(String loginacct, String userpswd, HttpSession session, Model model) {
		log.debug("开始登录。。。");
		
		log.debug("loginacct={}", loginacct);
		log.debug("userpswd={}", userpswd);
		
		//1.获取参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		
		//2.验证登录
		try {
			TAdmin admin =  adminService.getTAdminByLogin(paramMap);
			session.setAttribute(Const.LOGIN_ADMIN, admin);
			log.debug("登录成功。。。");
			//3.页面跳转
			return "redirect:/main";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(Const.MESSAGE, e.getMessage());
			log.debug("登录失败。。。");
			return "login";
		}
			
	}
	
	@RequestMapping("/main")
	public String main(HttpSession session, Model model){
		log.debug("跳转到后台系统main首页。。。");
		model.addAttribute("titleName", "控制面板");
		//存放父菜单
		List<TMenu> menuList = menuService.listMenuAll();
		session.setAttribute("menuList", menuList);
		return "main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		log.debug("注销系统。。。");
		if(session != null) {
			session.removeAttribute(Const.LOGIN_ADMIN);
			session.invalidate();
		}
		return "redirect:/index";
	}

}
