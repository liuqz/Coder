import com.wftest.Coder;
import com.wftest.asymmetry.RSACoder;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by Liuqz on 2017-6-27.
 */
public class UtilsTest {
    private static Logger logger = Logger.getLogger(UtilsTest.class);

    @Test
    public void test() throws Exception {

        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPMy82jfb8XHvK1xpb6ZOC+clebnTv7O8ngKYnxiBYLTQT9RLXjGLZ24Z5OZ/dfyX2GoRvKIx8NSqo2MMmkFobSCwUcortZH2O+1N9ID7g/GV9juSIbWIW3+HYM9hDSRORwkotqjc+ni6Qg1ZdSoosqaBdSd2JkXcDLxz0Es2FUwIDAQAB";
        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI8zLzaN9vxce8rXGlvpk4L5yV5udO/s7yeApifGIFgtNBP1EteMYtnbhnk5n91/JfYahG8ojHw1KqjYwyaQWhtILBRyiu1kfY77U30gPuD8ZX2O5IhtYhbf4dgz2ENJE5HCSi2qNz6eLpCDVl1KiiypoF1J3YmRdwMvHPQSzYVTAgMBAAECgYBvwMkisQ3ECkTNmqrGefWVrGv7FwaJSwWkdWC1/4e0aKqQvJxUvQlT7V73cgIRsK2mbzFVMnbYZAGDvY4mpVa7cRONhxQQxOg2JoN6scS+NPHZU4obeUC4n5pj64tGBXuyavzb3Xfg6LZW6j1IyjiF4kFFlQIa821IIPq6paXh2QJBANYevghn+DRdSRya0fDDOEsieki7Yy9C9blPRp7IPzjQU3w0x8tQTBsH81UwCQP3hFj/66oLKYBXLM3IycvWnBcCQQCrNWHvDm51Wbv/jvkNihWmqkRxL01RpD+ERKMpV0O7rueG8661EtADJaeiNBGd1cYoelLer5VFeXS2xrmeinolAkEAo5Y9LYLxeGBE+Vu3RbCAk7a1ole4AWWxAnD0zV7EHo3bGqvgcQ7Z9lTurYjTenpiXPS5geKhRHJVwqrCsb825QJAJpXHVosYFdZ9aiEcvBJswr0I48FXWZj8v6CAbSa/myLWBzIQI7rzqmHoH8RAXCP7ns/CsXE84QV4amDrIHHxCQJAKUjTy1Kbqr/Aspe6b70x+nX3CGsW/7M57qJzpEa4h34OIVKiTCkcsA9fBYQeovsXANtaB4h10U/2XMjCw82u4A==";
        String hardinfo = getSysinfo();
        //logger.info("Hardinfo:" + hardinfo);
        logger.info("=== 公钥加密，私钥解密 ===");
        String encodeWithPub = RSACoder.byPubKeyWithStr(hardinfo, pubKey,true);
        logger.info("Encode:" + encodeWithPub);
        String decodeWithPri = RSACoder.byPriKeyWithStr(encodeWithPub, priKey,false);
        logger.info("Decode:" + decodeWithPri);
        boolean eqs = hardinfo.equals(decodeWithPri);
        logger.info("是否一致 ：" + eqs);

        logger.info("=== 私钥加密，公钥解密 ===");
        String encodeWithPri = RSACoder.byPriKeyWithStr(hardinfo, priKey, true);
        logger.info("Encode:" + encodeWithPri);
        String decodeWithPub = RSACoder.byPubKeyWithStr(encodeWithPri, pubKey, false);
        logger.info("Decode:" + decodeWithPub);
        boolean eqs1 = hardinfo.equals(decodeWithPub);
        logger.info("是否一致 ：" + eqs1);
    }

    private String getSysinfo() throws Exception {
        String sep = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        String mac = "D0-17-C2-D0-48-2A";
        sb.append("Host Name = ").append(SystemUtils.getHostName()).append(sep);
        sb.append("OS Name = ").append(SystemUtils.OS_NAME).append(sep);
        sb.append("Mac Info = ").append(mac).append(sep);
        sb.append("Hard Info = ").append(Coder.encryptBASE64(mac.getBytes())).append(sep);
        return sb.toString();
    }
}
