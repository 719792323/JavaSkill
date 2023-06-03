import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _1基本使用 {
    static final Logger log = LoggerFactory.getLogger(_1基本使用.class);

    public static void main(String[] args) {
        log.debug("this is debug msg");
        log.info("this is info msg");
        log.warn("this is warn msg");
        log.error("this is error msg");
    }
}
