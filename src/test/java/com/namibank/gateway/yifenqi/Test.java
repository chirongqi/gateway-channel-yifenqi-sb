package com.namibank.gateway.yifenqi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.namibank.df.gateway.util.DateUtil;
import com.namibank.gateway.channel.yifenqi.bean.CreditBankcardInfo;
import com.namibank.gateway.channel.yifenqi.security.RSACryptUtil;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesCryptor;
import com.namibank.gateway.channel.yifenqi.security.aes2.AesException;

public class Test {

	static String yifenqiPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtmDFOpJxS0pe/YYgRqHyqNr3W8I8DjuFnnjpv1fYzxeo4t/N6Kp7/Fv0uD8/GqqnlhJGZk6qQ9C1Wo1iQn0amyW6d/02plADawpBFdnrGappYcd5lykSVmcNu/NkrUyv2QKlHFc5EabrArqflbpa/UextuwPgA8VU58NZ26LkjwIDAQAB";

	// 拿米私钥
	static String namiPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL2lH8huTcNFLOG9xFeJGTmBELIta+fK06RVjQnnO7liUud6wbMryIRfh4biXWXGj6f3tv8YOom/HaxPpk1OZVFCED9W4WJ4Mu67ueBVjtmGzfJ0OUrot7n7iVxb2xO+JmYVldO7Y/m/oia1CiR/IDusSToIOXcmbtcHBhGm0DTHAgMBAAECgYANFF5kzHbAiPzXdOcdwm7i8GveXhObIPoH036uuCv+VTGylVzWpqZlutD2FJasdk1iIEuWvG0NIwlA4R1vJ4EFQzMql8BnwqJe1CzD2WvsC8mtOHyTrO2MbRu9+8 o27ohesCW4ROngYTp590Y0DPCdbCgERYRvEtAlUboCQ2O1QQJBAO7WJeplLdRJB5vZN7lSUzhVB7H2cF52SdVul+SWeGrdGmay60lGAQ0QktUrT5vABMuc0fNE8yMg2j3p6Lp1pbUCQQDLRgE+jXNy28YhfIXTEvus2HaktUBm2D4oXEqb9SkaPFAvUPyvneXx1yx4Gf/BIRmb8pzH4322UoLL3dwLR34LAkEA2mCeLHK087yOo3Z6mA1pIEHHFnZhik2X9vn3EkBu/C8MW8jOj6HdhieYSice1Cc/ezj5UvjBY2Y/horgi9TUXQJBAKWeiyMBMyBRSpQ8FflNTTuePsSiXS+uXq6Zy7xYOr4CiD6VGNyppRGvhK7yV/xuUKRMP0tp4iJsBd+/Cysxe9MCQEuqKqoVTyGFwIl6CSrjSG57MS39ICyTWDQLhpMRWvrUWQ259G65WsD/QXgTUkqZ9qg0vqn2dcJGYl6Bi1HD1Pw=";
	// 拿米公钥
	static String namiPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9pR/Ibk3DRSzhvcRXiRk5gRCyLWvnytOkVY0J5zu5YlLnesGzK8iEX4eG4l1lxo+n97b/GDqJvx2sT6ZNTmVRQhA/VuFieDLuu7ngVY7Zhs3ydDlK6Le5+4l cW9sTviZmFZXTu2P5v6ImtQokfyA7rEk6CDl3Jm7XBwYRptA0xwIDAQAB";

	public static void init() {
		yifenqiPublicKey = Base64.encodeBase64String(yifenqiPublicKey.getBytes());
		System.out.println(yifenqiPublicKey);
		System.out.println(new String(Base64.decodeBase64(yifenqiPublicKey)));
		namiPrivateKey = Base64.encodeBase64String(namiPrivateKey.getBytes());
		namiPublicKey = Base64.encodeBase64String(namiPublicKey.getBytes());
	}

	static String AESKey = "B29A86FA425D439dB510A234A3E25A3E";

	static String AESIV = AESKey.substring(0, 15);

	public static void main(String[] args) throws Exception {
		// init();
		String base64EncodingAESKey = Base64.encodeBase64String(AESKey.getBytes());
		System.out.println(base64EncodingAESKey);

		// AESKeyBytes = base64_decode(Base64EncodingAESKey)，长度为32字节。
		byte[] AESKeyBytes = Base64.decodeBase64(base64EncodingAESKey);
		System.out.println(AESKeyBytes.length);
		System.out.println("字节转数组： " + new String(AESKeyBytes));
		System.out.println("1*****************************************************");
		String encryptKey = getEncodeEncryptKey(AESKey, yifenqiPublicKey);
		System.out.println(encryptKey);
		System.out.println("2*****************************************************");
		testSign();
		System.out.println("3*****************************************************");
		testDataAes();
	}
	// RSACryptUtil
	// System.out.println("=====<idCard1>=====:"+ idCard1);

