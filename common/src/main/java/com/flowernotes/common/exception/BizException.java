package com.flowernotes.common.exception;

/** 业务异常 */
public class BizException extends BaseException {
	private static final long serialVersionUID = 1942108848151982144L;
	
	public BizException() {
		super();
	}
	
	public BizException(Throwable throwable) {
		super(Error.biz_error, throwable);
	}
	
	public BizException(String... remark) {
		super(Error.biz_error, remark);
	}
	
	public BizException(Error errorCode, String... remark) {
		super(errorCode, remark);
	}
	
	public BizException(Error errorCode, Throwable throwable, String... remark) {
		super(errorCode, throwable, remark);
	}
}
