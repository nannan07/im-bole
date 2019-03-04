package com.allmsi.sys.model.po;

import java.util.Date;

public class WebRes {
	private String id;

	private String resUrl;

	private String name;

	private Integer flag;

	private String cUserId;

	private Date cTime;

	private String uUserId;

	private Date uTime;

	private Integer del;
	
	private String authDealId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getcUserId() {
		return cUserId;
	}

	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getuUserId() {
		return uUserId;
	}

	public void setuUserId(String uUserId) {
		this.uUserId = uUserId;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public String getAuthDealId() {
		return authDealId;
	}

	public void setAuthDealId(String authDealId) {
		this.authDealId = authDealId;
	}
}
