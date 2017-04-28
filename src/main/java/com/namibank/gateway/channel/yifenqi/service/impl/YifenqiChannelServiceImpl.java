package com.namibank.gateway.channel.yifenqi.service.impl;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.namibank.df.gateway.constant.ChannelDisposeStatus;
import com.namibank.df.gateway.constant.GatewayReturnCode;
import com.namibank.df.gateway.util.DateUtil;
import com.namibank.df.gateway.util.GatewayLogger;
import com.namibank.gateway.channel.yifenqi.bean.CreditBankcardInfo;
import com.namibank.gateway.channel.yifenqi.bean.PreauthDataRequest;
import com.namibank.gateway.channel.yifenqi.bean.TxnResultMetadata;
import com.namibank.gateway.channel.yifenqi.bean.YifenqiDecodeResponse;
import com.namibank.gateway.channel.yifenqi.constant.YifenqiConstants;
import com.namibank.gateway.channel.yifenqi.dto.PauthResponse;
import com.namibank.gateway.channel.yifenqi.dto.PreauthDTO;
import com.namibank.gateway.channel.yifenqi.exception.YifenqiException;
import com.namibank.gateway.channel.yifenqi.service.YifenqiChannelService;
import com.namibank.gateway.channel.yifenqi.utils.IdWorker;

/**
 * 广州易分期,实现
 *
 * @author Chirq
 * @date 2017年4月18日11:08:20
 */
@Service("yifenqiChannelService")
public class YifenqiChannelServiceImpl implements YifenqiChannelService {

	private static final GatewayLogger logger = GatewayLogger.getLogger(YifenqiChannelServiceImpl.class);

	@Value("${yifenqi.merchantNo}")
	private String merchantNo; // 商户号

	@Autowired
	YifenqiService yifenqiService;

	IdWorker idWorker = new IdWorker(2, 10);

	/**
	 * 预授权
	 * 
	 * @param payBean
	 * @return
	 */
	public PauthResponse preauth(PreauthDTO preauthDTO) {
		logger.info("[batchId={}] 广州-易分期, 入参: gatePayBean={}", preauthDTO.getBatchId(), preauthDTO);
		// 参数校验
		Assert.notNull(preauthDTO, "参数不能为空");
		Assert.notNull(preauthDTO.getTxnTime(), "交易时间不能为空");
		Assert.notNull(preauthDTO.getTxnAmt(), "交易金额不能为空");

		PauthResponse response = PauthResponse.getInstance();
		try {
			// 需要签名的参数因子
			List<String> factorList = new ArrayList<String>();
			// 预授权 封装入参
			PreauthDataRequest preauthDataRequest = new PreauthDataRequest();
			// String now = DateUtil.format("yyyyMMddHHmmss", new Date());
			preauthDataRequest.setMerId(merchantNo); // 商户ID
			factorList.add("merId" + merchantNo);
			preauthDataRequest.setTxnTime(preauthDTO.getTxnTime()); // 交易请求时间格式yyyyMMddHHmmss
			factorList.add("txnTime" + preauthDataRequest.getTxnTime());
			preauthDataRequest.setMerOrderId(merchantNo + preauthDTO.getTxnTime() + idWorker.get8IdToString()); // 商户订单号，30位：8位商户号+14位日期时间+8位序列列号
			factorList.add("merOrderId" + preauthDataRequest.getMerOrderId());
			BigDecimal amount = preauthDTO.getTxnAmt().multiply(new BigDecimal(100));// 元转分
			preauthDataRequest.setTxnAmt(amount.toBigInteger().toString()); // 预授权金额（分）
			factorList.add("txnAmt" + preauthDataRequest.getTxnAmt());
			// 持卡人信息
			CreditBankcardInfo creditBankcardInfo = new CreditBankcardInfo();
			creditBankcardInfo.setAccNo(preauthDTO.getBankCard()); // 信用卡号
			factorList.add("accNo" + creditBankcardInfo.getAccNo());
			creditBankcardInfo.setIdNumber(preauthDTO.getBankUserId()); // 持卡人身份证号
			factorList.add("idNumber" + creditBankcardInfo.getIdNumber());
			creditBankcardInfo.setName(preauthDTO.getBankUserName()); // 持卡人姓名
			factorList.add("name" + creditBankcardInfo.getName());
			creditBankcardInfo.setPhone(preauthDTO.getBankPhone()); // 信用卡预留留手机号
			factorList.add("phone" + creditBankcardInfo.getPhone());
			creditBankcardInfo.setCvn2(preauthDTO.getCvn2()); // 信用卡cvn2
			factorList.add("cvn2" + creditBankcardInfo.getCvn2());
			creditBankcardInfo.setValidPeriod(preauthDTO.getValidPeriod()); // 信用卡有效期，格式yymm
			factorList.add("validPeriod" + creditBankcardInfo.getValidPeriod());

			preauthDataRequest.setCreditBankcardInfo(creditBankcardInfo);
			String data = JSONObject.toJSONString(preauthDataRequest);
			// 获取响应值
			String responseMsg = yifenqiService.sendRequest(YifenqiConstants.APINAME_PREAUTH, data, factorList);
			// 响应为空设置成处理中
			if (responseMsg == null || responseMsg.length() <= 0) {
				response.setDisposeStatus(ChannelDisposeStatus.PAYING);
				return response;
			}
			// 解密响应
			YifenqiDecodeResponse decodeResponse = yifenqiService.decodeResponseData(responseMsg);
			this.parseResponse(response, decodeResponse);
		} catch (SocketTimeoutException e) {
			logger.error("[batchId=" + preauthDTO.getBatchId() + "] 广州-易分期 预授权 请求超时", e);
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
			response.setRespCode(GatewayReturnCode.E0005.name());
			response.setRespMsg(GatewayReturnCode.E0005.getMsg());
		} catch (Exception e) {
			logger.error("[batchId=" + preauthDTO.getBatchId() + "] 广州-易分期 预授权", e);
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
		}
		return response;
	}

