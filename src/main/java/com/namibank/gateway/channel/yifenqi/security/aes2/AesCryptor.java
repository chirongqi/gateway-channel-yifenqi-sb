package com.namibank.gateway.channel.yifenqi.security.aes2;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Description: AES加解密
 *
 * @date Jul 18, 2016
 */
public class AesCryptor {

	/** 算法名称 */
	public static final String DEFAULT_ALGORITHM = "AES";

	/** 加解密算法/模式/填充方式 */
	public static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

	private static Key key;
	private static Cipher cipher;

	private static int DEFAULT_KEY_SIZE = 256;

	/**
	 * Description: 初始化
	 * 
	 * @param keyBytes
	 *            密钥字节数组
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static void init(byte[] keyBytes) throws AesException {

		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}

		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		key = new SecretKeySpec(keyBytes, DEFAULT_ALGORITHM);
		try {
			// 初始化cipher
			cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, "BC");
		} catch (NoSuchAlgorithmException e) {
			throw new AesException("No Such Algorithm", e);
		} catch (NoSuchPaddingException e) {
			throw new AesException("No Such Padding", e);
		} catch (NoSuchProviderException e) {
			throw new AesException("No Such Provider", e);
		}
	}

	/**
	 * Description: 随机生成256位AES密钥
	 * 
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static byte[] randomAesKey() throws AesException {
		return randomAesKey(DEFAULT_KEY_SIZE);
		// return Base64.encodeBase64String(randomAesKey(DEFAULT_KEY_SIZE));
	}

	/**
	 * Description: 随机生成AES密钥
	 * 
	 * @param keysize
	 *            密钥位数（只接受128位、192位、256位）
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static byte[] randomAesKey(int keysize) throws AesException {
		try {
			// 实例化密钥生成器
			KeyGenerator kg = KeyGenerator.getInstance(DEFAULT_ALGORITHM);
			// 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
			kg.init(keysize);
			// 生成密钥
			SecretKey secretKey = kg.generateKey();
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new AesException("No Such Algorithm", e);
		}
	}

	// 生成密钥
	// public static byte[] generateKey() throws Exception {
	// Security.addProvider(new
	// org.bouncycastle.jce.provider.BouncyCastleProvider());
	// KeyGenerator keyGenerator = KeyGenerator.getInstance(DEFAULT_ALGORITHM);
	// keyGenerator.init(256);
	// SecretKey key = keyGenerator.generateKey();
	// return key.getEncoded();
	// }
	//
	// // 转化成JAVA的密钥格式
	// public static byte[] convertToKey(byte[] keyBytes) throws Exception {
	// SecretKey secretKey = new SecretKeySpec(keyBytes, DEFAULT_ALGORITHM);
	// return secretKey.getEncoded();
	// }

	/**
	 * Description: 加密
	 * 
	 * @param data
	 *            要加密的字符串
	 * @param base64EncodingKey
	 *            加密密钥（base64编码）
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static String encrypt(String data, String base64EncodingKey) throws AesException {
		byte[] dataBytes = data.getBytes();
		byte[] keyBytes = Base64.decodeBase64(base64EncodingKey);
		return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));
	}

	/**
	 * Description: 加密
	 * 
	 * @param data
	 *            要加密的字符串
	 * @param keyBytes
	 *            加密密钥字节数组
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static String encrypt(String data, byte[] keyBytes) throws AesException {
		byte[] dataBytes = data.getBytes();
		return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));
	}

	/**
	 * Description: 加密
	 * 
	 * @param dataBytes
	 *            要加密的字符串字节数组
	 * @param keyBytes
	 *            加密密钥数组
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static byte[] encrypt(byte[] dataBytes, byte[] keyBytes) throws AesException {
		byte[] encryptedData = null;
		init(keyBytes);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(get16BitIV(keyBytes)));
			encryptedData = cipher.doFinal(dataBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new AesException("Invalid Key", e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new AesException("Invalid Algorithm Parameter", e);
		} catch (IllegalBlockSizeException e) {
			throw new AesException("Illegal Block Size", e);
		} catch (BadPaddingException e) {
			throw new AesException("Bad Padding", e);
		}
		return encryptedData;
	}

	/**
	 * Description: 解密
	 * 
	 * @param base64EncodingEncryptedData
	 *            要解密的字符串（base64编码）
	 * @param base64EncodingKey
	 *            解密密钥（base64编码）
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static String decrypt(String base64EncodingEncryptedData, String base64EncodingKey) throws AesException {
		byte[] encryptedDataBytes = Base64.decodeBase64(base64EncodingEncryptedData);
		byte[] keyBytes = Base64.decodeBase64(base64EncodingKey);
		return new String(decrypt(encryptedDataBytes, keyBytes));
	}

	/**
	 * Description: 解密
	 * 
	 * @param base64EncodingEncryptedData
	 *            要解密的字符串（base64编码）
	 * @param keyBytes
	 *            解密密钥字节数组
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static String decrypt(String base64EncodingEncryptedData, byte[] keyBytes) throws AesException {
		byte[] encryptedDataBytes = Base64.decodeBase64(base64EncodingEncryptedData);
		return new String(decrypt(encryptedDataBytes, keyBytes));
	}

	/**
	 * Description: 解密
	 * 
	 * @param dataBytes
	 *            要解密的加密内容字节数组
	 * @param keyBytes
	 *            解密密钥字节数组
	 * @return
	 *
	 * @throws AesException
	 * @date Jul 18, 2016
	 */
	public static byte[] decrypt(byte[] dataBytes, byte[] keyBytes) throws AesException {
		byte[] decryptedText = null;
		init(keyBytes);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(get16BitIV(keyBytes)));
			decryptedText = cipher.doFinal(dataBytes);
		} catch (InvalidKeyException e) {
			throw new AesException("Invalid Key", e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new AesException("Invalid Algorithm Parameter", e);
		} catch (IllegalBlockSizeException e) {
			throw new AesException("Illegal Block Size", e);
		} catch (BadPaddingException e) {
			throw new AesException("Bad Padding", e);
		}
		return decryptedText;
	}

	/**
	 * Description: 获取补齐用的字节数组
	 * 
	 * @param data
	 * @return
	 *
	 * @date Jul 18, 2016
	 */
	public static byte[] get16BitIV(byte[] data) {
		byte[] iv = new byte[16];
		System.arraycopy(data, 0, iv, 0, 16);
		return iv;
	}

}
