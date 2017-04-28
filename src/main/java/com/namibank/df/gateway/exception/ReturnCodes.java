package com.namibank.df.gateway.exception;

/**
 * 
 * @author Administrator
 * 短信系统错误码
 */
public enum ReturnCodes {
	
	LO_SYS_SUCCESS("LO_0000","成功"),
	LO_SYS_ERROR("LO_9999","系统异常"),
	DATE_TIME_ERROR("LO_9901","日期格式错误"),
	LO_SYS_ING("RE_9910","在处理中"),
	LO_SAVE_DATABASE("LO_9001","落地数据失败"),
	LO_NOT_UPDATE("LO_9002","没有更新数据"),
	REPAY_ING("RE_9010","在还款中"),
	LO_VERIFY_PARA_ERROR("LO_1001","校验请求参数不合法"),
	LO_BUS_REPEAT("LO_1002","重复下单"),
	
	CREDIT_REDUCE_FAIL("CR_1012","扣减授信失败"),
	CREDIT_ADD_FAIL("CR_1022","增加授信失败"),
	
	QUERY_USER_BANK_CARD("QU_1001","查询用户银行信息异常"),
	QUERY_NOT_FOUND("QU_1002","查询的数据不存在"),
	
	CHECK_FAIL("CH_0012","风控审核失败"),
	CHECK_EXCEPTION("CH_0013","风控审核异常"),
	PAY_APPL_FAIL("pay_0022","支付申请失败"),
	PAY_FAIL("pay_0023","支付失败"),
	
	/**查询订单**/
	QUERY_DATA_INEXISTENT("query_0101","查询的数据不存在"),
	QUERY_DATA_REPETITION("query_0102","查询的数据重复"),
	QUERY_DATA_STATUS("query_0103","查询的数据不正确"),
	
	
	;
	
	private String code;
	private String msg;
	
	private ReturnCodes(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
