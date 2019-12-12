package com.dragon.atcrowdfunding.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dragon.atcrowdfunding.bean.TAdmin;
import com.dragon.atcrowdfunding.bean.TAdminExample;
import com.dragon.atcrowdfunding.bean.TPermission;
import com.dragon.atcrowdfunding.bean.TRole;
import com.dragon.atcrowdfunding.bean.TSecurityAdmin;
import com.dragon.atcrowdfunding.mapper.TAdminMapper;
import com.dragon.atcrowdfunding.mapper.TPermissionMapper;
import com.dragon.atcrowdfunding.mapper.TRoleMapper;

/**
 * 
 * <p>
 * Title: SecurityUserDetailServiceImpl
 * </p>
 * <p>
 * Description: AccessDeniedHandler
 * </p>
 * 
 * @author Dragon.Wen
 * @date 2019年12月12日
 */
@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TAdminMapper adminMapper;

	@Autowired
	TRoleMapper roleMapper;

	@Autowired
	TPermissionMapper permissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1、查询用户
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example);

		if (!list.isEmpty() && list.size() == 1) {
			TAdmin tAdmin = list.get(0);
			// 2、将这个用户对应的所有角色和权限全部查询出来
			List<TRole> hasRoles = roleMapper.getRoleByAdminId(tAdmin.getId());
			logger.debug("SpringSecurity将  {} 用户信息去数据库查询", tAdmin.getLoginacct());
			// 3、这个用户对应的所有权限
			List<TPermission> permissions = permissionMapper.getPermissionsByAdminId(tAdmin.getId());

			Set<GrantedAuthority> authorities = new HashSet<>();

			// 4、将角色添加到权限列表
			for (TRole role : hasRoles) {
				if (!StringUtils.isEmpty(role.getName())) {
					authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
					// hasRole("管理员") ROLE_管理员
				}
			}

			// 5、将权限添加到权限列表
			for (TPermission perm : permissions) {
				if (!StringUtils.isEmpty(perm.getName())) {
					authorities.add(new SimpleGrantedAuthority(perm.getName()));
					// hasRole("管理员") ROLE_管理员
				}
			}
			logger.debug("SpringSecurity  {} 用户信息收集完成", tAdmin.getLoginacct());
			return new TSecurityAdmin(tAdmin, authorities);
		}
		// 用户查不出返回空即可
		return null;
	}

}
