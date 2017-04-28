package com.namibank.df.gateway.constant;

/**
 * 拿米支付网关返回码枚举类
 * 
 * @author CliveYuan
 * @date Mar 24, 2017 1:40:23 PM
 *
 */
public enum GatewayReturnCode {
    
    /**
     * 成功
     */
    S0000("成功"),
    /**
     * 失败
     */
    F0000("失败"),
    /**
     * 四要素错误
     */
    E0000("四要素错误"),
    /**
     * 代扣余额不足
     */
    E_100311("代扣余额不足"),
    /**
     * 代付余额不足
     */
    E0002("代付余额不足"),
    /**
     * 订单不存在
     */
    E0003("订单不存在"),
    /**
     * 重复交易
     */
    E0004("重复交易"),
    /**
     * 请求超时
     */
    E0005("请求超时"),
    /**
     * 网络异常
     */
    E0006("网络异常"),
    /**
     * 验签失败
     */
    E0007("验签失败"),
    /**
     * 卡状态不符
     */
    E0008("卡状态不符"),
    /**
     * 系统繁忙
     */
    E0009("系统繁忙"),
    /**
     * 不发送至第三方通道
     */
    E0010("不发送至第三方通道"),
    /**
     * 无可用支付通道
     */
    E0011("无可用支付通道"),
    /**
     * 未知错误
     */
    O0001("未知错误");
    
    private GatewayReturnCode(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
