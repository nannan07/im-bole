package com.allmsi.test.model;

import java.util.List;

public class UrlTree {
	
	private String label;
	
	private List<UrlTree> children;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<UrlTree> getChildren() {
		return children;
	}

	public void setChildren(List<UrlTree> children) {
		this.children = children;
	}

}
