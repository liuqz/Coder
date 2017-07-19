package com.wfcode.arithmetic;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 基础加密组件
 *
 * Created by Liuqz on 2017-6-23.
 */
public abstract class BaseCoder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_HMAC = "HmacSHA512";

    /**
     * BASE64解码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String key) throws Exception {
        return new String(Base64.decodeBase64(key), "UTF-8");
    }

    /**
     * BASE64编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String key) throws Exception {
        return Base64.encodeBase64String(key.getBytes("UTF-8"));
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes("UTF-8"));
        return Base64.encodeBase64String(md5.digest());
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data.getBytes("UTF-8"));
        return Base64.encodeBase64String(sha.digest());
    }

    /**
     * 生成HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String generateHMACKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_HMAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptHMAC(String data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBase64(key).getBytes(), KEY_HMAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return Base64.encodeBase64String(mac.doFinal(data.getBytes("UTF-8")));
    }

}
