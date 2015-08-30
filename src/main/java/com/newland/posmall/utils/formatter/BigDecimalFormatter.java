/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;

import java.math.BigDecimal;


/**
 * BigDecimalFormatter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class BigDecimalFormatter extends NumberFormatter {
    
    public BigDecimalFormatter(String name) {
        super(name);
        
    }
    
    public BigDecimalFormatter() {
        super("BigDecimalFormatter");
    }
    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
    	if (aString == null || "".equals(aString))
    		return null;
    	BigDecimal bd = null;
    	try {
    		bd = new BigDecimal(aString);
    	} catch (Exception e) {
    	}
    	if (bd != null) 
    		return bd;
    	
        Object result = super.unformat(aString);
        if (result == null) return null;
        BigDecimal bigDec = new BigDecimal(((Number)result).doubleValue());
        return bigDec;
    }
    


}
