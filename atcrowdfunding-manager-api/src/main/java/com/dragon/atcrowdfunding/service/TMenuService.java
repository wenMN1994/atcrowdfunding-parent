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

	List<TMenu> listMenuAll();

}
