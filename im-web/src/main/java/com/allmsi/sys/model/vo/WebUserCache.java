package com.allmsi.sys.model.vo;

import java.io.Serializable;

import com.allmsi.sys.model.po.WebUser;

/**
 * 用户登录后返回的用户信息
 * @author sunnannan
 *
 */
public class WebUserCache implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;

	protected String userName;

	protected String token;

	public WebUserCache() {

	}

	public WebUserCache(WebUser user) {
		if (user != null) {
			this.id = user.getId();
			this.userName = user.getUserName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
