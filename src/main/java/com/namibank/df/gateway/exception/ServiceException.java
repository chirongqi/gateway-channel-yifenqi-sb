package com.namibank.df.gateway.exception;
/**
 * 异常订单处理平台Service抛出的异常
 */
public class ServiceException extends BaseException {
	private static final long serialVersionUID = 4833282991444885984L;

	public ServiceException(String errorCode) {
		super(errorCode, "");
	}

	public ServiceException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	public ServiceException(String errorCode, Throwable caused) {
		super(errorCode, caused);
	}

	public ServiceException(String errorCode, String errorMsg,
			Throwable caused) {
		super(errorCode, errorMsg, caused);
	}
}
