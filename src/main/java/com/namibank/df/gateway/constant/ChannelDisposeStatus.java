package com.namibank.df.gateway.constant;

/**
 * 通道处理状态
 * 
 * @author CliveYuan
 * @date Mar 22, 2017 4:33:58 PM
 *
 */
public enum ChannelDisposeStatus {
    
    /**
     * 支付中
     */
    PAYING,
    
    /**
     * 支付成功
     */
    PAY_SUCCESS,
    
    /**
     * 支付失败
     */
    PAY_FAIL,
    
    /**
     * 支付异常
     */
    PAY_EXCEPTION;
    

}
