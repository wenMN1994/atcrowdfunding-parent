package com.dragon.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dragon.atcrowdfunding.bean.TPermission;
import com.dragon.atcrowdfunding.mapper.TPermissionMapper;
import com.dragon.atcrowdfunding.service.TPermissionService;
/**
 * 
 * <p>Title: TPermissionServiceImpl</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月4日
 */
@Service
public class TPermissionServiceImpl implements TPermissionService {
	
	@Autowired
	TPermissionMapper permissionMapper;

	@Override
	public List<TPermission> listPermissionAllTree() {
		return permissionMapper.selectByExample(null);
	}
}
