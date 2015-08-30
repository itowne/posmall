/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;


/**
 * NullDecorator 对null的处理
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class NullDecorator extends Decorator {
    Object defaultObject = null;

    public NullDecorator(String name) {
        super(name);

    }
    @Override
    public void setPattern(String pattern) {
    }
    public NullDecorator() {
        super("NullDecorator");
    }
    /**
     * @see com.jasine.format.Decorator#addDecoration(java.lang.String)
     */
    public String addDecoration(String aString) {
        return aString;
    }

    /**
     * @see com.jasine.format.Decorator#removeDecoration(java.lang.String)
     */
    public String removeDecoration(String aString) {
        return aString;
    }

    /**
     * 如果输入对象为null, 则转换为空字符串
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        if (aObject == null) return "";
        else if (this.getDecorated() != null) {
            return this.getDecorated().format(aObject);
        } else return (String)aObject;
    }
    /**
     * 不进行额外处理
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        String result = removeDecoration(aString);
        if (result == null) return defaultObject;
        if (result.trim().equals("")) return defaultObject;
        if (this.getDecorated() != null)
            return this.getDecorated().unformat(result);
        else return result;
    }
    /**
     * @return Returns the defaultObject.
     */
    public Object getDefaultObject() {
        return defaultObject;
    }
    /**
     * @param defaultObject The defaultObject to set.
     */
    public void setDefaultObject(Object defaultObject) {
        this.defaultObject = defaultObject;
    }
}
