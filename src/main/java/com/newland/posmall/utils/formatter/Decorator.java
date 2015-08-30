/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;


/**
 * Decorator
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public abstract class Decorator extends Formatter {
    private Formatter decorated;
    
    public Decorator(String name) {
        super(name);
    }
    
    public Decorator() {
        super("Decorator");
    }
    /**
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        String result = null;
        if(decorated != null)
            result = decorated.format(aObject);
        else if (aObject != null) {
            result = aObject.toString();
        }
        return addDecoration(result);
    }

    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        if(decorated != null) 
            return decorated.unformat(removeDecoration(aString));
        else 
            return removeDecoration(aString);
    }


    
    public Formatter getDecorated() {
        return decorated;
    }
    public void setDecorated(Formatter decorated) {
        this.decorated = decorated;
    }
    
    public abstract String addDecoration(String aString);
    public abstract String removeDecoration(String aString);
}
