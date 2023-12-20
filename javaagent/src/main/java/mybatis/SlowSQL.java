package mybatis;

public class SlowSQL {
    public static final ThreadLocal<SlowSQL> THREAD_LOCAL = new ThreadLocal<>();
    private static final int maxDuration = 0;
    private long startTime;
    private long endTime;
    private String sql;

    private SlowSQL(String sql) {
        this.sql = sql;
    }

    public static void start(String sql) {
        SlowSQL slowSQL = new SlowSQL(sql);
        slowSQL.startTime = System.currentTimeMillis();
        THREAD_LOCAL.set(slowSQL);
    }

    private void check() {
        if (endTime - startTime > maxDuration) {
            System.out.println(this);
        }
    }

    public static void end() {
        SlowSQL slowSQL = THREAD_LOCAL.get();
        slowSQL.endTime = System.currentTimeMillis();
        THREAD_LOCAL.remove();
        slowSQL.check();
    }


    @Override
    public String toString() {
        return "SlowSQL{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", cost=" + (endTime - startTime) + "ms" +
                ", sql=" + sql.replaceAll("\n", " ") +
                '}';
    }


}