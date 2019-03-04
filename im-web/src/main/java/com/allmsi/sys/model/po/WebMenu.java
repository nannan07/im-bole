package com.allmsi.sys.model.po;

import java.util.Date;

public class WebMenu {

	private String id;

	private String label;

	private String path;

	private String icon;

	private String pId;

	private Integer sort;

	private Date cTime;

	private Date uTime;

	private Boolean del;
	
	private String authDealId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId == null ? null : pId.trim();
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

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public Boolean getDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	public String getAuthDealId() {
		return authDealId;
	}

	public void setAuthDealId(String authDealId) {
		this.authDealId = authDealId;
	}
}