package com.namibank.gateway.channel.yifenqi.service.impl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.namibank.df.gateway.util.DateUtil;
import com.namibank.df.gateway.util.GatewayLogger;
import com.namibank.gateway.channel.yifenqi.bean.PreauthDataResponse;
import com.namibank.gateway.channel.yifenqi.bean.YifenqiDecodeResponse;
import com.namibank.gateway.channel.yifenqi.bean.YifenqiRequest;
import com.namibank.gateway.channel.yifenqi.bean.YifenqiResponse;
import com.namibank.gateway.channel.yifenqi.constant.YifenqiConstants;
import com.namibank.gateway.channel.yifenqi.exception.YifenqiException;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesCryptor;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesException;
import com.namibank.gateway.channel.yifenqi.utils.AesDataUtils;
import com.namibank.gateway.channel.yifenqi.utils.EncryptKeyUtils;
import com.namibank.gateway.channel.yifenqi.utils.SignatureUtils;

/**
 *
 * 广州易支付服务类
 *
 * @author Chirq
 * @date 2017年4月18日11:20:00
 */
@Component
public class YifenqiService {

	private static final GatewayLogger logger = GatewayLogger.getLogger(YifenqiService.class);

	@Value("${yifenqi.api.url.prefix}")
	private String apiUrlPrefix;

	@Value("${yifenqi.api.url.host}")
	private String apiUrlHost;

	@Value("${yifenqi.api.url.suffix}")
	private String apiUrlSuffix;

	@Value("${yifenqi.public.key}")
	private String yifenqiPublicKey;

	@Value("${nami.private.key}")
	private String namiPrivateKey;

	// @Value("${nami.public.key}")
	// private String namiPublicKey;

	/**
	 * 发送请求
	 *
	 * @param xmlParamsString
	 *            xml格式参数
	 * @return xml格式字符串
	 * @throws IOException
	 * @throws HttpException
	 * @throws YifenqiException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public String sendRequest(String apiName, String data, List<String> factorList)
			throws HttpException, IOException, SocketTimeoutException, YifenqiException {
		String returnStr = null;
		// 处理url
		String apiUrl = apiUrlPrefix + apiUrlHost + apiUrlSuffix + apiName;
		// 处理请求参数
		String strSendData = this.manageSendData(apiName, data, factorList);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(apiUrl);
		postMethod.setRequestHeader("Content-Type", "application/json;charset=utf-8");
		logger.info("请求地址: {}", apiUrl);
		logger.info("请求报文: {}", strSendData);
		try {
			// 设置编码
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			httpClient.getParams().setSoTimeout(20 * 1000);
			postMethod.setRequestBody(strSendData);
			// httpClient.setTimeout();
			int statusCode = httpClient.executeMethod(postMethod);
			// 失败
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
				byte[] responseBody = postMethod.getResponseBody();
				String strResp = new String(responseBody, "UTF-8");
				logger.error(strResp);
				throw new YifenqiException("http状态码为: " + statusCode);
			} else {
				// 读取内容
				byte[] responseBody = postMethod.getResponseBody();
				String strResp = new String(responseBody, "UTF-8");
				logger.info("返回报文: {}", strResp);
				if (strResp == null || strResp.length() <= 0) {
					return returnStr;
				}
				returnStr = strResp;
			}
		} finally {
			postMethod.releaseConnection();
		}
		// logger.info("返回原始报文: {}", returnStr);
		return returnStr;
	}

	/**
	 * 解密响应数据
	 * 
	 * @param response
	 * @return
	 */
	public YifenqiDecodeResponse decodeResponseData(String responseStr) {
		YifenqiDecodeResponse yifenqiDecodeResponse = new YifenqiDecodeResponse();
		YifenqiResponse response = JSONObject.parseObject(responseStr, YifenqiResponse.class);
		logger.info("返回状态+描述：{}-{}", response.getStatus(), response.getMessage());
		// 原始数据
		yifenqiDecodeResponse.setYifenqiResponse(response);
		// 解密 易分期AESKeyBytes
		byte[] aesKey = null;
		if (response.getEncryptKey() != null && response.getEncryptKey().length() > 0) {
			aesKey = EncryptKeyUtils.getDecodeEncryptKey(response.getEncryptKey(), namiPrivateKey);
			yifenqiDecodeResponse.setAesKey(aesKey);
		}
		// 解密data数据体
		PreauthDataResponse preauthDataResponse = null;
		if (response.getData() != null && response.getData().length() > 0) {
			String decodeData = AesDataUtils.getDecodeData(aesKey, response.getData());
			logger.info("解密后的数据data体：{}", decodeData);
			yifenqiDecodeResponse.setDecodeData(decodeData);
			preauthDataResponse = JSONObject.parseObject(decodeData, PreauthDataResponse.class);
			yifenqiDecodeResponse.setPreauthDataResponse(preauthDataResponse);
		}
		if (preauthDataResponse != null) {
			// 需要签名的参数因子
			List<String> factorResList = new ArrayList<String>();
			if (preauthDataResponse.getMerId() != null) {
				factorResList.add("merId" + preauthDataResponse.getMerId());
			}
			if (preauthDataResponse.getTxnResultMetadata().getErrorCode() != null) {
				factorResList.add("errorCode" + preauthDataResponse.getTxnResultMetadata().getErrorCode());
			}
			if (preauthDataResponse.getTxnResultMetadata().getErrorMessage() != null) {
				factorResList.add("errorMessage" + preauthDataResponse.getTxnResultMetadata().getErrorMessage());
			}
			if (preauthDataResponse.getTxnResultMetadata().getOrderId() != null) {
				factorResList.add("orderId" + preauthDataResponse.getTxnResultMetadata().getOrderId());
			}
			if (preauthDataResponse.getTxnResultMetadata().getTxnType() != null) {
				factorResList.add("txnType" + preauthDataResponse.getTxnResultMetadata().getTxnType());
			}
			if (preauthDataResponse.getTxnResultMetadata().getTxnSubType() != null) {
				factorResList.add("txnSubType" + preauthDataResponse.getTxnResultMetadata().getTxnSubType());
			}
			if (preauthDataResponse.getTxnResultMetadata().getTxnTime() != null) {
				factorResList.add("txnTime" + preauthDataResponse.getTxnResultMetadata().getTxnTime());
			}
			if (preauthDataResponse.getTxnResultMetadata().getTxnAmt() != null) {
				factorResList.add("txnAmt" + preauthDataResponse.getTxnResultMetadata().getTxnAmt());
			}
			if (preauthDataResponse.getTxnResultMetadata().getCurrency() != null) {
				factorResList.add("currency" + preauthDataResponse.getTxnResultMetadata().getCurrency());
			}
			if (preauthDataResponse.getTxnResultMetadata().getQueryId() != null) {
				factorResList.add("queryId" + preauthDataResponse.getTxnResultMetadata().getQueryId());
			}
			if (preauthDataResponse.getTxnResultMetadata().getOrigQueryId() != null) {
				factorResList.add("origQueryId" + preauthDataResponse.getTxnResultMetadata().getOrigQueryId());
			}
			if (preauthDataResponse.getTxnResultMetadata().getOrigOrderStatus() != null) {
				factorResList.add("origOrderStatus" + preauthDataResponse.getTxnResultMetadata().getOrigOrderStatus());
			}
			if (preauthDataResponse.getTxnResultMetadata().getOrigOrderMessage() != null) {
				factorResList
						.add("origOrderMessage" + preauthDataResponse.getTxnResultMetadata().getOrigOrderMessage());
			}
			// 验证签名
			boolean boo = SignatureUtils.verifyResponseData(response.getSignature(), yifenqiPublicKey, factorResList);
			yifenqiDecodeResponse.setSignature(boo);
		}
		return yifenqiDecodeResponse;
	}

