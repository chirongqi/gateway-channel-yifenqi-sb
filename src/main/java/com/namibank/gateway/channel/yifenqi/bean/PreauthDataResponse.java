package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

/**
 * 预授权接口响应
 * 
 * @author chirq
 *
 */
public class PreauthDataResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merId; // 商户ID

	private TxnResultMetadata txnResultMetadata;

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public TxnResultMetadata getTxnResultMetadata() {
		return txnResultMetadata;
	}

	public void setTxnResultMetadata(TxnResultMetadata txnResultMetadata) {
		this.txnResultMetadata = txnResultMetadata;
	}

}
