package org.ohuyo.rapid.base.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newland.posmall.biz.common.MapBiz;

@SuppressWarnings("serial")
public class DictSelectTag extends TagSupport {

	private String selectName;
	private String dictType;
	private String defaultValue;
	private String cssClass;
	private String style;
	private String needAll;
	
	@Override
	public int doStartTag() throws JspException {
		
		try {
			JspWriter jspWriter = this.pageContext.getOut();
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
	
			MapBiz mapBiz = (MapBiz) ctx.getBean("posmall.mapBiz");
			Map<String, String> map = mapBiz.getMapByType(dictType);

			StringBuilder sb = new StringBuilder();
			sb.append("<select name=\"").append(selectName).append("\"");
			
			if(StringUtils.isNotBlank(cssClass)){
				sb.append(" class=\"").append(cssClass).append("\"");
			}
			
			if(StringUtils.isNotBlank(style)){
				sb.append(" style=\"").append(style).append("\"");
			}
			
			sb.append(">");
			
			if(!StringUtils.equals("NO", needAll)){
				sb.append("<option value=\"\">---请选择---</option>");
			}
			
			if(map!=null&&map.size()>0){
				for (String key : map.keySet()) {
					sb.append("<option value=\"").append(key).append("\"");
					if(StringUtils.equals(key, defaultValue)){
						sb.append(" selected=\"selected\">");
					}else{
						sb.append(">");
					}
					sb.append(map.get(key));
					sb.append("</option>");
				}
			}
			sb.append("</select>");
			jspWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getNeedAll() {
		return needAll;
	}

	public void setNeedAll(String needAll) {
		this.needAll = needAll;
	}

}
