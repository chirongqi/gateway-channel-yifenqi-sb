package com.namibank.gateway.channel.yifenqi.utils;

import com.namibank.df.gateway.util.GatewayLogger;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesCryptor;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesException;

/**
 * AES加密、解密数据
 * 
 * @author chirq
 *
 */
public class AesDataUtils {
	private static final GatewayLogger logger = GatewayLogger.getLogger(AesDataUtils.class);

	/**
	 * aes加密数据
	 * 
	 * @param aesKey
	 * @param data
	 * @return
	 */
	public static String getEncodeData(byte[] aesKey, String data) {
		logger.info("--------------AES/CBC/PKCS7Padding-------------------------");
		String encryptData = "";
		String dencryptData = "";
		try {
			encryptData = AesCryptor.encrypt(data, aesKey);
			dencryptData = AesCryptor.decrypt(encryptData, aesKey);
		} catch (AesException e) {
			e.printStackTrace();
		}
		logger.info("加密 :{}", encryptData);
		logger.info("解密:{}" + dencryptData);
		return encryptData;
	}

	/**
	 * aes解密数据
	 * 
	 * @param aesKey
	 * @param encryptData
	 * @return
	 */
	public static String getDecodeData(byte[] aesKey, String encryptData) {
		String dencryptData = "";
		try {
			dencryptData = AesCryptor.decrypt(encryptData, aesKey);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return dencryptData;
	}
}
