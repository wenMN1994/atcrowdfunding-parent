package com.dragon.atcrowdfunding.service;

import java.util.Map;

import com.dragon.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

/**
 * 
 * <p>Title: TRoleService</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月1日
 */
public interface TRoleService {

	PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

}
