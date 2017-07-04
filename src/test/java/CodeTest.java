import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created by Liuqz on 2017-7-4.
 */
public class CodeTest {
    private Logger logger = LogManager.getLogger(CodeTest.class);

    @Test
    public void test() throws Exception {
        String origin = "AAAAA";
        logger.info("Origin: " + origin);
    }
}
