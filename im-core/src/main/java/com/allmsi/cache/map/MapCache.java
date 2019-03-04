package com.allmsi.cache.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.allmsi.cache.Cache;

/**
 * map cache
 * @author sunnannan
 *
 */
@Component
public class MapCache implements Cache {

	private static final MapCache instance = new MapCache();

	private Map<String, Object> objectMap = new HashMap<String, Object>();

	private Map<String, String> stringMap = new HashMap<String, String>();

	/**
	 * 为缓存设置有效时间，-1时永久有效
	 */
	private Map<String, Long> keys = new HashMap<String, Long>();

	@Override
	public boolean setObject(String key, Object obj) {
		return setObject(key, obj, -1);
	}

	@Override
	public boolean setObject(String key, Object obj, int seconds) {
		if (isEmpty(key) || obj == null || obj.toString() == "") {
			return false;
		}
		if (objectMap == null) {
			objectMap = new HashMap<String, Object>();
		}
		objectMap.put(key, obj);
		keyTime(key, seconds);
		return true;
	}

	@Override
	public boolean setString(String key, String value) {
		return setString(key, value, -1);
	}

	@Override
	public boolean setString(String key, String value, int seconds) {
		if (isEmpty(key) || isEmpty(value)) {
			return false;
		}
		if (stringMap == null) {
			stringMap = new HashMap<String, String>();
		}
		stringMap.put(key, value);
		keyTime(key, seconds);
		return true;
	}

	private long keyTime(String key, int seconds) {
		long time = System.currentTimeMillis() + seconds * 1000;
		if (-1 == seconds) {
			time = 1;
		}
		keys.put(key, time);
		return time;
	}

	@Override
	public String getString(String key) {
		if (isEmpty(key)) {
			return null;
		}
		return stringMap.get(key);
	}

	@Override
	public Object getObject(String key) {
		if (isEmpty(key)) {
			return null;
		}
		return objectMap.get(key);
	}

	@Override
	public boolean isExists(String key) {
		if (isEmpty(key)) {
			return false;
		}
		if (keys.containsKey(key)) {
			long time = System.currentTimeMillis();
			if (keys.get(key) >= time || 1 == keys.get(key)) {
				return true;
			}
			del(key);
		}
		return false;
	}

	@Override
	public boolean del(String key) {
		if (isEmpty(key)) {
			return false;
		}
		objectMap.remove(key);
		stringMap.remove(key);
		keys.remove(key);
		return true;
	}

	@Override
	public long expire(String key, int seconds) {
		if (isEmpty(key)) {
			if (keys.containsKey(key) && keys.get(key) != 1) {
				return keyTime(key, seconds);
			}
		}
		return 0;
	}

	@Override
	public Set<String> keys(String pattern) {
		Set<String> keys = new HashSet<String>();
		Set<String> keySet1 = objectMap.keySet();
		Set<String> keySet2 = stringMap.keySet();
		for (String keyName : keySet1) {
			if (keyName.indexOf(pattern) != -1) {
				keys.add(keyName);
			}
		}
		for (String keyName : keySet2) {
			if (keyName.indexOf(pattern) != -1) {
				keys.add(keyName);
			}
		}
		return keys;
	}

	private MapCache() {
	}

	public static MapCache getInstance() {
		return instance;
	}

	public boolean status() {
		if (getInstance() == null) {
			return false;
		}
		return true;
	}

	private boolean isEmpty(String key) {
		return key == null || "".equals(key.trim());
	}
}
