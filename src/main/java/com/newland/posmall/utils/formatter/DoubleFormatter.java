/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;



/**
 * DoubleFormatter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class DoubleFormatter extends NumberFormatter {
    
    public DoubleFormatter(String name) {
        super(name);
    }
    public DoubleFormatter() {
        super("DoubleFormatter");
    }
    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        Object result = super.unformat(aString);
        if (result == null) return null;
        if (result instanceof Double) return result;
        return new Double(((Number)result).doubleValue());
    }
}
