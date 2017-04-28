package com.namibank.df.gateway.bean.common;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TenBankType {
	
	ib("1464","1002","中国工商银行"),
	bd("1479","1003","中国建设银行"),
	ly("1468","1005","中国农业银行"),
	xy("1497","1009","兴业银行"),
	bj("1544","1032","北京银行"),
	gd("1484","1022","中国光大银行"),
	zx("1483","1021","中信实业银行"),
	yc("1442","1066","中国邮储银行"),
	pa("1517","1010","平安银行(含深发展)"),
	ch("1474","1026","中国银行"),
	jt("1482","1020","中国交通银行");
//	pf("","1004","上海浦东发展银行"),
//	ms("","1006","中国民生银行"),
//	fz("","1027","广东发展银行");
	
	private String bankId;
	private String bankType;
	private String bankName;
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	private TenBankType(String bankId, String bankType, String bankName){
		this.bankId = bankId;
		this.bankType = bankType;
		this.bankName = bankName;
	}
	
	public static Map<String, TenBankType> getEnumMap(){
        Map<String, TenBankType> map = new LinkedHashMap<>();
        TenBankType [] values = TenBankType.values();
        for (TenBankType statusType : values){
            map.put(statusType.bankId, statusType);
        }
        return map;
    }
    public static Map<String, String> getMap(){
        Map<String, String> map = new LinkedHashMap<>();
        TenBankType [] values = TenBankType.values();
        for (TenBankType statusType : values){
            map.put(statusType.bankId, statusType.bankType);
        }
        return map;
    }

    public static TenBankType buildByBankId(String bankId){
        switch(bankId){
	        case "1464" : return ib ;
	        case "1479" : return bd ;
	        case "1468" : return ly ;
	        case "1497" : return xy ;
	        case "1544" : return bj ;
	        case "1484" : return gd ;
	        case "1483" : return zx ;
	        case "1442" : return yc ;
	        case "1517" : return pa ;
	        case "1474" : return ch ;
	        case "1482" : return jt ;
	        default:{
                return null;
            }
        }
    }
}
