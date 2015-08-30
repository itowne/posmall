package com.newland.posmall.utils;
/**
 * 默认格式序列号生成器
 * <p>
 * 一般性而言，如果序列号生成器存在别的特性，例如：需要根据前缀生成序列号
 * 则该的接口将返回默认生成的序列号策略
 * 
 * 
 * @author lance
 *
 */
public interface IdentifierGenerator {
	
	public String generate();

}
