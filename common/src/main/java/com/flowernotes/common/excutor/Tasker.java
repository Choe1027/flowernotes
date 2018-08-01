package com.flowernotes.common.excutor;

/** 任务接口 */
public interface Tasker {
	/** 执行一个任务 */
	public Object doTask(Object... objs) throws Exception;
}
