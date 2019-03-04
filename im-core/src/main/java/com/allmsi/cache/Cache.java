package com.allmsi.cache;

import java.util.Set;

/**
 * Cache implementation method
 * @author sunnannan
 *
 */
public interface Cache {

	/**
	 * set key-value into cache
	 * @param key 缓存唯一标识
	 * @param value 缓存内容 参数类型为String
	 * @return boolean
	 */
	boolean setString(String key, String value);

	/**
	 * set key-value into cache
	 * @param key 缓存唯一标识
	 * @param value 缓存内容 参数类型为String
	 * @param seconds 缓存失效时间
	 * @return boolean
	 */
	boolean setString(String key, String value, int seconds);

	/**
	 * set key-value into cache
	 * @param key 缓存唯一标识
	 * @param obj 缓存内容
	 * @return boolean
	 */
	boolean setObject(String key, Object obj);

	/**
	 * set key-value into cache
	 * @param key 缓存唯一标识
	 * @param obj 缓存内容
	 * @param seconds 缓存失效时间
	 * @return boolean
	 */
	boolean setObject(String key, Object obj, int seconds);

	/**
	 * get String value by key
	 * @param key 缓存唯一标识
	 * @return String
	 */
	String getString(String key);

	/**
	 * get Object value by key
	 * @param key 缓存唯一标识
	 * @return Object
	 */
	Object getObject(String key);

	/**
	 * key isExist in cache
	 * @param key 缓存唯一标识
	 * @return boolean
	 */
	boolean isExists(String key);

	/**
	 * delete key-value by key
	 * @param key 缓存唯一标识
	 * @return boolean
	 */
	boolean del(String key);

	/**
	 * reset valid time for key
	 * @param key 缓存唯一标识
	 * @param seconds
	 * @return
	 */
	long expire(String key, int seconds);

	/**
	 * Query keys with the specified content
	 * @param pattern
	 * @return
	 */
	Set<String> keys(String pattern);
}
