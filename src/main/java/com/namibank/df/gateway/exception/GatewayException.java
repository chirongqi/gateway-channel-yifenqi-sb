package com.namibank.df.gateway.exception;

/**
 * 支付网关异常类
 * 
 * @author CliveYuan
 * @date Mar 21, 2017 11:39:37 PM
 *
 */
public class GatewayException extends BaseException {
    
    private static final long serialVersionUID = 6206032905731247372L;

    public GatewayException(String errorMsg) {
        super("9999", errorMsg);
    }
    public GatewayException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

}
