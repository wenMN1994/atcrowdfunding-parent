package com.dragon.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dragon.atcrowdfunding.bean.TMenu;
import com.dragon.atcrowdfunding.bean.TPermissionMenuExample;
import com.dragon.atcrowdfunding.mapper.TMenuMapper;
import com.dragon.atcrowdfunding.mapper.TPermissionMenuMapper;
import com.dragon.atcrowdfunding.service.TMenuService;
/**
 * 
 * <p>Title: TMenuServiceImpl</p>  
 * <p>Description: 后台菜单管理服务接口实现类</p>  
 * @author Dragon.Wen
 * @date 2019年11月29日
 */
@Service
public class TMenuServiceImpl implements TMenuService {
	
	Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);
	
	@Autowired
	TMenuMapper menuMapper;
	
	@Autowired
	TPermissionMenuMapper permissionMenuMapper;

	@Override
	public List<TMenu> listMenuAll() {
		
		List<TMenu> menuList = new ArrayList<TMenu>();//之存放父菜单，但是将children属性赋值
		Map<Integer, TMenu> cache = new HashMap<Integer, TMenu>();
		
		List<TMenu> allList = menuMapper.selectByExample(null);
		
		for (TMenu tMenu : allList) {
			if(tMenu.getPid() == 0) {
				menuList.add(tMenu);
				cache.put(tMenu.getId(), tMenu);
			}
		}
		
		for (TMenu tMenu : allList) {
			
			if(tMenu.getPid() != 0) {
				Integer pid = tMenu.getPid();
				TMenu parent = cache.get(pid);
				parent.getChildren().add(tMenu);//根据子菜单pid查找父菜单id，然后根据父菜单children属性进行父子关系组合
			}
		}
		log.debug("菜单={}",menuList);
		return menuList;
	}

	@Override
	public List<TMenu> listMenuAllTree() {
		return menuMapper.selectByExample(null);
	}

	@Override
	public void saveTMenu(TMenu menu) {
		menuMapper.insertSelective(menu);
	}

	@Override
	public TMenu getMenuById(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTMenu(TMenu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public void deleteTMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void saveAssignPermissionToMenu(Integer menuId, List<Integer> ids) {
		//先删除之前分配过的，然后重新分配所有打勾的
		TPermissionMenuExample example = new TPermissionMenuExample();
		example.createCriteria().andMenuidEqualTo(menuId);
		permissionMenuMapper.deleteByExample(example);
		
		permissionMenuMapper.saveAssignPermissionToMenu(menuId,ids);
	}

	@Override
	public List<Integer> listPermissionIdByMenuId(Integer menuId) {
		return permissionMenuMapper.listPermissionIdByMenuId(menuId);
	}
	
}
