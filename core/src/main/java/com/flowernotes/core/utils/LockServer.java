package com.flowernotes.core.utils;


import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.excutor.RunnableEx;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.core.cache.CacheService;
import com.flowernotes.core.constant.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockServer {
	@Autowired
	private CacheService cacheService;

	/** 开启锁 */
	public void doLock(String flag) {
		doLock(flag, Context.cache_lock_timeout);
	}

	/** 开启锁，附带锁的超时时间(毫秒) */
	public void doLock(String flag, Long timeout) {
		Long testTime = System.currentTimeMillis();
		while(true) {
			Long cacheTimeout = cacheService.lock(flag, timeout);
			long time = System.currentTimeMillis()-testTime;
			if(cacheTimeout.equals(-1L)) {
				LoggerUtil.info(this.getClass(), "成功获取到【"+flag+"】锁，等待了 "+time+"ms");
				return;
			}
			LoggerUtil.info(this.getClass(), "获取【"+flag+"】锁失败, 持有锁的用户"+(cacheTimeout-System.currentTimeMillis())+"ms锁定时间，已等待 "+time+"ms");
			if(time > timeout) {
				throw new CommonException("缓存锁【"+flag+"】获取失败");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new CommonException(e);
			}
		}
	}
	
	public void unLock(String flag) {
		LoggerUtil.info(this.getClass(), "【"+flag+"】已解锁");
		cacheService.unLock(flag);
	}

	/***
	 * 新增一个同步任务
	 * @param flag	同步的标记
	 * @param runBody	执行体
	 * @return	执行体的返回值
	 */
	public void doSynchronized(String flag, Runnable runBody) {
		doLock(flag);
		runBody.run();
		unLock(flag);
	}
	
	/***
	 * 新增一个同步任务
	 * @param flag	同步的标记
	 * @param runBody	执行体
	 * @return	执行体的返回值
	 */
	public <T> T doSynchronized(String flag, RunnableEx<T> runBody) {
		doLock(flag);
		T t = runBody.run(System.currentTimeMillis());
		unLock(flag);
		return t;
	}
}
