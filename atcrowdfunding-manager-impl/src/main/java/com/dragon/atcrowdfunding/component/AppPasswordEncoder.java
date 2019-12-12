package com.dragon.atcrowdfunding.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dragon.atcrowdfunding.util.MD5Util;
/**
 * 
 * <p>Title: AppPasswordEncoder</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月12日
 */
@Component
public class AppPasswordEncoder implements PasswordEncoder {

	/**
	 * 密码加密的算法
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		String digestPwd = MD5Util.digest(rawPassword.toString());
		return digestPwd;
	}

	/**
	 * 比较登录密码和数据库存储密码是否一致
     * rawPassword:页面的明文密码
     * encodedPassword：数据库的密文密码
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//使用自己的工具类
		String digestPwd = MD5Util.digest(rawPassword.toString());
		return digestPwd.equals(encodedPassword);
	}

}
