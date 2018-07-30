package com.flowernotes.core.base;

import java.util.Map;

/**
 * @author cyk
 * @date 2018/7/30/030 14:58
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface CacheService {

    /**
     * get(获取缓存数据)
     * 获取对应key的值
     * @return Object
     * @param key type:String 缓存中的键值
     * @exception
     * @since  1.0.0
     */
    public <T> T get(String key);

    /**
     * getMapValue(获取缓存中map的值)
     * 获取缓存对应key的map中键为label的值
     * @param key type:string 缓存中的键值
     * @param label type:String 缓存中map的键值
     * @return Object
     * @exception
     * @since  1.0.0
     */
    public <T extends Object> T getMapValue(String key, String label);

    /**
     * getMap(获取缓存的map)
     * 获取对应key的map
     * @return Map<String, ?>
     * @param key type:String 缓存中的键值
     * @exception
     * @since  1.0.0
     */
    public Map<String, ?> getMap(String key) ;

    /**
     * set(存储数据缓存)
     * @return void
     * @param key type:String 缓存中的键值
     * @param value type:Object 存储的数据
     * @exception
     * @since  1.0.0
     */
    public void set(String key, Object value);

    /**
     * setMap(存储map到缓存)
     * @return void
     * @param key type:String 缓存中的键值
     * @param value type:BaseBean 存储的数据
     * @param label type:String 缓存中map的键值
     * @exception
     * @since  1.0.0
     */
    public void setMapValue(String key, String label, Object value);

    /**
     * remove(移除存储的数据缓存)
     * @return key type:String  移除的键值
     * @param key type:String 缓存中的键值
     * @exception
     * @since  1.0.0
     */
    public <T> T remove(String key);

    /**
     * remove(移除存储的Map数据缓存)
     * @return key type:String  移除的键值
     * @param label type:String 缓存中map的键值
     * @exception
     * @since  1.0.0
     */
    public <T> T removeMapValue(String key, String label);

    /**
     * cleanAll(移除缓存中的所有值)
     * @return void
     * @exception
     * @since  1.0.0
     */
    public void cleanAll();

    /**
     * containsMapValue(判断缓存中是否包含某个值)
     * @param key type:String  缓存中对应的键值
     * @param value type:Object 缓存中判断是否存在的值
     * @return String
     * @exception
     * @since  1.0.0
     */
    public String containsMapValue(String key, Object value);

    /**
     * 分布式锁(相同key同时请求，只有一个可以返回true，对应unlock解锁)
     * @param key	锁的key
     * @param timeout	锁的超时时间（毫秒）
     * @return	-1：代表当前线程获取到锁；其他：返回当前持有锁的人的超时时间
     */
    public Long lock(String key, Long timeout);


    /**
     * 分布式锁(相同key同时请求，只有一个可以返回true，对应unlock解锁)<br/>
     * (使用缺省的超时时间)
     * @param key	锁的key
     * @return	-1：代表当前线程获取到锁；其他：返回当前持有锁的人的超时时间
     */
    public Long lock(String key);

    /**
     * 分布式锁的解锁<br/>
     *（会自动校验是否当前锁是自己的锁，防止误解锁）
     * @param key	需要解锁的key
     */
    public void unLock(String key);



    /**
     * 判端是否存在某个key
     * @param key
     * @return
     */
    boolean isExists(String key);

    /**
     * 判端是否存在某个field（仅适用于Hash结构）
     * @param key
     * @param field
     * @return
     */
    boolean hIsExists(String key, String field);


    /**
     * 设置超时时间
     * @param key	公共key
     * @param timeout	超时时间（毫秒）
     */
    public void setTimeout(String key, Long timeout);


    /**
     * 生成订单编号
     * @param key
     * @param type
     * @return
     */
    String genOrderNum(String key, Integer type);

}
