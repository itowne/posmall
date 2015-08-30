package org.ohuyo.rapid.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.dist.AuthType;
import org.springframework.stereotype.Service;

/**
 * 权限服务
 * 
 * @author rabbit
 *
 */
@Service
public class AuthService {

	/**
	 * 将权限LIST转换为MAP
	 * 
	 * @param auths
	 * @param urlMap
	 * @param codeMap
	 */
	public static void authMap(List<? extends Auth> auths,
			Map<String, Auth> urlMap, Map<String, Auth> codeMap) {
		for (Auth a : auths) {
			if (urlMap != null) {
				String url = a.getUrl();
				if (StringUtils.isNotBlank(url)) {
					url = url.trim();
					if (!StringUtils.equals(url, "#")) {
						urlMap.put(url, a);
					}
				}
			}
			if (codeMap != null) {
				String code = a.getCode();
				codeMap.put(code, a);
			}
		}
	}

	public static void setAuth2Session(List<? extends Auth> all,
			List<Auth> allow) {
		Map<String, Auth> allUrlMap = new HashMap<String, Auth>();
		Map<String, Auth> allCodeMap = new HashMap<String, Auth>();
		authMap(all, allUrlMap, allCodeMap);

		Map<String, Auth> allowUrlMap = new HashMap<String, Auth>();
		Map<String, Auth> allowCodeMap = new HashMap<String, Auth>();
		authMap(all, allowUrlMap, allowCodeMap);

		MenuNode menuNode = getMenuNode(allow);
	}

	public static MenuNode getMenuNode(List<? extends Auth> auths) {
		Map<Long, MenuNode> menuMap = new HashMap<Long, MenuNode>();
		MenuNode top = null;
		for (Auth a : auths) {
			if (a.getAuthType() != AuthType.MENU) {
				continue;
			}
			Long iauth = a.getIauth();
			MenuNode m = menuMap.get(iauth);
			if (m == null) {
				m = new MenuNode();
				menuMap.put(iauth, m);
			}
			m.setAuth(a);

			Long piauth = a.getParentIauth();
			MenuNode pm = menuMap.get(piauth);
			if (pm == null) {
				pm = new MenuNode();
				if (piauth == null || piauth == 0) {
					top = pm;
				}
				menuMap.put(piauth, pm);
			}
			pm.getSubNodes().add(m);
		}
		return top;
	}
}
