package com.namibank.gateway.channel.yifenqi.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA 这种算法1978年就出现了，它是第一个既能用于数据加密也能用于数字签名的算法。<br>
 * RSA同时有两把钥匙，公钥与私钥。同时支持数字签名。<br>
 * 数字签名的意义在于，对传输过来的数据进行校验。确保数据在传输过程中不被修改。
 * <ul>
 * 流程分析：
 * <li>甲方构建密钥对儿，将公钥公布给乙方，将私钥保留。</li>
 * <li>甲方使用私钥加密数据，然后用私钥对加密后的数据签名，发送给乙方签名以及加密后的数据；乙方使用公钥、签名来验证待解密数据是否有效，如果有效使用公钥对数据解密。</li>
 * <li>乙方使用公钥加密数据，向甲方发送经过加密后的数据；甲方获得加密数据，通过私钥解密。</li>
 * <ul>
 * 
 * 
 */
public class RSACryptUtil {

	/**
	 * 日志组件
	 */
	protected static final Logger logger = LoggerFactory.getLogger(RSACryptUtil.class);

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	// public static final String SIGNATURE_MD5_ALGORITHM = "MD5withRSA";

	/**
	 * 签名算法:RSA+SHA1
	 */
	public static final String SIGNATURE_SHA1_ALGORITHM = "SHA1withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) {

		try {
			/**
			 * 解密由base64编码的私钥
			 */
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			/**
			 * 构造PKCS8EncodedKeySpec对象
			 */
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			/**
			 * KEY_ALGORITHM 指定的加密算法
			 */
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			/**
			 * 取私钥匙对象
			 */
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			/**
			 * 用私钥对信息生成数字签名
			 */
			Signature signature = Signature.getInstance(SIGNATURE_SHA1_ALGORITHM);
			signature.initSign(priKey);
			signature.update(data);
			return Base64.encodeBase64String(signature.sign());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		/**
		 * 解密由base64编码的公钥
		 */
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		/**
		 * 构造X509EncodedKeySpec对象
		 */
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		/**
		 * KEY_ALGORITHM 指定的加密算法
		 */
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		/**
		 * 取公钥匙对象
		 */
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_SHA1_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		/**
		 * 验证签名是否正常
		 */
		return signature.verify(Base64.decodeBase64(sign));
	}

	/**
	 * 用私钥解密并返回String
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKeyStr(byte[] data, String key) throws Exception {
		return new String(decryptByPrivateKey(data, key), "UTF-8");
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		if (data == null || data.length == 0) {
			throw new Exception("[解密内容为空]");
		}
		/**
		 * 待加解密的消息
		 */
		byte[] msg = data;
		/**
		 * 对密钥解密
		 */
		byte[] keyBytes = Base64.decodeBase64(key);
		/**
		 * 取得私钥
		 */
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		/**
		 * 对数据解密
		 */
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int inputLen = msg.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/**
		 * 对数据分段解密
		 */
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(msg, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(msg, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		/**
		 * 对密钥解密
		 */
		byte[] keyBytes = Base64.decodeBase64(key);
		/**
		 * 取得公钥
		 */
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		/**
		 * 对数据解密
		 */
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 用公钥加密并转成base64码
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKeyTOBase64Str(byte[] data, String key) throws Exception {
		// String ms = Hex.encodeHexString(encryptedData);
		String ms = Base64.encodeBase64String(encryptByPublicKey(data, key));
		return ms;
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		if (data == null || data.length <= 0) {
			throw new Exception("[加密内容为空]");
		}
		/**
		 * 对公钥解密
		 */
		byte[] keyBytes = Base64.decodeBase64(key);
		/**
		 * 取得公钥
		 */
		// byte[] msg = data.getBytes("UTF-8");
		byte[] msg = data;
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		/**
		 * 对数据加密
		 */
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int inputLen = msg.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/**
		 * 对数据分段加密
		 */
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(msg, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(msg, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();

		return encryptedData;
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		/**
		 * 对密钥解密
		 */
		byte[] keyBytes = Base64.decodeBase64(key);
		/**
		 * 取得私钥
		 */
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		/**
		 * 对数据加密
		 */
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 动态生成RSA密钥对
	 * 
	 * @return
	 */
	public static Map<String, Object> createRSAKey() {

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		KeyPairGenerator keyPairGen;
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			/**
			 * 公钥
			 */
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			/**
			 * 私钥
			 */
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);

		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}

		return keyMap;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);
	}

	// public static void main(String[] args) throws Exception {
	// Map<String, Object> keyMap = createRSAKey();
	// System.out.println(getPrivateKey(keyMap));
	// System.out.println(getPublicKey(keyMap));
	// }
}