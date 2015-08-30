package org.ohuyo.rapid.base.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;

@SuppressWarnings("serial")
public class AuthButtonTag extends TagSupport {

	private String authCode;
	private String type;
	private String displayVal;
	private String onclick;

	@Override
	public int doStartTag() throws JspException {

		try {
			JspWriter jspWriter = this.pageContext.getOut();
			StringBuilder sb = new StringBuilder();

			sb.append("<button ");
			
			boolean flag = AppSessionFilter.getAppSession().hasAuthCode(authCode);
			if (flag) {
				if (StringUtils.isNotBlank(type)) {
					sb.append(" type=\"").append(type).append("\"");
				}
				if (StringUtils.isNotBlank(onclick)) {
					sb.append(" onclick=\"").append(onclick).append("\"");
				}
			} else {
				sb.append(" type=\"button\" onclick=\"return alertMsg.info(\'您没有该按钮的权限！\');\" ");
			}

			sb.append(">");

			if (StringUtils.isNotBlank(displayVal)) {
				sb.append(displayVal);
			}
			sb.append("</button>");

			jspWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayVal() {
		return displayVal;
	}

	public void setDisplayVal(String displayVal) {
		this.displayVal = displayVal;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

}