	/**
	 * 预授权结果查询
	 * 
	 * @param gatePayBean
	 * @return
	 */
	public PauthResponse preauthQuery(PreauthDTO preauthDTO) {
		logger.info("[batchId={}] 广州-易分期, 入参: gatePayBean={}", preauthDTO.getBatchId(), preauthDTO);
		// 参数校验
		Assert.notNull(preauthDTO, "参数不能为空");
		Assert.notNull(preauthDTO.getQueryId(), "查询id不能为空");
		Assert.notNull(preauthDTO.getBizOrderId(), "交易订单号不能为空");
		Assert.notNull(preauthDTO.getTxnTime(), "交易时间不能为空");

		PauthResponse response = PauthResponse.getInstance();
		try {
			// 需要签名的参数因子
			List<String> factorList = new ArrayList<String>();
			Map<String, String> requestDataMap = new HashMap<String, String>(5);
			requestDataMap.put("merId", merchantNo);
			factorList.add("merId" + merchantNo);

			requestDataMap.put("origTxnType", preauthDTO.getTxnType());
			factorList.add("origTxnType" + requestDataMap.get("origTxnType"));

			requestDataMap.put("origMerOrderId", preauthDTO.getBizOrderId());
			factorList.add("origMerOrderId" + requestDataMap.get("origMerOrderId"));

			requestDataMap.put("origQueryId", preauthDTO.getQueryId());
			factorList.add("origQueryId" + requestDataMap.get("origQueryId"));

			requestDataMap.put("origTxnTime", preauthDTO.getTxnTime());
			factorList.add("origTxnTime" + requestDataMap.get("origTxnTime"));

			String data = JSONObject.toJSONString(requestDataMap);
			// 获取响应值
			String responseMsg = yifenqiService.sendRequest(YifenqiConstants.APINAME_PREAUTH_QUERY_TXN_STATE, data,
					factorList);
			// 响应为空设置成处理中
			if (responseMsg == null || responseMsg.length() <= 0) {
				response.setDisposeStatus(ChannelDisposeStatus.PAYING);
				return response;
			}

			// 解密响应
			YifenqiDecodeResponse decodeResponse = yifenqiService.decodeResponseData(responseMsg);
			this.parseResponse(response, decodeResponse);
		} catch (Exception e) {
			logger.error("[batchId=" + preauthDTO.getBatchId() + "] 广州-易分期 预授权", e);
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
		}
		return response;
	}

