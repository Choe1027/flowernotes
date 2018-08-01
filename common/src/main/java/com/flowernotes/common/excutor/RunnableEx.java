package com.flowernotes.common.excutor;

/** 扩展Runnable的run */
public interface RunnableEx<T> {
	
	/***
	 * 执行体
	 * @param execTime	执行的时间(时间戳)
	 * @return	返回结果回调
	 */
	public T run(Long execTime);
}
