package com.flowernotes.core.cache;


import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.utils.DateUtils;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.core.constant.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CacheServiceImpl implements CacheService {

	@Autowired
	private CacheClient cacheClient;
	@Autowired
	private ConfigBean configBean;

	/**启动时的日期**/
	private static Long datetime = DateUtils.getTheDayEnd(DateUtils.getCurrentTime());


	/**
	 * get(获取缓存数据) 获取对应key的值
	 * @return Object
	 * @param key type:String 缓存中的键值
	 * @exception @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		try {
			Object value = cacheClient.get(configBean.getFlag() + key);
			LoggerUtil.info(this.getClass(), "缓存获取到数据" + JsonUtil.objToJson(value));
			return (T) value;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "get缓存数据时异常(key=" + key + ")!", e);
			e.printStackTrace();
			return null;
		} finally {
			// cacheClient.close();
		}
	}

	/**
	 * getMapValue(获取缓存中map的值) 获取缓存对应key的map中键为label的值
	 * @param key type:string 缓存中的键值
	 * @param label type:String 缓存中map的键值
	 * @return Object
	 * @exception @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T getMapValue(String key, String label) {
		try {
			T result = (T) cacheClient.getMapValue(configBean.getFlag() + key, label);
			LoggerUtil.info(this.getClass(), "缓存获取到数据" + JsonUtil.objToJson(result));
			return result;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
			return null;
		} finally {
			// cacheClient.close();
		}
	}

	/**
	 * getMap(获取缓存的map) 获取对应key的map
	 * @return Map<String, ?>
	 * @param key type:String 缓存中的键值
	 * @exception @since 1.0.0
	 */
	@Override
	public Map<String, ?> getMap(String key) {
		try {
			Map<String, ?> result = cacheClient.getMap(configBean.getFlag() + key);
			LoggerUtil.info(this.getClass(), "缓存获取到数据" + JsonUtil.objToJson(result));
			return result;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
			return null;
		} finally {
			// cacheClient.close();
		}
	}

	/**
	 * set(存储数据缓存)
	 * @return void
	 * @param key type:String 缓存中的键值
	 * @param value type:Object 存储的数据
	 * @exception @since 1.0.0
	 */
	@Override
	public void set(String key, Object value) {
		try {
//			localCacheMap.put(configBean.getFlag() + key, value);
			cacheClient.set(configBean.getFlag() + key, value);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "set缓存数据时异常(key=" + key + ", value=" + value + ")!", e);
		} finally {
			// cacheClient.close();
		}
	}

	/**
	 * setMap(存储map到缓存)
	 * @return void
	 * @param key type:String 缓存中的键值
	 * @param value type:Object 存储的数据
	 * @param label type:String 缓存中map的键值
	 * @exception @since
	 *                1.0.0
	 */
	@Override
	public void setMapValue(String key, String label, Object value) {
		try {
			cacheClient.setMapValue(configBean.getFlag() + key, label, value);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			// cacheClient.close();
		}
	}

	/**
	 * remove(移除存储的数据缓存)
	 * @return key type:String 移除的键值
	 * @param key type:String 缓存中的键值
	 * @exception @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T remove(String key) {
		Object result = null;
		try {
//			localCacheMap.remove(configBean.getFlag() + key);
			result = cacheClient.remove(configBean.getFlag() + key);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "remove缓存数据时异常(key=" + key + ")!", e);
		} finally {
			// cacheClient.close();
		}
		return (T) result;
	}

	/**
	 * remove(移除存储的Map数据缓存)
	 * @return key type:String 移除的键值
	 * @param label type:String 缓存中map的键值
	 * @exception @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeMapValue(String key, String label) {
		Object result = null;
		try {
			result = cacheClient.removeMapValue(configBean.getFlag() + key, label);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			// cacheClient.close();
		}
		return (T) result;
	}

	/**
	 * cleanAll(移除缓存中的所有值)
	 * @return void
	 * @exception @since 1.0.0
	 */
	@Override
	public void cleanAll() {
//		localCacheMap.clear();
		cacheClient.cleanAll();
	}

	/**
	 * containsMapValue(判断缓存中是否包含某个值)
	 * @param key type:String 缓存中对应的键值
	 * @param value type:Object 缓存中判断是否存在的值
	 * @return String
	 * @exception @since 1.0.0
	 */
	@Override
	public String containsMapValue(String key, Object value) {
		try {
			return cacheClient.containsMapValue(configBean.getFlag() + key, value);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
			return null;
		} finally {
			// cacheClient.close();
		}
	}

	@Override
	public Long lock(String key, Long timeout) {
		try {
			return cacheClient.lock(configBean.getFlag() + key, timeout);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
			throw new CommonException(e);
		} finally {
			// cacheClient.close();
		}
	}

	@Override
	public Long lock(String key) {
		try {
			return cacheClient.lock(configBean.getFlag() + key, Context.cache_lock_timeout);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
			throw new CommonException(e);
		} finally {
			// cacheClient.close();
		}
	}

	@Override
	public void unLock(String key) {
		try {
			cacheClient.unlock(configBean.getFlag() + key);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
		} finally {
			// cacheClient.close();
		}
	}



    @Override
    public boolean isExists(String key) {
        boolean isExists = false;
        try {
            isExists = cacheClient.exists(configBean.getFlag()+key);
        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
        } finally {

        }
        return isExists;
    }

    @Override
    public boolean hIsExists(String key, String field) {
        boolean isExists = false;
        try {
            isExists = cacheClient.hExists(configBean.getFlag()+key, field);
        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
        } finally {

        }
        return isExists;
    }

    @Override
    public void setTimeout(String key, Long timeout) {
         try {
        	 cacheClient.setTimeout(configBean.getFlag()+key, timeout);
         } catch (Exception e) {
             LoggerUtil.error(this.getClass(), "异常在bad()中处理", e);
         } finally {

         }
    }

    @Override
    public String genOrderNum(String key, Integer type) {
        Long currentTime = DateUtils.getCurrentTime();
        long num;
        if (currentTime > datetime) {
            datetime = DateUtils.getTheDayEnd(currentTime);
            cacheClient.remove(key);
        }
        num = cacheClient.incr(key);
        return DateUtils.dateLongToString(currentTime,DateUtils.DATE_PATTERN_7) + "0" + type + String.format("%06d", num);
    }
}
