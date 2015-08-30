package org.ohuyo.rapid.base;

import org.ohuyo.rapid.base.dist.AuthType;

/**
 * 权限接口
 * 
 * @author rabbit
 *
 */
public interface Auth {

	Long getIauth();

	String getName();

	/**
	 * 唯一
	 * 
	 * @return
	 */
	String getCode();

	String getUrl();

	Long getParentIauth();

	AuthType getAuthType();
}
