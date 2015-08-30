package org.ohuyo.rapid.base;

/**
 * 
 * @author rabbit
 *
 */
public interface AppSession {

	boolean hasUrlAuth(String reqUrl);
	
	boolean hasAuthCode(String authCode);

	SessionType getSessionType();

	MenuNode getMenuNode();

	String getIpAddr();

}