	/**
	 * 预授权完成
	 * 
	 * @param gatePayBean
	 * @return
	 */
	public PauthResponse preauthCompletion(PreauthDTO preauthDTO) {
		logger.info("[batchId={}] 广州-易分期, 入参: gatePayBean={}", preauthDTO.getBatchId(), preauthDTO);
		// 参数校验
		Assert.notNull(preauthDTO, "参数不能为空");
		Assert.notNull(preauthDTO.getQueryId(), "查询id不能为空");
		Assert.notNull(preauthDTO.getTxnTime(), "交易时间不能为空");
		Assert.notNull(preauthDTO.getTxnAmt(), "交易金额不能为空");
		PauthResponse response = PauthResponse.getInstance();
		try {
			// 需要签名的参数因子
			List<String> factorList = new ArrayList<String>();
			Map<String, String> requestDataMap = new HashMap<String, String>(5);
			requestDataMap.put("merId", merchantNo);
			factorList.add("merId" + requestDataMap.get("merId"));
			// requestDataMap.put("merOrderId",
			// "170400012017042115043842345644");//
			// 商户订单号，30位：8位商户号+14位日期时间+8位序列列号
			requestDataMap.put("merOrderId", merchantNo + preauthDTO.getTxnTime() + idWorker.get8IdToString()); // 商户订单号，30位：8位商户号+14位日期时间+8位序列列号
			factorList.add("merOrderId" + requestDataMap.get("merOrderId"));

			requestDataMap.put("origQueryId", preauthDTO.getQueryId());
			factorList.add("origQueryId" + requestDataMap.get("origQueryId"));

			requestDataMap.put("txnTime", preauthDTO.getTxnTime());
			factorList.add("txnTime" + requestDataMap.get("txnTime"));
			BigDecimal amount = preauthDTO.getTxnAmt().multiply(new BigDecimal(100));// 元转分
			requestDataMap.put("txnAmt", amount.toBigInteger().toString()); // 预授权金额（分）
			factorList.add("txnAmt" + requestDataMap.get("txnAmt"));

			String data = JSONObject.toJSONString(requestDataMap);
			// 获取响应值
			String responseMsg = yifenqiService.sendRequest(YifenqiConstants.APINAME_PREAUTH_COMPLETION, data,
					factorList);
			// 响应为空设置成处理中
			if (responseMsg == null || responseMsg.length() <= 0) {
				response.setDisposeStatus(ChannelDisposeStatus.PAYING);
				return response;
			}

			// 解密响应
			YifenqiDecodeResponse decodeResponse = yifenqiService.decodeResponseData(responseMsg);
			this.parseResponse(response, decodeResponse);
		} catch (Exception e) {
			logger.error("[batchId=" + preauthDTO.getBatchId() + "] 广州-易分期 预授权", e);
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
		}
		return response;
	}

