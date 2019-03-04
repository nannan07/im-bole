package com.allmsi.sys.model.vo;

import java.io.Serializable;

import com.allmsi.sys.util.BaseTree;

/**
 * 菜单树，为用户显示可操作的菜单
 * @author sunnannan
 *
 */
public class WebMenuTree extends BaseTree<WebMenuTree> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String label;

	private String path;

	private String icon;

	private Integer sort;

	public WebMenuTree() {

	}

	public WebMenuTree(WebMenuVo webMenuVo) {
		if (webMenuVo != null) {
			this.setId(webMenuVo.getId());
			this.setpId(webMenuVo.getpId());
			this.label = webMenuVo.getLabel();
			this.path = webMenuVo.getPath();
			this.icon = webMenuVo.getIcon();
			this.sort = webMenuVo.getSort();
		}
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
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}