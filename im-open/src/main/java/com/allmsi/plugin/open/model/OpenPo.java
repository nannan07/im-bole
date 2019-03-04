package com.allmsi.plugin.open.model;

import java.util.Date;

public class OpenPo {

	private String id;

	private String token;

	private String code;

	private String objectId;

	private String receiveId;

	private String type;
	
	private int tCount;

	private int cCount;

	private String cUserId;

	private String uUserId;

	private Date cTime;

	private Date uTime;

	private int del;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int gettCount() {
		return tCount;
	}

	public void settCount(int tCount) {
		this.tCount = tCount;
	}

	public int getcCount() {
		return cCount;
	}

	public void setcCount(int cCount) {
		this.cCount = cCount;
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

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

}