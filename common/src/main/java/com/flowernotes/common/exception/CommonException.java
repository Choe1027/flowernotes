package com.flowernotes.common.exception;

/**
 * 公共异常
 */
public class CommonException extends BaseException {
	private static final long serialVersionUID = -126571840822647111L;
	
	public CommonException() {
		super();
	}
	
	public CommonException(String remark) {
		super(Error.system_error, remark);
	}
	
	public CommonException(Exception e) {
		super(Error.system_error, e);
	}
	
	public CommonException(String remark, Exception e) {
		super(Error.system_error, e, remark);
	}
	
	public CommonException(Error errorCode, String... remark) {
		super(errorCode, remark);
	}
	
	public CommonException(Error errorCode, Throwable e, String... remark) {
		super(errorCode, e, remark);
	}
}
