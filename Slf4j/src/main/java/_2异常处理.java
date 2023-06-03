import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _2异常处理 {
    static final Logger log = LoggerFactory.getLogger(_1基本使用.class);

    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("{},无法跟踪异常", e.getMessage());
            log.error("可以进行跟踪", e);
        }
    }
}
