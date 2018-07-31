package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 11:55
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface Context {
    String field_Create_time ="create_time";

    /**
     * 缓存中lock锁key的前缀
     */
    String cache_lock_key = "hj_cache_lock_";

    /**
     * 分布式锁的默认超时时间(毫秒)
     */
    Long cache_lock_timeout = 3000L;


    String CHARTSET = "UTF-8";


    /**
     * 缓存当天最后的订单编号，自增
     */
    String cache_latest_orderno ="order_num_incr";
}
