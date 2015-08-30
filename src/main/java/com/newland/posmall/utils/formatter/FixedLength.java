/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * FixedLength <p>
 * 定长格式化装饰器,定长判定通过<b>字节长度</b><p>
 * 正向生成时，若传入字符串的<b>字节长度</b>小于指定长度，将根据填充字符自动填充生成定长字符。<p>
 * 反向解析时，将自动脱除填充字符，还原原始字符串。
 * <p>
 * 若长度超过指定长度，将可能出现截取后填充的情况，例如:当指定长度为9时，输入字符串为<tt>"伟大的中国共产党"</tt>,
 * 且编码格式为"GBK",则输入将为"伟大的中",最后的字符将被填充，若填充字符为双字节，则总长将可能小于指定长度，这种条件下为8，即不被填充。
 * 但具体可能的长度，并无特别规则进行控制。
 * <p>
 * <b>
 * 因此，值得注意的是，由若填充字符为非单字节字符，可能将导致填充行为不受控制，可能等长，也可能小于指定长度。
 * 所以填充字符请使用ascii的单字符集。
 * </b><p>
 * <b>
 * 对于居中对齐{@link TextAlign#CENTER}的情况,就算填充字符为单字符，由于待填充字符数为奇数，也可能出现无法正确居中情况。
 * 一般来说，居中不对齐时，左边填充字符数将大于右边填充字符数。
 * </b>
 * <p>
 * 
 * 
 * Project: jasine.base <p>
 * 2004-7-11 <p>
 * 
 * <ul>
 * <li>改变原有利用整型判定对齐方式的模式，使用<tt>{@link TextAlign}</tt>来判定对齐</li>
 * <li>追加对多字符集的支持</li>
 * </ul>
 * 
 * 
 * @author shen,lance
 */
public class FixedLength extends Decorator {
	private Logger logger = LoggerFactory.getLogger(FixedLength.class);
	
	/**
	 * 支持的顶长格式化策略
	 */
    private final static String[] justifyNames  = {"left", "right", "center"};

    
    private TextAlign textAlign = TextAlign.LEFT;
    private int length;
    private char padCharacter=' ';
    private String charset = "GBK";
    
    protected FixedLength(String name) {
        super(name);
    }
    
    /**
     * 构造一个定长格式化装饰器<p>
     * 默认使用：长度-0,对齐方式-左对齐，填充字符-空格，编码格式-<tt>GBK</tt>方式构造。
     * 
     */
    public FixedLength() {
        super("FixedLength");
    }

    /** 
     * 构造一个定长格式化装饰器，默认使用<tt>GBK</tt>编码格式进行处理
     * 
     * @param length 长度
     * @param justify 对齐方式。0-左对齐，1-右对齐，2-中心对齐
     * @param padChar 填充字符
     * @deprecated 该方法已经被{@link #FixedLength(int, TextAlign, char)}替代
     */
    public FixedLength(int length, int justify, char padChar) {
    	this();
    	this.length = length;
    	setJustify(justify);
    	this.padCharacter = padChar;
    }
    /**
     * 构造一个定长格式化装饰器，默认使用<tt>GBK</tt>编码格式进行处理
     * 
     * @param length 长度
     * @param textAlign 对齐方式，参考{@link TextAlign}
     * @param padChar 填充字符
     */
    public FixedLength(int length,TextAlign textAlign,char padChar){
    	this();
    	this.length = length;
    	this.textAlign = textAlign;
    	this.padCharacter = padChar;   	
    }
    /**
     * 构造一个定长格式化装饰器
     * 
     * @param length 长度 
     * @param textAlign 对齐方式，参考{@link TextAlign}
     * @param padChar 填充字符
     * @param charset 编码格式
     */   
    public FixedLength(int length,TextAlign textAlign,char padChar,String charset){
    	this();
    	this.length = length;
    	this.textAlign = textAlign;
    	this.padCharacter = padChar;
    	this.charset = charset;
    }

    /**
     * @see com.jasine.format.Decorator#addDecoration(java.lang.String)
     */
    public String addDecoration(String aString) {
        if (aString == null) aString = "";

        char[] chars = aString.toCharArray();
        int i=0;
        int rl = 0;
        for (; i<chars.length; i++) {
            try {
				rl += ((Character)chars[i]).toString().getBytes(charset).length;
				if(i < chars.length - 1){
					int next = ((Character)chars[i+1]).toString().getBytes(charset).length;
					if(rl + next > getLength()){
						logger.debug("cut input["+aString+"] at char-index:"+i+",request:["+length+","+textAlign+","+charset+"]");
						break;
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new FormatterException(this,"不支持的字符集："+charset,e);
			}
        }
        int padlength = 0;
        try {
        	padlength = ((Character)padCharacter).toString().getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			throw new FormatterException(this,"不支持的字符集："+charset,e);
		}
        int leftPadLen = 0, rightPadLen = 0 ;
        switch (getAlign().ordinal()) {
        	case 0:
        	    rightPadLen = (getLength() - rl)/padlength;
        	    break;
        	case 1:
        	    leftPadLen = (getLength() - rl)/padlength;
        	    break;

        	case 2:
        	    rightPadLen = ((getLength() - rl)/2);
        	    rightPadLen = rightPadLen / padlength;
        	    
        	    leftPadLen = (getLength() - rl - rightPadLen*padlength);
        	    leftPadLen = leftPadLen /padlength;
        	    break;
        }

        StringBuffer sb = new StringBuffer(this.getLength());
        for (int j = 0; j < leftPadLen ; j++) {
            sb.append(this.getPadCharacter());
        }
        sb.append(chars, 0, i++);
        for (int j = 0; j < rightPadLen ; j++) {
            sb.append(this.getPadCharacter());
        }
        return sb.toString();
    }

    /**
     * @see com.jasine.format.Decorator#removeDecoration(java.lang.String)
     */
    public String removeDecoration(String aString) {
        if (aString == null) return null;
        if (' ' == this.getPadCharacter()) return aString.trim();
        char[] chars = aString.toCharArray();
        int startIndex = 0;
        int endIndex = chars.length-1;

        // 左对齐不进行处理
        for (; getAlign().ordinal() != 0 && startIndex<endIndex; startIndex++) {
            if (chars[startIndex] != this.getPadCharacter()) break;
        }

        // 右对齐不进行处理
        for (; getAlign().ordinal() != 1 && startIndex<=endIndex; endIndex--) {
            if (chars[endIndex] != this.getPadCharacter()) break;
        }
        if (startIndex >= endIndex) return "";
        return aString.substring(startIndex, endIndex+1);

    }

    /**
     * 按照字节长度抽取本格式化器对应的字符串和剩余的字符串
     *
     * @see com.jasine.format.Formatter#extract(java.lang.String)
     */
    public String[] extract(String aString) {
        if (aString == null) return null;
        byte[] bytes;
		try {
			bytes = aString.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new FormatterException(this, "不支持的字符集："+charset,e);
		}
        if (bytes.length <= length) return new String[]{aString};
        try {
			return new String[]{new String(bytes, 0, length,charset), new String(bytes, length, bytes.length-length,charset)};
		} catch (UnsupportedEncodingException e) {
			throw new FormatterException(this, "不支持的字符集："+charset,e);
		}
    }
    
    @Override
    public void setPattern(String pattern) {
    }
    /**
     * 返回对齐的判定方式
     * @return <ul><li>当为左对齐时，返回0</li><li>右对齐时，返回1</li><li>中心对齐时，返回2</li></ul>
     * @deprecated 该方法已经被{@link #getAlign()}替代
     */
    public int getJustify() {
        return textAlign.ordinal();
    }
    /**
     * 设置对齐方式，其中对齐方式为：
     * <ul>
     * 	<li><tt>0</tt>-左对齐</li>
     * 	<li><tt>1</tt>-右对齐</li>
     * 	<li><tt>2</tt>-中心对齐</li>
     * </ul>
     * @param justify 对齐方式
     * @deprecated 该方法已经被{@link #setAlign(TextAlign)}替代
     */
    public void setJustify(int justify) {
    	switch (justify){
    		case 0:
    			textAlign = TextAlign.LEFT;
    			break;
    		case 1:
    			textAlign = TextAlign.RIGHT;
    			break;
    		case 2:
    			textAlign = TextAlign.CENTER;
    			break;
    		default:
    			break;
    	}
    }

    /**
     * 设置对齐方式，其中对齐方式为：
     * <ul>
     * 	<li><tt>"left"</tt>左对齐</li>
     * 	<li><tt>"right"</tt>右对齐</li>
     * 	<li><tt>"center"</tt>中心对齐</li>
     * </ul>
     * @param align 对齐方式
     * @deprecated 该方法已经被{@link #setAlign(TextAlign)}替代
     */
    public void setAlign(String align) {
        for (int i=0; i<justifyNames.length; i++) {
            if (justifyNames[i].equalsIgnoreCase(align)) {
                setJustify(i);
                break;
            }
        }
    }
    
    /**
     * 设置对齐方式
     * @param textAlign 参见{@link TextAlign}
     */
    public void setAlign(TextAlign textAlign){
    	this.textAlign = textAlign;
    }
    public TextAlign getAlign(){
    	return this.textAlign;
    }
    
    /**
     * @return Returns the length.
     */
    public int getLength() {
        return length;
    }
    /**
     * @param length The length to set.
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @return Returns the padCharacter.
     */
    public char getPadCharacter() {
        return padCharacter;
    }
    /**
     * @param padCharacter The padCharacter to set.
     */
    public void setPadCharacter(char padCharacter) {
        this.padCharacter = padCharacter;
    }

	public void setCharset(String charset) {
		this.charset = charset;
	}
    
}