	/**
	 * 预授权撤销
	 * 
	 * @param gatePayBean
	 * @return
	 */
	public PauthResponse preauthCancel(PreauthDTO preauthDTO) {
		logger.info("[batchId={}] 广州-易分期, 入参: gatePayBean={}", preauthDTO.getBatchId(), preauthDTO);
		// 参数校验
		Assert.notNull(preauthDTO, "参数不能为空");
		Assert.notNull(preauthDTO.getQueryId(), "查询id不能为空");
		Assert.notNull(preauthDTO.getTxnAmt(), "交易金额不能为空");
		Assert.notNull(preauthDTO.getTxnTime(), "交易时间不能为空");

		PauthResponse response = PauthResponse.getInstance();
		try {
			// 需要签名的参数因子
			List<String> factorList = new ArrayList<String>();
			Map<String, String> requestDataMap = new HashMap<String, String>(5);
			requestDataMap.put("merId", merchantNo);
			factorList.add("merId" + requestDataMap.get("merId"));
			requestDataMap.put("merOrderId", merchantNo + preauthDTO.getTxnTime() + idWorker.get8IdToString()); // 商户订单号，30位：8位商户号+14位日期时间+8位序列列号
			factorList.add("merOrderId" + requestDataMap.get("merOrderId"));

			requestDataMap.put("origQueryId", preauthDTO.getQueryId());
			factorList.add("origQueryId" + requestDataMap.get("origQueryId"));

			requestDataMap.put("txnTime", preauthDTO.getTxnTime());
			factorList.add("txnTime" + requestDataMap.get("txnTime"));

			BigDecimal amount = preauthDTO.getTxnAmt().multiply(new BigDecimal(100));// 元转分
			requestDataMap.put("txnAmt", amount.toBigInteger().toString()); // 预授权金额（分）
			factorList.add("txnAmt" + requestDataMap.get("txnAmt"));

			String data = JSONObject.toJSONString(requestDataMap);
			// 获取响应值
			String responseMsg = yifenqiService.sendRequest(YifenqiConstants.APINAME_PREAUTH_CANCEL, data, factorList);
			// 响应为空设置成处理中
			if (responseMsg == null || responseMsg.length() <= 0) {
				response.setDisposeStatus(ChannelDisposeStatus.PAYING);
				return response;
			}

			// 解密响应
			YifenqiDecodeResponse decodeResponse = yifenqiService.decodeResponseData(responseMsg);
			this.parseResponse(response, decodeResponse);
		} catch (Exception e) {
			logger.error("[batchId=" + preauthDTO.getBatchId() + "] 广州-易分期 预授权", e);
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
		}
		return response;
	}

	/**
	 * 解析数据
	 * 
	 * @param response
	 * @param decodeResponse
	 * @throws YifenqiException
	 */
	private void parseResponse(PauthResponse response, YifenqiDecodeResponse decodeResponse) throws YifenqiException {
		if (response == null) {
			response = PauthResponse.getInstance();
		}
		if (decodeResponse == null) {
			throw new YifenqiException("解密数据失败");
		}

		// 原始状态
		String status = decodeResponse.getYifenqiResponse().getStatus();
		// 是否有异常
		if (yifenqiService.checkStatusIsException(status)) {
			response.setDisposeStatus(ChannelDisposeStatus.PAY_EXCEPTION);
			response.setOriginalCode(status);
			response.setOriginalMsg(decodeResponse.getYifenqiResponse().getMessage());
			throw new YifenqiException("易分期接口返回异常信息: " + decodeResponse.getYifenqiResponse().getMessage());
		}
		// 验证签名
		if (!decodeResponse.isSignature()) {
			throw new YifenqiException("响应数据签名验证失败");
		}
		// 设置 响应---------------------------
		if (YifenqiConstants.STATUS_CODE_OK.equals(status)) {
			response.setDisposeStatus(ChannelDisposeStatus.PAY_SUCCESS);
			response.setRespCode(GatewayReturnCode.S0000.name());
			response.setRespMsg(GatewayReturnCode.S0000.getMsg());
		} else if (YifenqiConstants.STATUS_CODE_TIMEOUT.equals(status)) {
			response.setDisposeStatus(ChannelDisposeStatus.PAYING);
			response.setRespCode(GatewayReturnCode.E0005.name());
			response.setRespMsg(GatewayReturnCode.E0005.getMsg());
		} else {
			response.setDisposeStatus(ChannelDisposeStatus.PAY_FAIL);
			response.setRespCode(GatewayReturnCode.F0000.name());
			response.setRespMsg(GatewayReturnCode.F0000.getMsg());
		}
		response.setOriginalCode(status);
		response.setOriginalMsg(decodeResponse.getYifenqiResponse().getMessage());
		String now = DateUtil.format("yyyyMMddHHmmss", new Date());
		response.setRespTime(now);
		TxnResultMetadata txnResultMetadata = decodeResponse.getPreauthDataResponse().getTxnResultMetadata();
		response.setBizOrderId(txnResultMetadata.getOrderId());
		response.setQueryId(txnResultMetadata.getQueryId());
		BigDecimal bd = new BigDecimal(txnResultMetadata.getTxnAmt()).divide(new BigDecimal(100)); // 分转元
		response.setTxnAmt(bd);
		response.setTxnTime(txnResultMetadata.getTxnTime());
		response.setTxnType(txnResultMetadata.getTxnType());

	}
}
