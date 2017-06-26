import com.wftest.Coder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Liuqz on 2017-6-23.
 */
public class CoderTest {
    private static Logger logger = Logger.getLogger(CoderTest.class);

    @Test
    public void test() throws Exception{
        String beforeEncode = "OS: Windows 10 企业版";
        byte[] beforeByte = beforeEncode.getBytes();
        String afterEncode = Coder.encryptBASE64(beforeByte);
        logger.info("Base64Encode: " + afterEncode);
        String afterDecode = new String(Coder.decryptBASE64(afterEncode));
        logger.info("Base64Decode: " + afterDecode);
        BigInteger sha = new BigInteger(Coder.encryptSHA(beforeByte));
        logger.info("SHAEncode: " + sha.toString(32));
        String hmacKey = Coder.initHMACKey();
        logger.info("HMAC Key: " + hmacKey);
        BigInteger mac = new BigInteger(Coder.encryptHMAC(beforeByte, hmacKey));
        logger.info("HMACEncode: " + mac.toString(16));
    }
}
