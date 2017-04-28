package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

/**
 * 预授权接口请求
 * 
 * @author chirq
 *
 */
public class PreauthDataRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merId; // 商户ID
	private String merOrderId; // 商户订单号，30位：8位商户号+14位日期时间+8位序列列号
	private String txnTime; // 交易易请求时间, 格式yyyyMMddHHmmss
	private String txnAmt; // 预授权金额（分）

	private CreditBankcardInfo creditBankcardInfo; // 持卡人信息

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
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

	public CreditBankcardInfo getCreditBankcardInfo() {
		return creditBankcardInfo;
	}

	public void setCreditBankcardInfo(CreditBankcardInfo creditBankcardInfo) {
		this.creditBankcardInfo = creditBankcardInfo;
	}

	@Override
	public String toString() {
		return "PreauthDataRequest [merId=" + merId + ", merOrderId=" + merOrderId + ", txnTime=" + txnTime
				+ ", txnAmt=" + txnAmt + ", creditBankcardInfo=" + creditBankcardInfo + "]";
	}

}