	public static void testDataAes() {
		String now = DateUtil.format("yyyyMMddHHmmss", new Date());

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("merId", "1508281725030001");
		dataMap.put("merOrderId", "17040001" + now + "12345678");

		dataMap.put("txnTime", now);
		dataMap.put("txnAmt", "2000");
		CreditBankcardInfo creditBankcardInfo = new CreditBankcardInfo();
		creditBankcardInfo.setAccNo("6222081203007442250");
		creditBankcardInfo.setCvn2("112233");
		creditBankcardInfo.setIdNumber("11010119800101007X");
		creditBankcardInfo.setName("大乔");
		creditBankcardInfo.setPhone("15611110001");
		creditBankcardInfo.setValidPeriod("0821");
		dataMap.put("creditBankcardInfo", JSONObject.toJSONString(creditBankcardInfo));
		try {
			testAes(dataMap);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String testAes(Map<String, String> dataMap) throws AesException {

		// 处理data数据体
		String dataMapStr = JSONObject.toJSONString(dataMap);
		System.out.println("aes加密前数据：" + dataMapStr);
		// aes加密
		String aesData = AesCryptor.encrypt(dataMapStr, "utf-8");
		System.out.println("=====<aesData>=====:" + aesData);
		String encodeAesData = "";
		try {
			// encodeAesData = new
			// String(AESBase64.encode(URLEncoder.encode(aesData,
			// "utf-8").getBytes("utf-8")),
			// "utf-8");
			System.out.println("=====<encodeAesData>=====:" + encodeAesData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 解密测试
		String decodeData;
		try {
			decodeData = URLDecoder.decode(new String(Base64.decodeBase64(encodeAesData)), "utf-8");
			System.out.println("aes解密后：" + AesCryptor.decrypt(decodeData, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 2
		String base64data = Base64.encodeBase64String(aesData.getBytes());
		System.out.println("base64Data: " + base64data);
		String de = new String(Base64.decodeBase64(base64data));
		System.out.println("解密" + AesCryptor.decrypt(de, "utf-8"));

		System.out.println("--------------AES/CBC/PKCS7Padding-------------------------");
		String encryptData = "";
		String dencryptData = "";
		try {
			byte[] key = AesCryptor.randomAesKey(256);
			System.err.println(key.length);
			for (int i = 0; i < key.length; i++) {
				System.out.printf("%x", key[i]);
			}
			System.out.println();
			encryptData = AesCryptor.encrypt(dataMapStr, key);
			dencryptData = AesCryptor.decrypt(encryptData, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("加密  " + encryptData);
		System.out.println("解密  " + dencryptData);

		return encodeAesData;
	}

	public static void testSign() throws Exception {
		String msg = "dwfsdfsdfsd";
		System.err.println("私钥加密——公钥解密");
		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = RSACryptUtil.encryptByPrivateKey(data, namiPrivateKey);

		byte[] decodedData = RSACryptUtil.decryptByPublicKey(encodedData, namiPublicKey);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = RSACryptUtil.sign(encodedData, namiPrivateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = RSACryptUtil.verify(encodedData, namiPublicKey, sign);
		System.err.println("状态:\r" + status);
		System.out.println("--------------对字符串进行加密解密签名验证------------");
		// 产生签名
		String sign2 = RSACryptUtil.sign(msg.getBytes(), namiPrivateKey);
		System.err.println("签名:\r" + sign2);

		// 验证签名
		boolean status2 = RSACryptUtil.verify(msg.getBytes(), namiPublicKey, sign2);
		System.err.println("状态:\r" + status2);
	}

	/**
	 * 将aesKey通过公钥加密,并转成Base64
	 * 
	 * @param aesKey
	 * @param publicKey
	 * @return
	 */
	private static String getEncodeEncryptKey(String aesKey, String publicKey) {
		// encryptKey = base64encode( rsaencrypt(AESKeyBytes, publicKey))
		String encryptKey = "";
		try {
			encryptKey = Base64
					.encodeBase64String(RSACryptUtil.encryptByPublicKey(aesKey.getBytes(), yifenqiPublicKey));
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
	private static String getDecodeEncryptKey(String encryptKey, String privateKey) {
		String aesKey = "";
		try {
			aesKey = RSACryptUtil.decryptByPrivateKeyStr(Base64.decodeBase64(encryptKey), namiPrivateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(aesKey);
		return aesKey;
	}
}
