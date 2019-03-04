package com.allmsi.sys.service;

/**
 * 系统登录和退出操作
 * @author sunnannan
 *
 */
public interface WebLoginService {

	Object login(String loginName,String password);

	Object logout(String token);
}
