package com.flowernotes.common.excutor;

/** 拦截器 */
public interface Filter<T> {
	
	/** 是否进行拦截(true：拦截掉) */
	public boolean doFilter(T obj);
}
