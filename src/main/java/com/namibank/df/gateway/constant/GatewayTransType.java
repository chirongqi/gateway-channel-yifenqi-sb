package com.namibank.df.gateway.constant;

/**
 * 支付网关交易类型
 * 
 * @author CliveYuan
 * @date Mar 21, 2017 5:17:49 PM
 *
 */
public enum GatewayTransType {
    
    /**
     * 代扣
     */
    DEDUCT(51),
    
    /**
     * 代收
     */
    PAY(52);

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private GatewayTransType(int code) {
        this.code = code;
    }
    
}
