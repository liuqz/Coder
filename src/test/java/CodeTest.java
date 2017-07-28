import com.wfcode.arithmetic.BaseCoder;
import com.wfcode.arithmetic.RSACoder;

import com.wfcode.utils.SysUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


/**
 * Created by Liuqz on 2017-7-4.
 */
public class CodeTest {
    private Logger logger = Logger.getLogger(getClass());
    private String pubKey;
    private String priKey;
    private String origin;

    @Before
    public void init() throws Exception{
        origin = SysUtil.getSysInfo();
        //logger.info("原始信息: " + origin);
        Map<String, Object> keyMap = RSACoder.generateKey();
        pubKey = RSACoder.getPublicKeyString(keyMap);
        priKey = RSACoder.getPrivateKeyString(keyMap);

    }

    @Test
    public void base() {
        String sysinfo = BaseCoder.encryptBase64(origin);
        logger.info("Base64加密: " + sysinfo);
        String base64safe = BaseCoder.encryptBase64Safe(origin);
        logger.info("Base64安全加密: " + base64safe);
        logger.info("Base64安全解密: " + BaseCoder.decryptBase64(base64safe));
        logger.info("MD5加密: " + BaseCoder.encryptMD5(origin));
        logger.info("SHA1加密: " + BaseCoder.encryptSHA1(origin));
        logger.info("SHA256加密: " + BaseCoder.encryptSHA256(origin));
        logger.info("SHA512加密: " + BaseCoder.encryptSHA512(origin));
        String hKey = "new";
        logger.info("HMAC密钥: " + hKey);
        String hmace = BaseCoder.encryptHMACBySha512(hKey, origin);
        logger.info("HMAC加密: " + hmace);
    }

    @Test
    public void rsa() throws Exception {
        logger.info("公钥：" + pubKey);
        logger.info("私钥：" + priKey);
        String sysByPri = RSACoder.byPriKeyWithStr(BaseCoder.encryptBase64(origin), priKey, true);
        logger.info("RSA私钥加密: " + sysByPri);
        String sysByPub = RSACoder.byPubKeyWithStr(BaseCoder.encryptBase64(origin), pubKey, true);
        logger.info("RSA公钥加密: " + sysByPub);
    }

    public void aes() throws Exception {

    }

}
