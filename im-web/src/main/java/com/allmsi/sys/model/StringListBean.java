package com.allmsi.sys.model;

import java.util.ArrayList;
import java.util.List;

public class StringListBean {

	private String key;
	
	private List<String> values = new ArrayList<String>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
}
