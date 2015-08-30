package org.ohuyo.rapid.base.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TuserroleDao;
import org.ohuyo.rapid.base.dao.TuserDao;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	TuserDao tuserDao;

	@Resource
	TuserroleDao troleDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Tuser u = tuserDao.getByLoginName(username);
		if (u == null) {
			String message = "用户" + username + "不存在";
			logger.error("loginName {} not found.", username);
			throw new UsernameNotFoundException("用户名称或密码错误.");
		}

		List<Tuserrole> roles = troleDao.findByIuser(u.getIuser());
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		// 获得用户的角色
		for (Tuserrole r : roles) {
			GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl(
					r.getName());
			if (logger.isDebugEnabled()) {
				logger.debug("用户：[" + username + "]拥有角色：[" + r.getName()
						+ "],即spring security中的access");
			}
			auths.add(grantedAuthorityImpl);
		}
		return null;
	}
}
