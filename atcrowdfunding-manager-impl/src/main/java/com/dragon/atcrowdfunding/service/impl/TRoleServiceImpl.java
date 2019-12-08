package com.dragon.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dragon.atcrowdfunding.bean.TRole;
import com.dragon.atcrowdfunding.bean.TRoleExample;
import com.dragon.atcrowdfunding.mapper.TAdminRoleMapper;
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
	
	@Autowired
	TAdminRoleMapper adminRoleMapper;

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

	@Override
	public void saveTRole(TRole role) {
		roleMapper.insertSelective(role);
	}

	@Override
	public TRole getRoleById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTRole(TRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void deleteTRole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteTRole(List<Integer> idList) {
		roleMapper.deleteBatchUser(idList);
	}

	@Override
	public List<TRole> listAllRole() {
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> getRoleByAdminId(String id) {
		return adminRoleMapper.getRoleByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.saveAdminAndRoleRelationship(roleId,adminId);
	}

	@Override
	public void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.deleteAdminAndRoleRelationship(roleId,adminId);
	}
}
