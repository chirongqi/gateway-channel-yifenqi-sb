package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;
import java.util.Arrays;

public class YifenqiDecodeResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] aesKey; // 解密后的aeskey

	private boolean isSignature; // 签名验证是否通过

	private String decodeData; // 解密后响应业务级数据

	private PreauthDataResponse preauthDataResponse; // 解密后预授权响应

	private YifenqiResponse yifenqiResponse; // 完整响应对象

	public byte[] getAesKey() {
		return aesKey;
	}

	public void setAesKey(byte[] aesKey) {
		this.aesKey = aesKey;
	}

	public boolean isSignature() {
		return isSignature;
	}

	public void setSignature(boolean isSignature) {
		this.isSignature = isSignature;
	}

	public PreauthDataResponse getPreauthDataResponse() {
		return preauthDataResponse;
	}

	public void setPreauthDataResponse(PreauthDataResponse preauthDataResponse) {
		this.preauthDataResponse = preauthDataResponse;
	}

	public String getDecodeData() {
		return decodeData;
	}

	public void setDecodeData(String decodeData) {
		this.decodeData = decodeData;
	}

	public YifenqiResponse getYifenqiResponse() {
		return yifenqiResponse;
	}

	public void setYifenqiResponse(YifenqiResponse yifenqiResponse) {
		this.yifenqiResponse = yifenqiResponse;
	}

	@Override
	public String toString() {
		return "YifenqiDecodeResponse [aesKey=" + Arrays.toString(aesKey) + ", isSignature=" + isSignature
				+ ", decodeData=" + decodeData + ", preauthDataResponse=" + preauthDataResponse + ", yifenqiResponse="
				+ yifenqiResponse + "]";
	}

}
