package org.ohuyo.rapid.base.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;

@SuppressWarnings("serial")
public class AuthATag extends TagSupport {

	private String authCode;
	private String rel;
	private String cssClass;
	private String href;
	private String value;
	private String title;
	private String target;
	private String mask;
	private String warn;
	private String width;
	private String height;
	private String id;
	private String onclick;

	@Override
	public int doStartTag() throws JspException {

		try {
			JspWriter jspWriter = this.pageContext.getOut();
			StringBuilder sb = new StringBuilder();
			sb.append("<a ");

			if (StringUtils.isNotBlank(cssClass)) {
				sb.append(" class=\"").append(cssClass).append("\"");
			}

			if (StringUtils.isNotBlank(id)) {
				sb.append(" id=\"").append(id).append("\"");
			}

			boolean flag = AppSessionFilter.getAppSession().hasAuthCode(authCode);
			if (flag) {
				if (StringUtils.isNotBlank(rel)) {
					sb.append(" rel=\"").append(rel).append("\"");
				}
				if (StringUtils.isNotBlank(title)) {
					sb.append(" title=\"").append(title).append("\"");
				}
				if (StringUtils.isNotBlank(target)) {
					sb.append(" target=\"").append(target).append("\"");
				}
				if (StringUtils.isNotBlank(mask)) {
					sb.append(" mask=\"").append(mask).append("\"");
				}
				if (StringUtils.isNotBlank(warn)) {
					sb.append(" warn=\"").append(warn).append("\"");
				}
				if (StringUtils.isNotBlank(width)) {
					sb.append(" width=\"").append(width).append("\"");
				}
				if (StringUtils.isNotBlank(height)) {
					sb.append(" height=\"").append(height).append("\"");
				}
				if (StringUtils.isNotBlank(onclick)) {
					sb.append(" onclick=\"").append(onclick).append("\"");
				}
				if (StringUtils.isNotBlank(href)) {
					sb.append(" href=\"").append(href).append("\"");
				}
			} else {
				sb.append(" href=\"javascript:void(0);\" onclick=\"return alertMsg.info(\'您没有该按钮的权限！\');\" ");
			}
			sb.append(">");
			if (StringUtils.isNotBlank(value)) {
				sb.append("<span>").append(value).append("</span>");
			}
			sb.append("</a>");
			jspWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
