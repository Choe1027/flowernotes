/**   
 * Copyright (c) 版权所有 2010-2017 杭州宸禾网络科技有限公司
 * 产品名： 
 * 包名：org.stardream.cache   
 * 文件名：CacheClient.java   
 * 版本信息：   
 * 创建日期：2017年5月24日-上午11:22:00
 *    
 */
package com.flowernotes.core.cache;


import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.constant.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class CacheClient {
	private JedisPool pool;

	@Autowired
	private ConfigBean configBean;

	public void setConfigBean(ConfigBean configBean) {
		this.configBean = configBean;
	}

	/**
	 * initClient(初始化缓存客户端)
	 * 
	 * @return void
	 * @exception @since
	 *                1.0.0
	 */
	@PostConstruct
	public void initClient() {
		String address = configBean.getAddress();
		int port = configBean.getPort();
		String pwd = configBean.getPwd();
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(configBean.getMaxIdle());
		if (StringUtil.isEmpty(pwd)) {
			pool = new JedisPool(config, address, port, 1000 * 10);
		} else {
			pool = new JedisPool(config, address, port, 1000 * 10, pwd);
		}
		LoggerUtil.info(this.getClass(), "ip=" + address + ", port=" + port + ", pwd=" + pwd);
	}

	public Jedis getClient() {
		try {
			Jedis jedis = pool.getResource();
			return jedis;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e);
		}
		return null;

	}

	/**
	 * get(获取缓存数据) 获取对应key的值
	 * 
	 * @return Object
	 * @param key
	 *            type:String 缓存中的键值
	 * @exception @since
	 *                1.0.0
	 */
	public Object get(String key) {
		Jedis jedis = getClient();
		try {
			String value = jedis.get(key);
			if (value == null) {
				return null;
			}
			if (!JsonUtil.isJson(value)){
				return value;
			}
			return JsonUtil.jsonToBean(value,Object.class);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 获取锁(timeout：锁的超时时间，单位:毫秒)
	 * 
	 * @param key
	 *            锁
	 * @param timeout
	 *            锁的超时时间
	 * @return -1：代表当前线程获取到锁；其他：返回当前持有锁的人的超时时间
	 */
	public Long lock(String key, Long timeout) {
		Jedis jedis = getClient();
		try {
			key = Context.cache_lock_key + key;
			if (timeout == null) {
				timeout = Context.cache_lock_timeout;
			}
			String value = String.valueOf(System.currentTimeMillis() + timeout + 1);
			Long result = jedis.setnx(key, value);
			// SETNX成功，则成功获取一个锁
			if (result != null && result == 1) {
				jedis.pexpire(key, timeout);
				return -1L;
			}

			// 获取当前持有锁的家伙是不是过期了
			String oldValueStr = jedis.get(key);
			if (oldValueStr == null) {
//				throw new CommonException("未知原因，缓存锁无法获取");
				oldValueStr = String.valueOf(System.currentTimeMillis() + timeout + 1);
			}
			Long oldValue = Long.valueOf(oldValueStr);

			// 如果当前正在持有锁的线程已经过期了，则进行清理工作
			if (oldValue < System.currentTimeMillis()) {
				// 获取上一个锁到期时间，并设置现在的锁到期时间，
				String getValue = jedis.getSet(key, String.valueOf(value));
				// 如果过期的时间等于获得的上一个的时间，则当前线程竞争成功
				if (getValue == null || Long.parseLong(getValue) == oldValue.longValue()) {
					jedis.pexpire(key, timeout);
					return -1L;
				}
				// TODO 被别人抢走
			}
			return oldValue;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e);
			throw new CommonException(e);
		} finally {
			jedis.close();
		}
	}

	/** 释放锁 */
	public void unlock(String key) {
		Jedis jedis = getClient();
		try {
			key = Context.cache_lock_key + key;
//			Long nowTime = System.currentTimeMillis();
//			String oldTimeStr = jedis.get(key);
//			if (oldTimeStr != null && Long.parseLong(oldTimeStr) < nowTime) {
				jedis.del(key);
//			}
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e);
			throw new CommonException(e);
		} finally {
			jedis.close();
		}
	}

	/**
	 * getMapValue(获取缓存中map的值) 获取缓存对应key的map中键为label的值
	 * 
	 * @param key
	 *            type:string 缓存中的键值
	 * @param label
	 *            type:String 缓存中map的键值
	 * @return Object
	 * @exception @since
	 *                1.0.0
	 */
	public <T extends Object> T getMapValue(String key, String label) {
		Jedis jedis = getClient();
		try {
			List<String> objList = jedis.hmget(key, label);

//			List<byte[]> objList = jedis.hmget(key.getBytes(), label.getBytes());
			if (objList != null && objList.size() > 0) {
				for (String value : objList) {
					if (value == null) {
						jedis.hdel(key, label);
					} else {

						return (T)(JsonUtil.jsonToBean(value,Object.class));
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e);
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * getMap(获取缓存的map) 获取对应key的map
	 * 
	 * @return Map<String, ?>
	 * @param key
	 *            type:String 缓存中的键值
	 * @exception @since
	 *                1.0.0
	 */
	public Map<String, ?> getMap(String key) {
		Jedis jedis = getClient();
		try {
			Map<String, String> tempMap = jedis.hgetAll(key);

			Map<String, Object> map = new HashMap<String, Object>();
			if (tempMap != null) {
				Set<String> keys = tempMap.keySet();
				for (String k : keys) {
					map.put(k, JsonUtil.jsonToBean(tempMap.get(k),Object.class));
				}
			}
			return map;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e);
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * set(存储数据缓存)
	 * 
	 * @return void
	 * @param key
	 *            type:String 缓存中的键值
	 * @param value
	 *            type:Object 存储的数据
	 * @exception @since
	 *                1.0.0
	 */
	public void set(String key, Object value) {
		Jedis jedis = getClient();
		try {
			jedis.set(key, JsonUtil.objToJson(value));
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
	}

	/**
	 * setMap(存储map到缓存)
	 * 
	 * @return void
	 * @param key
	 *            type:String 缓存中的键值
	 * @param value
	 *            type:Object 存储的数据
	 * @param label
	 *            type:String 缓存中map的键值
	 * @exception @since
	 *                1.0.0
	 */
	public void setMapValue(String key, String label, Object value) {
		Jedis jedis = getClient();
		try {
			jedis.hset(key, label, JsonUtil.objToJson(value));
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
	}

	/**
	 * remove(移除存储的数据缓存)
	 * 
	 * @return key type:String 移除的键值
	 * @param key
	 *            type:String 缓存中的键值
	 * @exception @since
	 *                1.0.0
	 */
	public Object remove(String key) {
		Jedis jedis = getClient();
		try {
			Object result = get(key);
			jedis.del(key);
			return result;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * remove(移除存储的Map数据缓存)
	 * 
	 * @return key type:String 移除的键值
	 * @param label
	 *            type:String 缓存中map的键值
	 * @exception @since
	 *                1.0.0
	 */
	public Object removeMapValue(String key, String label) {
		Jedis jedis = getClient();
		try {
			Object resutl = getMapValue(key, label);
			jedis.hdel(key, label);
			return resutl;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;

	}

	/**
	 * cleanAll(移除缓存中的所有值)
	 * 
	 * @return void
	 * @exception @since
	 *                1.0.0
	 */
	public void cleanAll() {
		Jedis jedis = getClient();
		jedis.flushAll();
		jedis.flushDB();
	}

	/**
	 * containsMapValue(判断缓存中是否包含某个值)
	 * 
	 * @param key
	 *            type:String 缓存中对应的键值
	 * @param value
	 *            type:Object 缓存中判断是否存在的值
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public String containsMapValue(String key, Object value) {
		Map<String, ?> map = getMap(key);
		for (String mapKey : map.keySet()) {
			Object mapValue = map.get(mapKey);
			if (mapValue.equals(value) || mapValue.toString().equals(value.toString())
					|| JsonUtil.objToJson(mapValue).equals(JsonUtil.objToJson(value))) {
				return mapKey;
			}
		}
		return null;
	}

	/**
	 * 获取是否存在某个key
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		Jedis jedis = getClient();
		try {
			return jedis.exists(key);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return false;
	}

	/**
	 * 获取是否存在某个field（适用于hash结构）
	 * @param key
	 * @param field
	 * @return
	 */
	public boolean hExists(String key,String field){
		Jedis jedis = getClient();
		try {
			return jedis.hexists(key,field);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return false;
	}
	
	/** 设置超时时间 */
	public void setTimeout(String key, Long timeout) {
		Jedis jedis = getClient();
		try {
			jedis.pexpire(key, timeout);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
	}

	/** 保存用户位置 */
	public void locationModify(String key, Double lat, Double lng, Object userFlag) {
		Jedis jedis = getClient();
		try {
			jedis.geoadd(key , lng, lat, StringUtil.toString(userFlag));
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
	}
	
	/** 获取用户位置 */
	public Point.Double locationGet(String key, Object userFlag) {
		Jedis jedis = getClient();
		try {
			List<GeoCoordinate> list = jedis.geopos(key, StringUtil.toString(userFlag));
			if(list!=null && list.size()>0) {
				GeoCoordinate gc = list.get(0);
				return new Point2D.Double(gc.getLatitude(), gc.getLongitude());
			}
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}
	
	/**
	 * 获取附近的人
	 * @param key	公共key
	 * @param lat	纬度
	 * @param lng	经度
	 * @param range	范围（米）
	 * @return	返回附近的人位置列表
	 */
	public List<GeoRadiusResponse> locationFind(String key, Double lat, Double lng, Double range) {
		Jedis jedis = getClient();
		try {
			List<GeoRadiusResponse> list = jedis.georadius(key, lng, lat, range, GeoUnit.M);
			return list;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}
	
	/** 删除位置信息 */
	public Long locationDel(String key, Object userFlag) {
		Jedis jedis = getClient();
		try {
			return jedis.zrem(key, StringUtil.toString(userFlag));
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}

	
	/**
	 * 清空缓存中db1所有数据
	 */
	public void removeAll() {
		Jedis jedis = getClient();
		Set<String> keys = jedis.keys("*");
		for (String string : keys) {
			jedis.del(string);
		}
	}


	public Long incr(String key){
		Jedis jedis = getClient();
		try {
			return jedis.incr(key);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			jedis.close();
		}
		return null;
	}
	
}
