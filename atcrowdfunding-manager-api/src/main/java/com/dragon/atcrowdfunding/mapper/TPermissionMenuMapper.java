package com.dragon.atcrowdfunding.mapper;

import com.dragon.atcrowdfunding.bean.TPermissionMenu;
import com.dragon.atcrowdfunding.bean.TPermissionMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPermissionMenuMapper {
    long countByExample(TPermissionMenuExample example);

    int deleteByExample(TPermissionMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPermissionMenu record);

    int insertSelective(TPermissionMenu record);

    List<TPermissionMenu> selectByExample(TPermissionMenuExample example);

    TPermissionMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPermissionMenu record, @Param("example") TPermissionMenuExample example);

    int updateByExample(@Param("record") TPermissionMenu record, @Param("example") TPermissionMenuExample example);

    int updateByPrimaryKeySelective(TPermissionMenu record);

    int updateByPrimaryKey(TPermissionMenu record);

	void saveAssignPermissionToMenu(@Param("menuId") Integer menuId, @Param("ids") List<Integer> ids);

	List<Integer> listPermissionIdByMenuId(Integer menuId);
}