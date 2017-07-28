package com.wfcode.arithmetic;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;


/**
 * 基础加密组件
 *
 * Created by Liuqz on 2017-6-23.
 */
public abstract class BaseCoder {

    /**
     * BASE64解码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String key) {
        return new String(Base64.decodeBase64(key));
    }

    /**
     * BASE64编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String key) {
        return Base64.encodeBase64String(key.getBytes());
    }

    /**
     * BASE64安全编码
     *
     * @param key
     * @return
     */
    public static String encryptBase64Safe(String key) {
        return Base64.encodeBase64URLSafeString(key.getBytes());
    }


    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA1(String data) {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA256(String data) {
        return DigestUtils.sha256Hex(data);
    }

    /**
     * SHA512加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA512(String data) {
        return DigestUtils.sha512Hex(data);
    }

    /**
     * HMAC加密
     *
     * @param key
     * @param data
     * @return
     */
    public static String encryptHMACBySha512(String key, String data) {
        return HmacUtils.hmacSha512Hex(key, data);
    }

}
