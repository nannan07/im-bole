package com.allmsi.sys.model;

/**
 * 分页
 * @author sunnannan
 *
 */
public class PageBean {

	private int pageSize;

	private int page;

	public PageBean() {

	}

	public PageBean(PageBean pageBean) {
		if (pageBean != null) {
			this.pageSize = pageBean.pageSize;
			this.page = pageBean.page;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (this.page > 0) {
			this.page = (this.page - 1) * pageSize;
		}
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(int page) {
		if (this.pageSize > 0) {
			this.page = (page - 1) * pageSize;
		} else {
			this.page = page;
		}
	}
}
