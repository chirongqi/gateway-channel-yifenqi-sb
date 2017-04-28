package com.namibank.gateway.channel.yifenqi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.namibank.df.gateway.constant.ChannelDisposeStatus;

public class PauthResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public static PauthResponse getInstance() {
		PauthResponse resp = new PauthResponse();
		resp.setDisposeStatus(ChannelDisposeStatus.PAYING);
		return resp;
	}

	/**
	 * 通道处理状态
	 */
	private ChannelDisposeStatus disposeStatus;

	/**
	 * 响应code
	 */
	private String respCode;

	/**
	 * 响应描述
	 */
	private String respMsg;

	/**
	 * 响应时间
	 */
	private String respTime;

	// 交易订单号
	private String bizOrderId;

	// 易分期银联返回查询id
	private String queryId;

	// 交易时间
	private String txnTime;

	// 交易易金金额（分）或 被查询交易易的交易易金金额
	private BigDecimal txnAmt;

	// 交易易类型。02-预授权，32-预授权撤销，03-预授权完成，33-预授权完成 撤销，04-退货
	private String txnType;

	/**
	 * 通道原始返回码
	 */
	private String originalCode;

	/**
	 * 通道原始返回消息
	 */
	private String originalMsg;

	public ChannelDisposeStatus getDisposeStatus() {
		return disposeStatus;
	}

	public void setDisposeStatus(ChannelDisposeStatus disposeStatus) {
		this.disposeStatus = disposeStatus;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getOriginalCode() {
		return originalCode;
	}

	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}

	public String getOriginalMsg() {
		return originalMsg;
	}

	public void setOriginalMsg(String originalMsg) {
		this.originalMsg = originalMsg;
	}

}
