/**
 * @(#) KeyUtil.java 2015年8月28日 下午4:56:00
 * 
 *      Copyright © 2015 GuangZhou Yifenqi Network Technology Co.,Ltd. All rights reserved.
 *
 */

package com.namibank.gateway.channel.yifenqi.security;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Description  公密钥工具类
 *
 * @author wenjianchuang
 * 
 * @version 2.0
 *
 */

public class KeyUtil {

    /**
     *日志组件
     */
    protected static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

    /**
     * 获取私钥字符串,isBase64Encode为true时,
     * 私钥字符串为base64编码格式，为false时，则以16进制编码格式出现
     * 
     * @param keyStorefile
     * @param keyalias
     * @param storePass
     * @param keyPass
     * @param isBase64Encode
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(
            String keyStorefile, String keyalias, String storePass, String keyPass, boolean isBase64Encode)
            throws Exception {

        String privateKey = "";
        byte[] keyData = getPrivateKey(keyStorefile, keyalias, storePass, keyPass);
        if (isBase64Encode) {
            privateKey = Base64.encodeBase64String(keyData);
        } else {
            privateKey = Hex.encodeHexString(keyData);
        }

        return privateKey;

    }

    /**
     * 获取私钥字符串
     * 
     * @return
     */
    public static byte[] getPrivateKey(String keyStorefile, String keyalias, String storePass, String keyPass) {

        /**
         * 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中
         */
        KeyStore keyStore = CertUtil.getKeyInfo(keyStorefile, storePass, CertUtil.KEYSTORE_FILE_TYPE_JKS);
        /**
         * 获取证书私钥
         */
        PrivateKey privateKey;
        try {

            privateKey = (PrivateKey) keyStore.getKey(keyalias, keyPass.toCharArray());
            return privateKey.getEncoded();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    /**
     * 获取私钥字符串
     * 
     * @return
     */
    public static byte[] getPrivateKey(KeyStore keyStore, String keyalias, String keyPass) {

        /**
         * 获取证书私钥
         */
        PrivateKey privateKey;
        try {

            privateKey = (PrivateKey) keyStore.getKey(keyalias, keyPass.toCharArray());
            return privateKey.getEncoded();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    /**
     * 获取私钥字符串
     * 
     * @return
     */
    public static String getPrivateKey(KeyStore keyStore, String keyalias, String keyPass, boolean isBase64Encode) {

        String privateKey = "";
        byte[] keyData = getPrivateKey(keyStore, keyalias, keyalias);
        if (isBase64Encode) {
            privateKey = Base64.encodeBase64String(keyData);
        } else {
            privateKey = Hex.encodeHexString(keyData);
        }

        return privateKey;

    }

    /**
     * 获取公钥字符串
     * 
     * @return
     */
    public static byte[] getPublicKey(String certFile) {
        try {

            Certificate certificate = CertUtil.getCertInfo(certFile);
            /**
             * 得到证书文件携带的公钥
             */
            PublicKey publicKey = certificate.getPublicKey();

            return publicKey.getEncoded();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    /**
     * 获取公钥字符串,isBase64Encode为true时,
     * 字符串为base64编码格式，为false时，则以16进制编码格式出现
     * 
     * @return
     */
    public static String getPublicKey(String certFile, boolean isBase64Encode) {

        String publicKeyText = "";
        try {

            Certificate certificate = CertUtil.getCertInfo(certFile);
            /**
             * 得到证书文件携带的公钥
             */
            PublicKey publicKey = certificate.getPublicKey();

            byte[] keyData = publicKey.getEncoded();

            if (isBase64Encode) {
                publicKeyText = Base64.encodeBase64String(keyData);
            } else {
                publicKeyText = Hex.encodeHexString(keyData);
            }

            return publicKeyText;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

}
