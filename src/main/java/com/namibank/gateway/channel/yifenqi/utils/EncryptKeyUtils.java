package com.namibank.gateway.channel.yifenqi.utils;

import org.apache.commons.codec.binary.Base64;

import com.namibank.gateway.channel.yifenqi.security.RSACryptUtil;

/**
 * 请求主数据Aes加密秘钥
 * 
 * @author chirq
 *
 */
public class EncryptKeyUtils {

	/**
	 * 将aesKey通过公钥加密,并转成Base64
	 * 
	 * @param aesKey
	 * @param publicKey
	 * @return
	 */
	public static String getEncodeEncryptKey(byte[] aesKey, String publicKey) {
		if (aesKey.length <= 0) {
			return "";
		}
		if (publicKey == null || publicKey.length() <= 0) {
			return "";
		}
		// 获取data的加密秘钥
		// encryptKey = base64encode( rsaencrypt(AESKeyBytes, publicKey))
		String encryptKey = "";
		try {
			encryptKey = Base64.encodeBase64String(RSACryptUtil.encryptByPublicKey(aesKey, publicKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptKey;
	}

	/**
	 * 将加密并Base64之后的aesKey通过私钥解密
	 * 
	 * @param encryptKey
	 * @param privateKey
	 * @return
	 */
	public static byte[] getDecodeEncryptKey(String encryptKey, String privateKey) {
		byte[] aesKey = null;
		try {
			aesKey = RSACryptUtil.decryptByPrivateKey(Base64.decodeBase64(encryptKey), privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aesKey;
	}

}
