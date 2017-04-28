package com.namibank.df.gateway.bean.common;

/**
 * 
 * 
 * @desc 支付网关错误码
 * @author wangguoqing
 * @date 2015年12月30日下午6:03:52
 *
 */
public class PayErrorCode {
	
	/**
	 * 支付网关错误码分为：本地错误，支付渠道错误，银联返回错误结果，
	 * **/
	
	/**参数异常**/
	public static String PAY_PARAMS_EXECPTION = "E_100101";
	/**已过撤销时间**/
	public static String PAY_PARAMS_TIMEOUT_REVOKE = "E_100102";
	/**已过退货时间**/
	public static String PAY_PARAMS_TIMEOUT_BACK = "E_100103";
	/**支付交易数据落地失败**/
	public static String PAY_SAVE_DATA_FAIL = "E_100104";

	
	/**支付渠道不存在**/
	public static String PAY_CHANNEL_NO_EXIST = "E_100201"; 
	/**支付渠道被禁用**/
	public static String PAY_CHANNEL_DISABLE = "E_100202"; 
	/**支付渠道不支持该交易**/
	public static String PAY_CHANNEL_PASS = "E_100203";
	
	/**连接银行支付异常**/
	public static String PAY_CONNECT_BANK_EXCEPTION = "E_100301";
	/**重复支付**/
	public static String PAY_DOUBLE = "E_100302";
	/** 银联查询接口，没有可以查询的订单**/
	public static String PAY_NO_ORDER = "E_100303";
	/** 银联接口，没有返回正确的http状态**/
	public static String PAY_NO_BACK_HTTP_STATUS = "E_100304";
	/** 银联接口，签名验证失败**/
	public static String PAY_CHECK_ERROR = "E_100305";
	/** 银联接口，报文错误**/
	public static String PAY_MESSAGE_ERROR = "E_100306";
	/** 银联接口，结果未明**/
	public static String PAY_UNKNOW_RESULT = "E_100307";
	/** 银联接口，订单已传入银联**/
	public static String PAY_ORDER_IN_YINLIAN = "E_100308";
	/** 银联接口，成功**/
	public static String PAY_RESULT_SUCCESS= "E_100309";
	/** 银联接口，交易进行中**/
	public static String PAY_BUSSES_UNDERWAY= "E_100310";
	/** 银联接口，卡内余额不足**/
	public static String PAY_MONEY_NOT_FUNDS= "E_100311";
	/** 银联接口，交易失败**/
	public static String PAY_TRANSACTION_FAILED= "E_100312";
	
}
