import com.wftest.asymmetry.RSACoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * Created by Liuqz on 2017-6-26.
 */
public class RSACoderTest {
    private static Logger logger = Logger.getLogger(RSACoderTest.class);
    private String publicKey;
    private String privateKey;
    private PublicKey pubKey;
    private PrivateKey priKey;

    @Before
    public void init() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();
        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        pubKey = RSACoder.getPublic(keyMap);
        priKey = RSACoder.getPrivate(keyMap);
        logger.info("RSA公钥: " + publicKey);
        logger.info("RSA私钥: " + privateKey);
    }

    @Test
    public void test() throws Exception {
        String string = "加密数据001XAY";
        byte[] origin = string.getBytes("UTF-8");
        logger.info("原始数据：" + string);
        byte[] encodeWithPub = RSACoder.encryptWithPubKey(origin, pubKey);
        byte[] encodeWithPri = RSACoder.encryptWithPriKey(origin, priKey);
        byte[] decodeWithPub = RSACoder.decryptWithPubKey(encodeWithPri, pubKey);
        byte[] decodeWithPri = RSACoder.decryptWithPriKey(encodeWithPub, priKey);
        logger.info("使用公钥加密：" + Base64.encodeBase64String(encodeWithPub));
        logger.info("使用私钥解密：" + new String(decodeWithPri, "UTF-8"));
        logger.info("使用私钥加密：" + Base64.encodeBase64String(encodeWithPri));
        logger.info("使用公钥解密：" + new String(decodeWithPub, "UTF-8"));
    }
}
