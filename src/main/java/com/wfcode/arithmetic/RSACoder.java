package com.wfcode.arithmetic;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA安全编码组件
 *
 * Created by Liuqz on 2017-6-23.
 */
public abstract class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA512withRSA";
    public static final String CIPHAR_ALGORITHM = "RSA/ECB/PKCS1Padding ";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int ENCRYPT_BLOCK_MAX = 117;
    private static final int DECRYPT_BLOCK_MAX = 128;

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
    public static String getPublicKeyString(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    private static PublicKey getPublicKey(Map<String, Object> keyMap) throws Exception {
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        return publicKey;
    }

    /**
     * 由公钥字符串获得公钥对象
     *
     * @param key
     * @return
     */
    public static PublicKey getPublicKeyFromBase64Str(String key) throws Exception {
        byte[ ] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKeyString(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    private static PrivateKey getPrivateKey(Map<String, Object> keyMap) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        return privateKey;
    }

    /**
     * 由私钥字符串获得私钥对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromBase64Str(String key) throws Exception {
        byte[ ] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用公钥加密/解密 - byte
     *
     * @param data
     * @param publicKey
     * @param encrypt
     * @return
     * @throws Exception
     */
    private static byte[] byPubKey(byte[] data, PublicKey publicKey, boolean encrypt) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        int mode = encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
        cipher.init(mode, publicKey);
        return process(data, cipher, encrypt);
    }

    /**
     * 使用公钥加密/解密
     *
     * @param str
     * @param publicKey
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String byPubKeyWithStr(String str, String publicKey, boolean encrypt) throws Exception {
        byte[] data = encrypt ? str.getBytes("UTF-8") : Base64.decodeBase64(str);
        return toString(byPubKey(data,getPublicKeyFromBase64Str(publicKey),encrypt), encrypt);
    }

    /**
     * 使用私钥加密/解密 - byte
     *
     * @param data
     * @param privateKey
     * @param encrypt
     * @return
     * @throws Exception
     */
    private static byte[] byPriKey(byte[] data, PrivateKey privateKey, boolean encrypt) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        int mode = encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
        cipher.init(mode, privateKey);
        return process(data, cipher, encrypt);
    }

    /**
     * 加密/解密过程
     *
     * @param data
     * @param cipher
     * @param encrypt
     * @return
     * @throws Exception
     */
    private static byte[] process(byte[] data, Cipher cipher, boolean encrypt) throws Exception {
        int blockSize = encrypt ? ENCRYPT_BLOCK_MAX : DECRYPT_BLOCK_MAX;
        int dateLength = data.length;
        byte[] outData = new byte[0];
        for (int i=0; i<dateLength; i+=blockSize){
            byte[] subArray = ArrayUtils.subarray(data, i, i+blockSize);
            byte[] outArray = cipher.doFinal(subArray);
            outData = ArrayUtils.addAll(outData, outArray);
        }
        return outData;
    }

    /**
     * 使用私钥加密/解密
     *
     * @param str
     * @param privateKey
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String byPriKeyWithStr(String str,String privateKey, boolean encrypt) throws Exception {
        byte[] data = encrypt ? str.getBytes("UTF-8") : Base64.decodeBase64(str);
        return toString(byPriKey(data,getPrivateKeyFromBase64Str(privateKey), encrypt), encrypt);
    }

    /**
     * 加密/解密结果转换为字符串
     *
     * @param data
     * @param encode
     * @return
     */
    private static String toString(byte[] data, boolean encode) throws Exception {
        return encode ? Base64.encodeBase64String(data) : new String(data, "UTF-8");
    }

}
