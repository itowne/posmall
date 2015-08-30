/*
 * Created on 2004-9-12
 *
 * Project: CashManagement
 */
package com.newland.posmall.utils.formatter;


/**
 * Tokenizer
 * Project: CashManagement
 * @author shen
 *
 * 2004-9-12
 */
public class Tokenizer extends Decorator {
	char delimiterChar = '|';

	// 默认分割符
	String delimiters = "|\t,";
	char[] delimiterArray = new char[] {'|','\t',','};

    @Override
    public void setPattern(String pattern) {
    }

	/**
	 * @return Returns the delimiters.
	 */
	public String getDelimiters() {
		return delimiters;
	}

	/**
	 * @param delimiters The delimiters to set.
	 */
	public void setDelimiters(String delimiters) {
		this.delimiters = delimiters;
		delimiterArray = delimiters.toCharArray();
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
        if (aString == null) return " "+String.valueOf(this.getDelimiterChar());
        return aString+this.getDelimiterChar();
    }

    /**
     * @see com.jasine.format.Decorator#removeDecoration(java.lang.String)
     */
    public String removeDecoration(String aString) {
        if (aString == null) return null;
        if ("".equals(aString)) return aString;
        char c = aString.charAt(aString.length()-1);
        if (delimiters.indexOf(c) < 0) return aString;
        return aString.substring(0, aString.length()-1);
    }

    /**
     * 根据分割符，将输入串分解为本数据域和其余的数据域对应的字符串
     * @see com.jasine.format.Formatter#extract(java.lang.String)
     */
    public String[] extract(String aString) {
        if (aString == null) return null;

        char[] inCharArray = aString.toCharArray();
        StringBuffer result = new StringBuffer();
        boolean quote = false;
        int i = 0;
        for (i=0; i<inCharArray.length; i++) {
        	if (i == 0 && inCharArray[i] == '"') {
        		quote = true;
        		continue;
        	}
        	if (quote &&inCharArray[i] == '"') {
        		// skip next "
        		if (i+1 < inCharArray.length) {
        			i++;
        			// skip it
        			if (inCharArray[i] == '"')
        				continue;
        			else {
        				i--;
        				quote = false;
        				continue;
        			}
        		} else {
        			quote = false;
    				continue;
        		}
        	}

        	if (!quote && delimiters.indexOf(inCharArray[i])>=0)
        		break;

        	result.append(inCharArray[i]);
        }
        i++;
        if (i < inCharArray.length)
        	return new String[]{result.toString(), new String(inCharArray, i, inCharArray.length - i)};
        else return new String[]{result.toString()};
    }
}
