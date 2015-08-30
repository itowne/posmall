package org.ohuyo.rapid.base.security;

import java.util.Map;
import java.util.Set;

public interface IUrlAuthService {

	/**
	 * 生成授权需要的资源权限定义列表
	 * 
	 * key: 受限访问的URL
	 * set: 允许访问的角色列表
	 * @return
	 */
	Map<String, Set<String>> getUrlAuthorities();

}
