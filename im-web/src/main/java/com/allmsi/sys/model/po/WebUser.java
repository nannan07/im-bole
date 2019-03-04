package com.allmsi.sys.model.po;

import java.util.Date;

import com.allmsi.sys.model.PageBean;
import com.allmsi.sys.model.vo.WebUserVo;

public class WebUser extends PageBean implements Cloneable {

	private String id;
	
	private String loginName;

	private String password;

	private String userName;

	private String phone;

	private String email;

	private Integer sex;

	private Integer sort;

	private Date cTime;

	private String cUserId;

	private String uUserId;

	// 用户对应角色
	private String roleId;

	private String roleName;

	public WebUser() {

	}

	public WebUser(WebUserVo userVo) {
		if (userVo != null) {
			this.id = userVo.getId();
			this.loginName = userVo.getLoginName();
			this.userName = userVo.getUserName();
			this.password = userVo.getPassword();
			this.phone = userVo.getPhone();
			this.email = userVo.getEmail();
			this.sex = userVo.getSex();
			this.sort = userVo.getSort();
			this.cUserId = userVo.getcUserId();
			this.uUserId = userVo.getuUserId();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	@Override
	public Object clone() {
		WebUser user = null;
		try {
			user = (WebUser) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return user;
	}
}