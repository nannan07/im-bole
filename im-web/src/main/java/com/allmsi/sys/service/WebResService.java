package com.allmsi.sys.service;

/**
 * 
 * @author sunnannan
 *
 */
public interface WebResService {

	/**
	 * 将资源信息存入缓存
	 */
	void setResCache();
	
	/**
	 * 判断用户是否可以访问资源
	 * @param token
	 * @param resUrl
	 * @return
	 */
	boolean isVisit(String token, String resUrl);
}
