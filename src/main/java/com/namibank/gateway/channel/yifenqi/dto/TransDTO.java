package com.namibank.gateway.channel.yifenqi.dto;

public class TransDTO {

	private boolean result = false;

	private String msg;

	private Object data;

	public TransDTO() {

	}

	public TransDTO(boolean result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
