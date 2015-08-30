/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;


/**
 * Delimiter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class Delimiter extends Decorator {
    char delimiterChar = '|';
    public Delimiter(String name) {
        super(name);
    }
    public Delimiter() {
        super("Delimiter");
    }
        @Override
    public void setPattern(String pattern) {
    }
    /**
     * 根据分割符，将输入串分解为本数据域和其余的数据域对应的字符串
     * @see com.jasine.format.Formatter#extract(java.lang.String)
     */
    public String[] extract(String aString) {
        if (aString == null) return null;
        int sepIndex = aString.indexOf(delimiterChar);
        if (sepIndex == -1) return new String[]{aString};
        //else if (sepIndex >= aString.length()-1) return new String[] {aString};
        sepIndex++;
        return new String[]{aString.substring(0, sepIndex), aString.substring(sepIndex)};
    }
    /**
     * @return Returns the delimiterChar.
     */
    public char getDelimiterChar() {
        return delimiterChar;
    }
    /**
     * @param delimiterChar The delimiterChar to set.
     */
    public void setDelimiterChar(char delimiterChar) {
        this.delimiterChar = delimiterChar;
    }
    /**
     * @see com.jasine.format.Decorator#addDecoration(java.lang.String)
     */
    public String addDecoration(String aString) {
        if (aString == null) return String.valueOf(this.getDelimiterChar());
        return aString+this.getDelimiterChar();
    }

    /**
     * @see com.jasine.format.Decorator#removeDecoration(java.lang.String)
     */
    public String removeDecoration(String aString) {
        if (aString == null) return null;
        if ("".equals(aString)) return aString;
        if (aString.charAt(aString.length()-1) == this.getDelimiterChar())
            return aString.substring(0, aString.length()-1);
        else return aString;
    }
}
