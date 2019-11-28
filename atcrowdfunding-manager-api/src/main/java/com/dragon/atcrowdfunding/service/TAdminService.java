package com.dragon.atcrowdfunding.service;

import java.util.Map;

import com.dragon.atcrowdfunding.bean.TAdmin;

/**
 * 
 * <p>Title: TAdminService</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年11月28日
 */
public interface TAdminService {

	/**
	 * 
	 * <p>Title: getTAdminByLogin</p>  
	 * <p>Description: 验证登录</p>  
	 * @param paramMap
	 * @return
	 */
	TAdmin getTAdminByLogin(Map<String, Object> paramMap);

}