	/**
	 * 处理消息发送体
	 * 
	 * @param apiName
	 * @param data
	 * @param factorList
	 * @return
	 */
	private String manageSendData(String apiName, String data, List<String> factorList) {
		// 生成AESKeyBytes
		byte[] aesKey = null;
		try {
			aesKey = AesCryptor.randomAesKey();
		} catch (AesException e1) {
			logger.error("生成AESKey异常", e1);
		}
		if (aesKey == null || aesKey.length <= 0) {
			return "";
		}
		String now = DateUtil.format("yyyyMMddHHmmss", new Date());
		// 构造请求对象
		YifenqiRequest yifenqiRequest = new YifenqiRequest();
		// 1.AES加密模式
		yifenqiRequest.setCryptMode("1");
		// 2.请求时间戳
		yifenqiRequest.setTimestamp(now);
		// 3.获取data的加密秘钥 商户使用易易分期公钥加密并转为base64编码格式
		String encryptKey = EncryptKeyUtils.getEncodeEncryptKey(aesKey, yifenqiPublicKey);
		yifenqiRequest.setEncryptKey(encryptKey);
		logger.info("aes加密秘钥：{}", encryptKey);

		// 4.请求消息签名
		// 构造签名因子：urlPath
		String urlPath = apiUrlPrefix + apiUrlSuffix + apiName;
		String signature = SignatureUtils.signatureRequestData(urlPath, namiPrivateKey, factorList, now);
		logger.info("数据体签名：{}", signature);
		yifenqiRequest.setSignature(signature);

		// 5.处理data数据体
		String encryptData = AesDataUtils.getEncodeData(aesKey, data);
		yifenqiRequest.setData(encryptData);

		// 最后转成json
		String strSendData = JSONObject.toJSONString(yifenqiRequest);

		logger.info("请求数据体：{}", strSendData);
		return strSendData;
	}

	static Map<String, String> exceptionMap = new HashMap<String, String>(4);
	static {
		exceptionMap.put(YifenqiConstants.STATUS_CODE_EXCEPTION_BAD_DIGEST,
				YifenqiConstants.STATUS_CODE_EXCEPTION_BAD_DIGEST);
		exceptionMap.put(YifenqiConstants.STATUS_CODE_EXCEPTION_BAD_REQUEST,
				YifenqiConstants.STATUS_CODE_EXCEPTION_BAD_REQUEST);
		exceptionMap.put(YifenqiConstants.STATUS_CODE_EXCEPTION_SYSTEM_ERROR,
				YifenqiConstants.STATUS_CODE_EXCEPTION_SYSTEM_ERROR);
		exceptionMap.put(YifenqiConstants.STATUS_CODE_EXCEPTION_UNAUTHORIZED,
				YifenqiConstants.STATUS_CODE_EXCEPTION_UNAUTHORIZED);
	}

	/**
	 * 验证响应头是异常信息
	 * 
	 * @param status
	 * @return
	 */
	public boolean checkStatusIsException(String status) {
		if (exceptionMap.containsKey(status)) {
			return true;
		}
		return false;
	}

}
