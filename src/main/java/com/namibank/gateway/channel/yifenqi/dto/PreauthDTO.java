package com.namibank.gateway.channel.yifenqi.dto;

import java.math.BigDecimal;

public class PreauthDTO {
	// 批次号
	private String batchId;

	// 交易订单号
	private String bizOrderId;

	// 易分期银联返回查询id
	private String queryId;

	// 交易时间
	private String txnTime;

	// 交易金金额（元）或 被查询交易的交易金额
	private BigDecimal txnAmt;

	// 交易类型。02-预授权，32-预授权撤销，03-预授权完成，33-预授权完成 撤销，04-退货
	private String txnType;

	// 银行卡号
	private String bankCard;
	// 银行卡开户名
	private String bankUserName;
	// 银行卡开户身份证号
	private String bankUserId;
	// 银行预留手机号
	private String bankPhone;
	// 信用卡cvn2
	private String cvn2;
	// 信用卡有效期，格式yymm
	private String validPeriod;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
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

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public String getBankUserId() {
		return bankUserId;
	}

	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

}
