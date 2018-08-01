package com.flowernotes.core.utils;


import com.flowernotes.common.exception.CommonException;
import com.flowernotes.core.constant.Context;
import com.flowernotes.core.constant.UserType;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 请求临时对象缓存池 */
@Component
public class RequestPool {
	/** request临时对象池 */
	private static ThreadLocal<HttpServletRequest> pool = new ThreadLocal<HttpServletRequest>();
	
	/** 移除缓存 */
	public static void remove() {
		pool.remove();
	}
	
	/** 添加缓存 */
	public static void set(HttpServletRequest request) {
		pool.set(request);
		
	}

	/** 获取缓存 */
	public static HttpServletRequest get() {
		return pool.get();
	}
	
	/** 获取缓存session */
	public static HttpSession getSession() {
		return get().getSession();
	}
	
	/**
	 * 获取用户id
	 * @return	返回用户id
	 */
	public static Long getUserId() {
		Long userId = (Long) getSession().getAttribute(Context.session_userId);
		// 判断session中是否有用户信息ID 如果有从取用户信息ID
		if (userId == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		return userId;
	}
	
	/**
	 * 获取用户类型
	 * @return	返回用户id
	 * @see UserType
	 */
	public static UserType getUserType() {
		Integer userType = (Integer) getSession().getAttribute(Context.session_userType);
		UserType ut = UserType.forType(userType);
		if (ut == UserType.notKnow) {
			throw new CommonException("用户类型未知");
		}
		return ut;
	}
}
