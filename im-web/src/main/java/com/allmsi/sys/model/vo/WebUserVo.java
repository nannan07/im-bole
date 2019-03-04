package com.allmsi.sys.model.vo;

import java.util.Date;

import com.allmsi.sys.model.po.WebUser;

/**
 * 用户信息类
 * @author sunnannan
 *
 */
public class WebUserVo {
	
	protected String id;

	protected String userName;

	private String loginName;
	
	private String password;

	private String phone;

	private String email;

	private Integer sex;

	private Integer sort;

	private Date cTime;

	private String cUserId;

	private String uUserId;

	public WebUserVo() {

	}

	public WebUserVo(WebUser user) {
		if (user != null) {
			this.id = user.getId();
			this.userName = user.getUserName();
			this.phone = user.getPhone();
			this.email = user.getEmail();
			this.sex = user.getSex();
			this.sort = user.getSort();
			this.cTime = user.getcTime();
		}
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getcUserId() {
		return cUserId;
	}

	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}

	public String getuUserId() {
		return uUserId;
	}

	public void setuUserId(String uUserId) {
		this.uUserId = uUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
