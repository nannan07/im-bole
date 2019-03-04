package com.allmsi.sys.config;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.allmsi.cache.Cache;
import com.allmsi.cache.CacheFactory;
import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.model.vo.WebUserCache;

@Component
public class CacheInstence {
	
	@Resource
	private CacheFactory cacheFactory;

	public Cache getCache() {
		return cacheFactory.getCache();
	}

	public WebUserCache getUser(String token) {
		WebUserCache userVo = (WebUserCache) getCache().getObject(CacheKeyPrefix.TOKEN.getValue() + token);
		return userVo;
	}

	public String getUserId(String token) {
		return getUser(token).getId();
	}

	public boolean del(String key) {
		return getCache().del(key);
	}

	public void setObject(String key, Object value) {
		getCache().setObject(key, value);
	}
	
	public void setObject(String key, Object value, int iSessionOut) {
		getCache().setObject(key, value, iSessionOut);
	}

	public Object getObject(String key) {
		return getCache().getObject(key);
	}

	public boolean isExists(String key) {
		return getCache().isExists(key);
	}
}
