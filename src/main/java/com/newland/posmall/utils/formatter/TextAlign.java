package com.newland.posmall.utils.formatter;

/**
 * 格式化时，可能的对齐方式
 * <p>
 * @author lance
 *
 * 2011-9-1
 */
public enum TextAlign {
	/**
	 * 左对齐
	 */
	LEFT,
	/**
	 * 右对齐
	 */
	RIGHT,
	/**
	 * 中心对齐
	 */
	CENTER;
	
	public String toString(){
		switch (ordinal()) {
		case 0:
			return "left";
		case 1:
			return "right";
		case 2:
			return "center";
			
		default:
			return "unknown";
		}
	}
}
