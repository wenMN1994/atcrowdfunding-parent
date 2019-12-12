package com.dragon.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.dragon.atcrowdfunding.component.AccessDenidHandlerImpl;
import com.dragon.atcrowdfunding.component.AppPasswordEncoder;
import com.dragon.atcrowdfunding.component.SecurityUserDetailServiceImpl;
/**
 * 
 * <p>Title: AtcrowdFundingSecurityConfig</p>  
 * <p>Description: SpringSecurity的配置类</p>  
 * @author Dragon.Wen
 * @date 2019年12月12日
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AtcrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	SecurityUserDetailServiceImpl securityUserDetailServiceImpl;
	
	@Autowired
	AppPasswordEncoder appPasswordEncoder;
	
	@Autowired
	AccessDenidHandlerImpl accessDenidHandlerImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
		auth.userDetailsService(securityUserDetailServiceImpl).passwordEncoder(appPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http.authorizeRequests().antMatchers("/static/**","/welcome.jsp","/index").permitAll()
		.anyRequest().authenticated();//剩下都需要认证
		 
		// 用户登陆请求发给Security
		http.formLogin().loginPage("/toLogin") //【/toLogin】跳转登录页面的方法
		.usernameParameter("loginacct")
		.passwordParameter("userpswd")
		.loginProcessingUrl("/login") //【/login】对应登录表单action的值
		.defaultSuccessUrl("/main").permitAll();
		
		http.exceptionHandling().accessDeniedHandler(accessDenidHandlerImpl);
		 
		http.logout().logoutSuccessUrl("/index");
		
		http.csrf().disable();

	}
	
}
