package com.allmsi.sys.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.allmsi.sys.model.po.WebMenu;

/**
 * 菜单信息类
 * @author sunnannan
 *
 */
public class WebMenuVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String label;

	private String path;

	private String icon;

	private String pId;

	private Integer sort;

	private Date cTime;

	public WebMenuVo() {

	}

	public WebMenuVo(WebMenu webMenu) {
		if (webMenu != null) {
			this.id = webMenu.getId();
			this.label = webMenu.getLabel();
			this.path = webMenu.getPath();
			this.icon = webMenu.getIcon();
			this.pId = webMenu.getpId();
			this.sort = webMenu.getSort();
			this.cTime = webMenu.getcTime();
		}
	}

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
}