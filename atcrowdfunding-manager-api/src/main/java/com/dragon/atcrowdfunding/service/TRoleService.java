package com.dragon.atcrowdfunding.service;

import java.util.List;
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

	void saveTRole(TRole role);

	TRole getRoleById(Integer id);

	void updateTRole(TRole role);

	void deleteTRole(Integer id);

	void deleteTRole(List<Integer> idList);

	List<TRole> listAllRole();

	List<Integer> getRoleByAdminId(String id);

	void saveAdminAdnRoleRelationship(Integer[] roleId, Integer adminId);

	void deleteAdminAdnRoleRelationship(Integer[] roleId, Integer adminId);

}
