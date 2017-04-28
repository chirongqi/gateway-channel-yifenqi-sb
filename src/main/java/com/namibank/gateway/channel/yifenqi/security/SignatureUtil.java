/**
 * @(#) SignatureUtil.java 2015年9月17日 下午5:48:28
 * 
 *      Copyright © 2015 GuangZhou Yifenqi Network Technology Co.,Ltd. All rights reserved.
 *
 */
package com.namibank.gateway.channel.yifenqi.security;

import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @Description 报文签名处理的工具类.
 *
 * @author wenjianchuang
 * 
 * @version 2.0
 *
 */
public class SignatureUtil {

	public static final String AND_SEPARATOR = "&";

	/**
	 * 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域signature
	 * 
	 * @param data
	 *            待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2String(List<String> sigElements) {

		Collections.sort(sigElements);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < sigElements.size(); i++) {
			result.append(sigElements.get(i));
			if ((i + 1) < sigElements.size()) {
				result.append(AND_SEPARATOR);
			}
		}

		return result.toString();

	}

	/**
	 * 是否验签通过
	 * 
	 * @param data
	 * @param signature
	 * @param certFile
	 * @return
	 */
	public static boolean isSignature(List<String> sigElements, String signature, String certFile) {

		try {

			String _toValiateSignature = coverMap2String(sigElements);
			byte[] _toValiateSignatureByte = DigestUtils.shaHex(_toValiateSignature).getBytes();
			String publicKey = KeyUtil.getPublicKey(certFile, Boolean.TRUE);
			return RSACryptUtil.verify(_toValiateSignatureByte, publicKey, signature);

		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

	/**
	 * 是否验签通过
	 * 
	 * @param data
	 * @param signature
	 * @param certFile
	 * @return
	 */
	public static boolean isPassSign(List<String> sigElements, String signature, String publicKey) {

		try {

			String _toValiateSignature = coverMap2String(sigElements);
			byte[] _toValiateSignatureByte = DigestUtils.shaHex(_toValiateSignature).getBytes();
			return RSACryptUtil.verify(_toValiateSignatureByte, publicKey, signature);

		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

}
