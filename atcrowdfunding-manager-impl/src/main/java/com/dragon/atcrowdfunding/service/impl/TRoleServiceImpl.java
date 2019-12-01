package com.dragon.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dragon.atcrowdfunding.bean.TRole;
import com.dragon.atcrowdfunding.bean.TRoleExample;
import com.dragon.atcrowdfunding.mapper.TRoleMapper;
import com.dragon.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * <p>Title: TRoleServiceImpl</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月1日
 */
@Service
public class TRoleServiceImpl implements TRoleService {
	
	@Autowired
	TRoleMapper roleMapper;

	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {
		String condition = (String)paramMap.get("condition");
		
		List<TRole> list = null;
		
		if(StringUtils.isEmpty(condition)) {
			list = roleMapper.selectByExample(null);
		}else {
			TRoleExample example = new TRoleExample();
			example.createCriteria().andNameLike("%"+condition+"%");
			list = roleMapper.selectByExample(example);
		}
		
		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		return page;
	}
}