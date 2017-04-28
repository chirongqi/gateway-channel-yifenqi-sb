package com.namibank.gateway.channel.yifenqi.constant;

public class YifenqiConstants {

	/** 交易成功 */
	public static final String STATUS_CODE_OK = "ok";

	/** 交易失败 */
	public static final String STATUS_CODE_FAILURE = "failure";

	/** 交易易超时（通常需发起查询交易查询交易易结果） */
	public static final String STATUS_CODE_TIMEOUT = "timeout";

	/** 交易易不不存在（表示原始交易易不不存在，需核实交易易参数是否正确或者原始交易易是否已撤销） */
	public static final String STATUS_CODE_TRADE_UNFOUND = "trade_unfound";

	/** 交易异常：认证失败 */
	public static final String STATUS_CODE_EXCEPTION_UNAUTHORIZED = "unauthorized";
	/** 交易异常：消息签名不不正确 */
	public static final String STATUS_CODE_EXCEPTION_BAD_DIGEST = "bad_digest";
	/** 交易异常：请求不不正确 */
	public static final String STATUS_CODE_EXCEPTION_BAD_REQUEST = "bad_request";
	/** 交易异常：系统错误 */
	public static final String STATUS_CODE_EXCEPTION_SYSTEM_ERROR = "system_error";

	/**
	 * API名称：预授权接口
	 */
	public static final String APINAME_PREAUTH = "/pauth/preauth";

	/**
	 * API名称：预授权撤销接口
	 */
	public static final String APINAME_PREAUTH_CANCEL = "/pauth/preauth-cancel";

	/**
	 * API名称：预授权完成接口
	 */
	public static final String APINAME_PREAUTH_COMPLETION = "/pauth/preauth-completion";

	/**
	 * API名称：交易状态查询接口
	 */
	public static final String APINAME_PREAUTH_QUERY_TXN_STATE = "/pauth/query-txn-state";

	/**
	 * API名称：预授权完成撤销接口
	 */
	public static final String APINAME_PREAUTH_COMPLETION_CANCEL = "/pauth/preauth-completion-cancel";

	/**
	 * API名称：预授权冻结和完成接口
	 */
	public static final String APINAME_PREAUTH_COMPLETION_BATCH = "/pauth/preauth-completion-batch";

	/**
	 * API名称：退款接口
	 */
	public static final String APINAME_PREAUTH_REFUND = "/pauth/refund";

}
