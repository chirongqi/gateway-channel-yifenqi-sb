package com.namibank.gateway.channel.yifenqi.bean;

import java.io.Serializable;

import com.namibank.df.gateway.util.GatewayUtils;

/**
 * 持卡人信息
 * 
 * @author chirq
 *
 */
public class CreditBankcardInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // 持卡人姓名
	private String idNumber; // 持卡人身份证号
	private String accNo; // 信用卡号
	private String validPeriod; // 信用卡有效期，格式yymm
	private String cvn2; // 信用卡cvn2
	private String phone; // 信用卡预留留手机号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "CreditBankcardInfo [name=" + name + ", idNumber=" + GatewayUtils.desensitize(idNumber) + ", accNo="
				+ GatewayUtils.desensitize(accNo) + ", validPeriod=" + validPeriod + ", cvn2=" + cvn2 + ", phone="
				+ GatewayUtils.desensitize(phone) + "]";
	}

}
