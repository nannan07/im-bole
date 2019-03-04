package com.allmsi.cache;

/**
 * cache keyPrefix
 * @author sunnannan
 *
 */
public enum CacheKeyPrefix {

	TOKEN("用户标识", "TOKEN_"), 
	MENU("菜单标识", "MENU_"), 
	RES("资源标识", "RES_");

	private String name;

	private String value;

	private CacheKeyPrefix() {

	}

	private CacheKeyPrefix(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
