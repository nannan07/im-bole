package com.allmsi.plugin.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.allmsi.plugin.cache.redis.service.RedisCacheService;
import com.allmsi.sys.util.StrUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis cache
 * 
 * @author sunnannan
 *
 */
@Service("com.allmsi.plugin.cache.redis.RedisCache")
public class RedisCache implements RedisCacheService {

	@Resource
	private JedisPool jedisPool;

	private Jedis getJedis() {
		// 如果是网络超时则多试几次
		for (int i = 0; i < 3; i++) {
			try {
				Jedis jedis = jedisPool.getResource();
				return jedis;
			} catch (Exception e) {
				// 底层原因是SocketTimeoutException，不过redis已经捕捉且抛出JedisConnectionException，不继承于前者
				if (!(e instanceof JedisConnectionException || e instanceof SocketTimeoutException)) {
					break;
				}
			}
		}
		return null;
	}

	/**
	 * 存储value
	 */
	public boolean setObject(String key, Object obj) {
		return setObject(key, obj, -1);
	}

	public boolean setObject(String key, Object obj, int seconds) {
		if (StrUtil.isEmpty(key) || obj == null || obj.toString() == "") {
			return false;
		}
		byte[] bytes = serialize(obj);
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key.getBytes(), bytes);
			if (-1 != seconds) {
				expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public boolean setString(String key, String value) {
		return setString(key, value, -1);
	}

	public boolean setString(String key, String value, int seconds) {
		if (StrUtil.isEmpty(key) || StrUtil.isEmpty(value)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String result = jedis.set(key, value);
			if (StrUtil.isEmpty(result)) {
				return false;
			}
			if (-1 != seconds) {
				expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}

	/**
	 * 获取value
	 */
	public String getString(String key) {
		if (StrUtil.isEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	public Object getObject(String key) {
		if (StrUtil.isEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		byte[] bytes = null;
		try {
			jedis = getJedis();
			bytes = jedis.get(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return unserialize(bytes);
	}

	/**
	 * 设置有效时间
	 */
	public long expire(String key, int seconds) {
		if (StrUtil.isEmpty(key)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}

	/**
	 * 删除缓存
	 */
	public boolean del(String key) {
		if (StrUtil.isEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
			return true;
		} catch (Exception ex) {

		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	/**
	 * 判断缓存中是否存在key
	 */
	public boolean isExists(String key) {
		if (StrUtil.isEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public Set<String> keys(String key) {
		if (StrUtil.isEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.keys(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * 序列化
	 */
	private byte[] serialize(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 反序列化
	 */
	private Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Object obj = null;
		if (bytes == null) {
			return obj;
		}
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
