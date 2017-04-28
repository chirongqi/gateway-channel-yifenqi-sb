package com.namibank.gateway.channel.yifenqi.utils;

import java.util.Collections;
import java.util.List;

import com.namibank.df.gateway.util.GatewayLogger;
import com.namibank.gateway.channel.yifenqi.security.RSACryptUtil;

/**
 * 签名工具类
 * 
 * @author chirq
 *
 */
public class SignatureUtils {

	private static final GatewayLogger logger = GatewayLogger.getLogger(SignatureUtils.class);

	/**
	 * 签名请求数据
	 * 
	 * @param urlPath
	 * @param privateKey
	 * @param dataMap
	 * @param time
	 * @return
	 */
	public static String signatureRequestData(String urlPath, String privateKey, List<String> factorList, String time) {
		// 构造签名因子：urlPath
		logger.info("构造签名因子：urlPath：{}", urlPath);

		// 2 构造签名因子：拼装业务级参数
		Collections.sort(factorList);
		StringBuffer dataStr = new StringBuffer();
		for (String str : factorList) {
			dataStr.append(str);
		}
		logger.info("构造签名因子：拼装业务级参数：{}", dataStr);

		// 3 合并签名因子
		String concatString = urlPath + dataStr.toString() + time;
		logger.info("合并签名因子：{}", concatString);

		String signatureMsg = "";
		try {
			// 签名
			signatureMsg = RSACryptUtil.sign(concatString.getBytes(), privateKey);

			// 验证签名
			String namiPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9pR/Ibk3DRSzhvcRXiRk5gRCyLWvnytOkVY0J5zu5YlLnesGzK8iEX4eG4l1lxo+n97b/GDqJvx2sT6ZNTmVRQhA/VuFieDLuu7ngVY7Zhs3ydDlK6Le5+4lcW9sTviZmFZXTu2P5v6ImtQokfyA7rEk6CDl3Jm7XBwYRptA0xwIDAQAB";
			boolean status2 = RSACryptUtil.verify(concatString.getBytes(), namiPublicKey, signatureMsg);
			logger.info("验证签名状态:{}" + status2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signatureMsg;
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param publicKey
	 * @param factorList
	 * @return
	 */
	public static boolean verifyResponseData(String signature, String publicKey, List<String> factorList) {
		// 构造签名因子：拼装业务级参数
		Collections.sort(factorList);
		StringBuffer dataStr = new StringBuffer();
		for (String str : factorList) {
			dataStr.append(str);
		}
		String concatString = dataStr.toString();
		logger.info("构造响应签名因子：拼装业务级参数：{}", dataStr);
		try {
			// 验证签名
			boolean status = RSACryptUtil.verify(concatString.getBytes(), publicKey, signature);
			System.err.println("验证签名状态:\r" + status);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
