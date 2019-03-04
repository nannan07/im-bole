package com.allmsi.sys.model.vo;

/**
 * 用户登录时接收参数的实体类
 * @author sunnannan
 *
 */
public class WebLoginVo {

	private String loginName;

	private String password;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
