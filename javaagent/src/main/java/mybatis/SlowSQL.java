package mybatis;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.zaxxer.hikari.pool.ProxyConnection;

import java.lang.reflect.Field;
import java.sql.Statement;

public class SlowSQL {
    public static final ThreadLocal<SlowSQL> THREAD_LOCAL = new ThreadLocal<>();
    private static final int maxDuration = 0;
    private long startTime;
    private long endTime;
    private String sql;

    private long cost;
    private String host;
    private String dataBase;

    private SlowSQL(String sql) {
        this.sql = sql.replaceAll("\n", "");
    }


    public static void start(String sql, Statement statement) throws Exception {
        SlowSQL slowSQL = new SlowSQL(sql);
        slowSQL.startTime = System.currentTimeMillis();
        slowSQL.resolveStatement(statement);
        THREAD_LOCAL.set(slowSQL);
    }

    //((ConnectionImpl)((ProxyConnection)statement.getConnection()).delegate)
    //获取hikari连接池的连接信息
    private void resolveStatement(Statement statement) throws Exception {
        ProxyConnection connection = (ProxyConnection) statement.getConnection();
        Field delegate = ProxyConnection.class.getDeclaredField("delegate");
        delegate.setAccessible(true);
        ConnectionImpl impl = (ConnectionImpl) delegate.get(connection);
        this.host = impl.getHostPortPair();
        this.dataBase = impl.getDatabase();
    }


    private void check() {
        if (endTime - startTime > maxDuration) {
            System.out.println(this);
        }
    }

    public static void end() {
        SlowSQL slowSQL = THREAD_LOCAL.get();
        slowSQL.endTime = System.currentTimeMillis();
        slowSQL.cost = slowSQL.endTime - slowSQL.startTime;
        THREAD_LOCAL.remove();
        slowSQL.check();
    }


    @Override
    public String toString() {
        return "SlowSQL{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", sql='" + sql + '\'' +
                ", cost=" + cost +
                ", host='" + host + '\'' +
                ", dataBase='" + dataBase + '\'' +
                '}';
    }
}