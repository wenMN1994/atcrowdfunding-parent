package com.dragon.atcrowdfunding.service;

import java.util.List;

import com.dragon.atcrowdfunding.bean.TPermission;

/**
 * 
 * <p>Title: TPermissionService</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月4日
 */
public interface TPermissionService {

	List<TPermission> listPermissionAllTree();

	void saveTPermission(TPermission permission);

	TPermission getPermissionById(Integer id);

	void updateTPermission(TPermission permission);

	void updateTPermission(Integer id);

}
