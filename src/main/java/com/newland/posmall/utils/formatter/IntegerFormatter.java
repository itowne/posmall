/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;


/**
 * IntegerFormatter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class IntegerFormatter extends NumberFormatter {
    public IntegerFormatter(String name) {
        super(name);
    }
    public IntegerFormatter() {
        super("IntegerFormatter");
        setPattern("0");
    }
    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        Object result = super.unformat(aString);
        if (result == null) return null;
        
        return new Integer(((Number)result).intValue());
    }
}
