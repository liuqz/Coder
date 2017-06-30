import com.wftest.asymmetry.RSACoder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

/**
 * Created by Liuqz on 2017-6-26.
 */
public class RSACoderTest {
    private static Logger logger = Logger.getLogger(RSACoderTest.class);
    private String publicKey;
    private String privateKey;

    @Before
    public void init() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();
        publicKey = RSACoder.getPublicKeyString(keyMap);
        privateKey = RSACoder.getPrivateKeyString(keyMap);

        logger.info("RSA公钥: " + publicKey);
        logger.info("RSA私钥: " + privateKey);
    }

    @Test
    public void test() throws Exception {
        String str = "加密数据001XAY辛亥革命中央政府条件，加密一首诗：离离原上草，一岁一枯荣，野火烧不尽，春风吹又生；远芳侵古道，晴翠接荒城，又送王孙去，萋萋满别情。";
        String strshort = "远芳侵古道，晴翠接荒城";
        logger.info("原始数据：" + str);
        String encodeWithPub = RSACoder.byPubKeyWithStr(strshort, publicKey, true);
        String encodeWithPri = RSACoder.byPriKeyWithStr(strshort, privateKey, true);
        String decodeWithPub = RSACoder.byPubKeyWithStr(encodeWithPri, publicKey, false);
        String decodeWithPri = RSACoder.byPriKeyWithStr(encodeWithPub, privateKey,false);
        logger.info("使用公钥加密：" + encodeWithPub);
        logger.info("使用私钥解密：" + decodeWithPri);
        logger.info("使用私钥加密：" + encodeWithPri);
        logger.info("使用公钥解密：" + decodeWithPub);
        //byte[] deerr = RSACoder.decryptWithPubKey(encodeWithPub, pubKey);
    }
}
