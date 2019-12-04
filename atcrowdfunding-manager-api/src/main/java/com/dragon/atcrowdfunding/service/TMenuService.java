package com.dragon.atcrowdfunding.service;

import java.util.List;

import com.dragon.atcrowdfunding.bean.TMenu;

/**
 * 
 * <p>Title: TMenuService</p>  
 * <p>Description: 后台菜单管理服务接口</p>  
 * @author Dragon.Wen
 * @date 2019年11月29日
 */
public interface TMenuService {

	List<TMenu> listMenuAll();//组合父子关系

	List<TMenu> listMenuAllTree();//不用组合父子关系

	void saveTMenu(TMenu menu);

	TMenu getMenuById(Integer id);

	void updateTMenu(TMenu menu);

	void deleteTMenu(Integer id);

}
