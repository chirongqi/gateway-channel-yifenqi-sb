package com.namibank.df.gateway.bean.common;


/**
 * 
 * @desc 支付网关常量
 * @author wangguoqing
 * @date 2015年12月29日下午7:57:10
 *
 */
public class CommonConstant {
	
	public static String CHARSET_UTF8 = "UTF-8";
	
	//支付状态
	/**成功**/
	public static final int PAYMENT_TXN_STATUS_SUCCESS = 10;
	/**失败**/
	public static final int PAYMENT_TXN_STATUS_FAIL = 20;
	/**订单创建：支付订单创建完成**/ 
	public static final int PAYMENT_TXN_STATUS_WAIT = 22;
	/**支付中 支付结果不是同步情况适用**/
	public static final int PAYMENT_TXN_STATUS_ING = 30;
	/**异常**/
	public static final int PAYMENT_TXN_STATUS_EXCEPTION = 40;
	
	//支付交易类型
	/**代收-绑卡支付 */
	public static final int PAYEMNT_YILIAN_DAISHOU_BAND = 50;
	/**代收-建立绑定关系（后台绑定）**/
	public static final int PAYEMNT_YILIAN_DAISHOU_MAKE_BAND_BACK = 501;
	/**代收-建立绑定关系（前台绑定）**/
	public static final int PAYEMNT_YILIAN_DAISHOU_MAKE_BAND_FRONT = 502;
	/**代收-查询绑定关系**/
	public static final int PAYEMNT_YILIAN_DAISHOU_QUERY_BAND = 503;
	/**代收-解除绑定关系**/
	public static final int PAYEMNT_YILIAN_DAISHOU_MOVE_BAND = 504;
	/**代收-实名支付*/
	public static final int PAYEMNT_DAISHOU_REAL = 51;
	/**代收-退货*/
	public static final int PAYEMNT_YILIAN_DAISHOU_BACK = 511;
	/**代收-撤销*/
	public static final int PAYEMNT_YILIAN_DAISHOU_REVOKE = 512;
	/**银行卡实名认证 **/
	public static final int PAYMENT_TXN_CHECK_BANKCARD = 513;
	/**银行卡对账文件下载 **/
	public static final int PAYEMNT_YILIAN_DAISHOU_CHECK_FILE = 514;
	
	/**代付*/
	public static final int PAYEMNT_DAIFU = 52;
	/**代付-退货**/
	public static final int PAYEMNT_YILIAN_DAIFU_BACK = 521;
	/**代付-撤销*/
	public static final int PAYEMNT_YILIAN_DAIFU_REVOKE = 522;
	
	
	/**付款**/
	public static final int PAYMENT_TXN_TYPE_PAY = 10;
	/**退款**/
	public static final int PAYMENT_TXN_TYPE_REFUND = 20;
	
	
	/**预授权**/
	public static final int PAYMENT_TXN_TYPE_PRE_AUTH = 30;
	/**预授权完成**/
	public static final int PAYMENT_TXN_TYPE_PRE_AUTH_FINISH = 32;
	/**预授权撤销**/
	public static final int PAYMENT_TXN_TYPE_PRE_AUTH_CANCEL = 36;
	/**预授权撤销完成**/
	public static final int PAYMENT_TXN_TYPE_PRE_AUTH_CANCEL_FINISH = 38;
	
	//支付卡类型
	/**信用卡**/
	public static final int CREDIT_CARD = 10;
	/**借记卡**/
	public static final int DEBIT_CARD = 20;
	
	//支付网关所有开关状态
	/**支付网关禁用**/
	public static final int PAYEMNT_DISABLE = 10;
	
	/**支付网关启用**/
	public static final int PAYEMNT_ENABLED = 20;
	
	
}
