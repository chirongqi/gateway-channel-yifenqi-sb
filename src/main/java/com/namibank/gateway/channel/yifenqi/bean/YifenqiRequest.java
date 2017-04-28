package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

/**
 * 易分期通用请求参数
 * 
 * @author chirq
 *
 */
public class YifenqiRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 请求时间戳，格式yyyyMMddHHmmss
	private String timestamp;

	// 请求消息data的加密密钥，由客户端随机生成，商户使用易易分期公钥加密并转为base64编码格式
	// 商户端的公钥
	private String encryptKey;

	// AES加密模式：0-AES/CBC/NoPadding, 1-AES/CBC/PKCS7Padding，空值表示
	// AES/CBC/NoPadding
	private String cryptMode;

	// 请求消息签名，请参考“请求消息签名”说明
	private String signature;

	// 业务级输⼊入参数json串串的密⽂文（AES加密后的base64字符串串）。业务级输⼊入参数 不不同API有不不同的定义，请⻅见下⾯面具体的说明。
	private String data;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getCryptMode() {
		return cryptMode;
	}

	public void setCryptMode(String cryptMode) {
		this.cryptMode = cryptMode;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "YifenqiRequest [timestamp=" + timestamp + ", encryptKey=" + encryptKey + ", cryptMode=" + cryptMode
				+ ", signature=" + signature + ", data=" + data + "]";
	}

}
