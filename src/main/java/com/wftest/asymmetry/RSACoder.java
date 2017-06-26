package com.wftest.asymmetry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA安全编码组件
 *
 * Created by Liuqz on 2017-6-23.
 */
public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA512withRSA";
    public static final String CIPHAR_ALGORITHM = "RSA/ECB/PKCS1Padding ";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded()); //Base64.toBase64String(Base64.encode(key.getEncoded()));
    }

    public static PublicKey getPublic(Map<String, Object> keyMap) throws Exception {
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        return publicKey;
    }

    /**
     * 私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    public static PrivateKey getPrivate(Map<String, Object> keyMap) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        return privateKey;
    }

    /**
     * 使用公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptWithPubKey(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHAR_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptWithPriKey(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHAR_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptWithPubKey(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHAR_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptWithPriKey(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHAR_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
}
