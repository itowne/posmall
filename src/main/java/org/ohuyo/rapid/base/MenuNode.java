package org.ohuyo.rapid.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单类
 * 
 * @author rabbit
 *
 */
public class MenuNode {
	private Auth auth;
	private List<MenuNode> subNodes = new ArrayList<MenuNode>();
	
	private Map<Long, MenuNode> subMenus = new LinkedHashMap<Long, MenuNode>();

	public List<MenuNode> getSubNodes() {
		return subNodes;
	}

	public void setSubNodes(List<MenuNode> subNodes) {
		this.subNodes = subNodes;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public Map<Long, MenuNode> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Map<Long, MenuNode> subMenus) {
		this.subMenus = subMenus;
	}
	
	public void print(MenuNode node, StringBuilder sb, int deep) {
//		String deepStr = "";
//		for (int i = 0; i < deep; i++) {
//			deepStr = deepStr + "  ";
//		}
//		Auth auth = node.getMenu();
//		if (auth == null) {
//			sb.append(deepStr);
//			sb.append("null, null, size=0, total size=" + node.totalSize());
//		} else {
//			sb.append(deepStr);
//			sb.append(auth.getIauth() + ", " + auth.getAuthName() + ", size="
//					+ node.getSubMenus().size() + ", total size="
//					+ node.totalSize());
//		}
//		if (!node.getSubMenus().isEmpty()) {
//			for (MenuNode n : node.getSubMenus().values()) {
//				sb.append("\n");
//				print(n, sb, deep + 1);
//			}
//		}
	}

	public int totalSize() {
		int sum = 0;
		for (MenuNode node : subMenus.values()) {
			sum += node.totalSize();
		}
		sum += subMenus.size();
		return sum;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		print(this, sb, 0);
		return sb.toString();
	}
	
	
}
