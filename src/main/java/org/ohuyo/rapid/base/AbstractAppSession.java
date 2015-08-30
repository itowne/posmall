package org.ohuyo.rapid.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

/**
 * 
 * @author rabbit
 * 
 */
public abstract class AbstractAppSession implements AppSession, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 需要权限的url
	 */
	private Map<String, Auth> allUrlMap = new HashMap<String, Auth>();

	private Map<String, Auth> allCodeMap = new HashMap<String, Auth>();
	/**
	 * 允许访问的url
	 */
	private Map<String, Auth> allowUrlMap = new HashMap<String, Auth>();

	private Map<String, Auth> allowCodeMap = new HashMap<String, Auth>();

	/**
	 * 菜单
	 */
	private MenuNode menuNode;

	private String ipAddr;

	public boolean hasUrlAuth(String reqUrl) {
		if (allUrlMap.isEmpty()) {
			return true;
		}

		if (!allUrlMap.containsKey(reqUrl)) {
			return true;
		}

		return allowUrlMap.containsKey(reqUrl);
	}

	public boolean hasAuthCode(String authCode) {
		if (allCodeMap.isEmpty()) {
			return true;
		}

		if (!allCodeMap.containsKey(authCode)) {
			return true;
		}

		return allowCodeMap.containsKey(authCode);
	}

	final public MenuNode getMenuNode() {
		return menuNode;
	}

	final public void setAuth(List<? extends Auth> allAuth,
			List<? extends Auth> allowAuth) {
		AuthService.authMap(allAuth, allUrlMap, allCodeMap);
		AuthService.authMap(allowAuth, allowUrlMap, allowCodeMap);
		menuNode = AuthService.getMenuNode(allowAuth);
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

}