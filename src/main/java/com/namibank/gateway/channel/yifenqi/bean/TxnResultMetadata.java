package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

/**
 * 交易易响应元数据
 * 
 * @author chirq
 *
 */
public class TxnResultMetadata implements Serializable {

	private static final long serialVersionUID = 1L;

	// 可为空，交易易本身的错误代码，交易易失败时为非空值
	private String errorCode;
	// 可为空，交易易本身的错误详细消息，交易易失败时为非空值
	private String errorMessage;

	// 交易易订单号 或 被查询交易易的订单号
	private String orderId;

	// 交易易类型。02-预授权，32-预授权撤销，03-预授权完成，33-预授权完成 撤销，04-退货
	private String txnType;

	// 交易易子类型。02-预授权/预授权完成，00-预授权撤销/退货，01-预授权完成撤销
	private String txnSubType;

	// 交易易订单发送时间 或 被查询交易易的订单发送时间, 格式 yyyyMMddHHmmss
	private String txnTime;

	// 交易易金金额（分）或 被查询交易易的交易易金金额
	private String txnAmt;

	// 交易易币种，156-人民币（默认）
	private String currency;

	// 交易易查询流水号 或 被查询交易易的交易易查询流水号
	private String queryId;

	// 可为空，被查询交易易的原始交易易查询流水号. 如预授权撤销时，为预授权交易的queryId。调⽤用“交易易状态查询"接口时为非空值
	private String origQueryId;

	// 可为空，被查询交易易的交易易状态。调用“交易易状态查询"接口时为⾮非空值。"00"-被查询交易易交易易成功
	private String origOrderStatus;

	// 可为空，被查询交易易的交易易状态消息。调用“交易易状态查询"接口时为非空值
	private String origOrderMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getOrigQueryId() {
		return origQueryId;
	}

	public void setOrigQueryId(String origQueryId) {
		this.origQueryId = origQueryId;
	}

	public String getOrigOrderStatus() {
		return origOrderStatus;
	}

	public void setOrigOrderStatus(String origOrderStatus) {
		this.origOrderStatus = origOrderStatus;
	}

	public String getOrigOrderMessage() {
		return origOrderMessage;
	}

	public void setOrigOrderMessage(String origOrderMessage) {
		this.origOrderMessage = origOrderMessage;
	}

	@Override
	public String toString() {
		return "TxnResultMetadata [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", orderId=" + orderId
				+ ", txnType=" + txnType + ", txnSubType=" + txnSubType + ", txnTime=" + txnTime + ", txnAmt=" + txnAmt
				+ ", currency=" + currency + ", queryId=" + queryId + ", origQueryId=" + origQueryId
				+ ", origOrderStatus=" + origOrderStatus + ", origOrderMessage=" + origOrderMessage + "]";
	}

}
