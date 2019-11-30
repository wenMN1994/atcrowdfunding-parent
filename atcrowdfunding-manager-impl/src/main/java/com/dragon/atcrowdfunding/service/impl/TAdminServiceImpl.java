package com.dragon.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.bean.TAdminExample;
import com.dragon.atcrowdfunding.exception.LoginException;
import com.dragon.atcrowdfunding.mapper.TAdminMapper;
import com.dragon.atcrowdfunding.service.TAdminService;
import com.dragon.atcrowdfunding.util.Const;
import com.dragon.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;

/**
 * 
 * <p>Title: TAdminServiceImpl</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年11月28日
 */
@Service
public class TAdminServiceImpl implements TAdminService {
	
	@Autowired
	TAdminMapper adminMapper;

	@Override
	public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
		//1.密码加密
		
		//2.查询用户
		String loginacct = (String) paramMap.get("loginacct");
		String userpswd = (String) paramMap.get("userpswd");
		
		//3.判断用户是否为空
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		if(list == null || list.size() == 0) {
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}
		
		TAdmin admin = list.get(0);
		
		//4.判断密码是否一致
		if(!admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
			throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
		}
		
		//5.返回结果
		return admin;
	}

	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {
		
		TAdminExample example = new TAdminExample();
		
		example.setOrderByClause("createtime desc");
		
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		PageInfo<TAdmin> page = new PageInfo<TAdmin>(list, 5);
		
		return page;
	}

	@Override
	public void saveUser(TAdmin admin) {
		adminMapper.insert(admin);
	}

	@Override
	public TAdmin getTAdminById(Integer id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void editUser(TAdmin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void deleteUser(Integer id) {
		adminMapper.deleteByPrimaryKey(id);
	}
		
}
