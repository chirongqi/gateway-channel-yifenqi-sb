package com.namibank.df.gateway.bean.common;

/**
 * 
 * @desc 货币code枚举
 * @author wangguoqing
 * @date 2015年12月30日下午2:26:12
 *
 */
public enum CurrencyEnum {

	CNY(156,"人民币"),USD(840,"美元"),HKD(344,"港元"),JPY(392,"日元"),GBP(826,"英镑"),KRW(9,"韩元");
	
	private int currencyCode;
	private String currencyName;
	
	private CurrencyEnum(int currencyCode,String currencyName){
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
	}

	public int getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

}
