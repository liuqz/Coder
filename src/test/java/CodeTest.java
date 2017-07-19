import com.wfcode.arithmetic.BaseCoder;
import com.wfcode.arithmetic.RSACoder;

import com.wfcode.utils.SysUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


/**
 * Created by Liuqz on 2017-7-4.
 */
public class CodeTest {
    private Logger logger = LogManager.getLogger(getClass());
    private String pubKey;
    private String priKey;

    @Before
    public void init() throws Exception{
        Map<String, Object> keyMap = RSACoder.initKey();
        pubKey = RSACoder.getPublicKeyString(keyMap);
        priKey = RSACoder.getPrivateKeyString(keyMap);
        logger.info("公钥：" + pubKey);
        logger.info("私钥：" + priKey);
    }

    @Test
    public void test() throws Exception {
        String origin = SysUtil.getSysInfo();
        logger.info("原始信息: " + origin);
        String sysinfo = BaseCoder.encryptBase64(origin);
        logger.info("Base64加密: " + sysinfo);
        String md5 = BaseCoder.encryptMD5(origin);
        logger.info("MD5加密: " + md5);
        String sha = BaseCoder.encryptSHA(origin);
        logger.info("SHA加密: " + sha);
        String hmacKey = BaseCoder.generateHMACKey();
        logger.info("HMAC密钥: " + hmacKey);
        String hmac = BaseCoder.encryptHMAC(origin, hmacKey);
        logger.info("HMAC加密: " + hmac);
        String sysByPri = RSACoder.byPriKeyWithStr(sysinfo, priKey, true);
        logger.info("RSA私钥加密: " + sysByPri);
        String sysByPub = RSACoder.byPubKeyWithStr(sysinfo, pubKey, true);
        logger.info("RSA公钥加密: " + sysByPub);
    }

}
