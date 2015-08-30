package com.newland.posmall.utils.formatter;

/**
 * FormatElement是所有格式化器的基类 <p>
 * 该方法并不保证setPattern后的格式化操作是线程安全的。<p>
 * 是否线程安全，决定于具体的实现类行为。<p>
 * 
 * Project: jasine.base<p>
 * 2004-7-11<p>
 * 
 * 代码重构<p>
 * <ul>
 * 	<li>剥离extract行为。</li>
 * </ul>
 * 2011-09-05<p>
 * 
 * @author shen
 */
public abstract class Formatter {
    /**
     * 格式化器名称
     */
    String name;
    
    public Formatter(String name) {
        this.name = name;
    }
    
    public Formatter() {
    	this("");
    }
  
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    public abstract String format(Object aObject);
    
    public abstract Object unformat(String aString);

    public abstract void setPattern(String pattern);
}
