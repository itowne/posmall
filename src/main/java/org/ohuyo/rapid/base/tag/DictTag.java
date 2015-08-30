package org.ohuyo.rapid.base.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newland.posmall.biz.common.MapBiz;

@SuppressWarnings("serial")
public class DictTag extends TagSupport {

	private String dictCode;
	private String dictType;
	private String separator;
	private String outSeparator;

	@Override
	public int doStartTag() throws JspException {
		
		try {
			JspWriter jspWriter = this.pageContext.getOut();
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
	
			MapBiz mapBiz = (MapBiz) ctx.getBean("posmall.mapBiz");
			Map<String, String> map = mapBiz.getMapByType(dictType);
	
			String[] codes = dictCode.split(",");
			StringBuilder sb = new StringBuilder();
			boolean isfirst = true;
			for (String code : codes) {
				String v = map.get(code);
				if (v == null) {
					v = "";
				}
				if (isfirst) {
					isfirst = false;
				} else {
					sb.append(outSeparator);
				}
				sb.append(v);
			}
			jspWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getOutSeparator() {
		return outSeparator;
	}

	public void setOutSeparator(String outSeparator) {
		this.outSeparator = outSeparator;
	}

}
