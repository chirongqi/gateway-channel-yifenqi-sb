package com.namibank.gateway.channel.yifenqi.security;

/**
 * @(#) CertUtil.java 2015年8月28日 上午11:40:50
 * 
 *      Copyright © 2015 GuangZhou Yifenqi Network Technology Co.,Ltd. All rights reserved.
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Description 证书工具类
 *
 * @author wenjianchuang
 * 
 * @version 2.0
 *
 */

public class CertUtil {

	/**
	 * JKS类型
	 */
	public final static String KEYSTORE_FILE_TYPE_JKS = "JKS";

	/**
	 * PKCS12类型
	 */
	public final static String KEYSTORE_FILE_TYPE_PKCS12 = "PKCS12";

	/**
	 * 日志组件
	 */
	protected static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

	/**
	 * 将证书文件读取为证书对象
	 * 
	 * @param keyStorefile
	 *            证书文件名
	 * @param keypwd
	 *            文件密码
	 * @param type
	 *            文件类型
	 * @return 证书对象
	 */
	public static KeyStore getKeyInfo(String keyStorefile, String storePass, String type) {

		FileInputStream fis = null;

		try {
			KeyStore keyStore = null;
			if (KEYSTORE_FILE_TYPE_JKS.equals(type)) {
				keyStore = KeyStore.getInstance(type);
			} else if (KEYSTORE_FILE_TYPE_PKCS12.equals(type)) {
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				keyStore = KeyStore.getInstance(type, "BC");
			}
			fis = new FileInputStream(keyStorefile);
			char[] nPassword = null;
			nPassword = StringUtils.isBlank(storePass) ? null : storePass.toCharArray();
			keyStore.load(fis, nPassword);
			return keyStore;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			if ((e instanceof KeyStoreException) && KEYSTORE_FILE_TYPE_PKCS12.equals(type)) {
				Security.removeProvider("BC");
			}

			return null;

		} finally {

			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}

		}
	}

	/**
	 * 将证书文件读取为证书对象
	 * 
	 * @param keyStorefile
	 *            证书文件流
	 * @param keypwd
	 *            文件密码
	 * @param type
	 *            文件类型
	 * @return 证书对象
	 */
	public static KeyStore getKeyInfo(InputStream fis, String storePass, String type) {

		try {

			KeyStore keyStore = null;
			if (KEYSTORE_FILE_TYPE_JKS.equals(type)) {
				keyStore = KeyStore.getInstance(type);
			} else if (KEYSTORE_FILE_TYPE_PKCS12.equals(type)) {
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				keyStore = KeyStore.getInstance(type, "BC");
			}

			char[] nPassword = null;
			nPassword = StringUtils.isBlank(storePass) ? null : storePass.toCharArray();
			keyStore.load(fis, nPassword);
			return keyStore;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			if ((e instanceof KeyStoreException) && KEYSTORE_FILE_TYPE_PKCS12.equals(type)) {
				Security.removeProvider("BC");
			}

			return null;

		} finally {

			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}

		}
	}

	/**
	 * 根据X509证书文件获取X509证书对象
	 * 
	 * @param certFile
	 * @return
	 */
	public static X509Certificate getCertInfo(String certFile) {

		CertificateFactory cf = null;
		FileInputStream in = null;
		X509Certificate encryptCert = null;

		try {
			cf = CertificateFactory.getInstance("X.509");
			in = new FileInputStream(certFile);
			encryptCert = (X509Certificate) cf.generateCertificate(in);

		} catch (CertificateException e) {
			logger.error("证书加载失败", e);
		} catch (FileNotFoundException e) {
			logger.error("证书加载失败,文件不存在", e);
		} finally {
			if (null != in) {
				IOUtils.closeQuietly(in);
			}
		}

		return encryptCert;
	}

	/**
	 * 从JKS格式转换为PKCS12格式
	 * 
	 * @param jksFilePath
	 *            String JKS格式证书库路径
	 * @param jksPasswd
	 *            String JKS格式证书库密码
	 * @param pfxFilePath
	 *            String PKCS12格式证书库保存文件夹
	 * @param pfxPasswd
	 *            String PKCS12格式证书库密码
	 */
	public static void covertJSKToPFX(String jksFilePath, String jksPasswd, String keypass, String pfxFolderPath,
			String pfxPasswd) {

		FileInputStream fis = null;

		FileOutputStream out = null;

		try {

			KeyStore inputKeyStore = KeyStore.getInstance(KEYSTORE_FILE_TYPE_JKS);
			fis = new FileInputStream(jksFilePath);
			char[] srcPwd = jksPasswd == null ? null : jksPasswd.toCharArray();
			char[] destPwd = pfxPasswd == null ? null : pfxPasswd.toCharArray();
			char[] keyPwd = keypass == null ? null : keypass.toCharArray();
			inputKeyStore.load(fis, srcPwd);

			KeyStore outputKeyStore = KeyStore.getInstance(KEYSTORE_FILE_TYPE_PKCS12);
			Enumeration<String> enums = inputKeyStore.aliases();
			while (enums.hasMoreElements()) {

				String keyAlias = (String) enums.nextElement();
				logger.info("alias=[" + keyAlias + "]");
				outputKeyStore.load(null, destPwd);
				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, keyPwd);
					java.security.cert.Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
					outputKeyStore.setKeyEntry(keyAlias, key, destPwd, certChain);
				}

				String fName = pfxFolderPath + keyAlias + ".pfx";
				out = new FileOutputStream(fName);
				outputKeyStore.store(out, destPwd);
				outputKeyStore.deleteEntry(keyAlias);

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		} finally {

			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}

			if (out != null) {
				IOUtils.closeQuietly(out);
			}
		}
	}
/**
	public static void main(String[] args) throws Exception {

		KeyStore keyStore = getKeyInfo("E:/pfx12/client.p12", "80815017", KEYSTORE_FILE_TYPE_PKCS12);

		String privateKey = KeyUtil.getPrivateKey(keyStore, "epdcStore", "80815018", true);
		String publicKey = KeyUtil.getPublicKey("E:/pfx12/publicKey.cer", true);

		// String
		// _toValiateSignature="accessType=2.0.0&bizType=2.0.0&encoding=2.0.0&phoneNum=11111111111&reqTimestamp=2.0.0&txnType=2.0.0&version=2.0.0";
		String _toValiateSignature = "accessType=2.0.0&bizType=2.0.0&encoding=2.0.0&instalmentSum=3&loanAmt=700000&productId=7b17910ba85d431daa5e46dd17fa74ae&productPrice=&repayAmt=699999&reqTimestamp=2.0.0&txnType=2.0.0&version=2.0.0";

		String text = "123456";
		//
		String txt = Base64.encodeBase64String(RSACryptUtil.encryptByPrivateKey(text.getBytes(), privateKey));

		System.out.println("加密数据：" + txt);

		// byte[] _toValiateSignatureByte =
		// DigestUtils.shaHex(_toValiateSignature).getBytes();
		// String signature=RSACryptUtil.sign(_toValiateSignatureByte,
		// privateKey);
		//// System.out.println(publicKey);
		// System.out.println(signature);
		//
		//
		// byte[] toValiateSignatureByte =
		// DigestUtils.shaHex(_toValiateSignature).getBytes();
		// System.out.println(RSACryptUtil.verify(toValiateSignatureByte,
		// publicKey, signature));

	}
**/
}
