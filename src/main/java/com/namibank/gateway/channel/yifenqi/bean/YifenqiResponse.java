package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

/**
 * 易分期接口返回参数<br>
 * ⼀一般情况下，当status值不不是ok时，就是出错，此时message的值为中⽂文提示
 * 
 * @author chirq
 *
 */
public class YifenqiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	// 状态码
	private String status;

	// 提示消息（一般可以直接展示给用户）
	private String message;

	// 响应参数data的加密密钥，由服务端随机生成，易易分期使⽤用商户公钥加密，base64编码
	private String encryptKey;

	// 透传值（与请求消息相同）
	private String cryptMode;

	// 响应消息签名，请参考“响应消息签名”说明
	private String signature;

	// 业务级参数json串串的密文（AES加密后的base64字符串串），是否有data属性，以及data属性的数据结构，不不同api有不不同的定义。
	private String data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		return "YifenqiBaseResponse [status=" + status + ", message=" + message + ", encryptKey=" + encryptKey
				+ ", cryptMode=" + cryptMode + ", signature=" + signature + ", data=" + data + "]";
	}

}
