package org.ohuyo.rapid.base.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.AppSession;
import org.ohuyo.rapid.base.Auth;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;

/**
 * 
 * @author IBM
 *
 */
public class MenuTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8356289438122175932L;

	@Override
	public int doStartTag() throws JspException {
		String ctx = this.pageContext.getServletContext().getContextPath();
		StringBuilder sb = new StringBuilder();
		AppSession sess = AppSessionFilter.getAppSession();
		MenuNode node = sess.getMenuNode();
		sb.append("<ul class='tree treeFolder'>");
		if (node.getAuth() == null) {
			doMenu(sb, node.getSubNodes(), ctx);
		} else {
			doMenu(sb, node, ctx);
		}
		sb.append("</ul>");
		try {
			this.pageContext.getOut().append(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return super.doStartTag();
	}

	private void doMenu(StringBuilder sb, List<MenuNode> nodes, String ctx) {
		for (MenuNode menuNode : nodes) {
			doMenu(sb, menuNode, ctx);
		}
	}

	private void doMenu(StringBuilder sb, MenuNode node, String ctx) {
		Auth a = node.getAuth();
		List<MenuNode> sub = node.getSubNodes();
		sb.append("<li>");
		String url = a.getUrl();
		if (a != null) {
			String s = null;
			if (StringUtils.equals(url, "#")) {
				s = String.format("<a>%s</a>", a.getName());
			} else {
				s = String.format(
						"<a href='%s' target='navTab' rel='%s'>%s</a>", ctx
								+ url, a.getCode(), a.getName());
			}
			sb.append(s);
		}
		if (sub != null && !sub.isEmpty()) {
			sb.append("<ul class='navTab-tab'>");
			for (MenuNode menuNode : sub) {
				doMenu(sb, menuNode, ctx);
			}
			sb.append("</ul>");
		}
		sb.append("</li>");
	}

}
