package com.allmsi.cache;

/**
 * cache type (已经存在的缓存类型)
 * @author sunnannan
 *
 */
public enum CacheEnum {
	//系统自带的map缓存
	MAP,
	
	//外部的redis缓存
	REDIS;
}
