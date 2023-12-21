package mybatis;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.zaxxer.hikari.pool.ProxyConnection;
import lombok.ToString;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;

@ToString
public class SlowSQL {
    public static final ThreadLocal<SlowSQL> THREAD_LOCAL = new ThreadLocal<>();
    private static final int maxDuration = 0;
    private long startTime;
    private long endTime;
    private String sql;
    private long cost;
    private String host;
    private String dataBase;

    private String user;

    private String passwd;

    private String url;

    private List<Explain> explains;

    private SlowSQL() {
    }


    public static void start(String sql, Statement statement) throws Exception {
        SlowSQL slowSQL = new SlowSQL();
        slowSQL.sql = sql.replaceAll("\n", "");
        slowSQL.startTime = System.currentTimeMillis();
        slowSQL.resolveStatement(statement);
        THREAD_LOCAL.set(slowSQL);
    }


    public static void end() {
        SlowSQL slowSQL = THREAD_LOCAL.get();
        slowSQL.endTime = System.currentTimeMillis();
        slowSQL.cost = slowSQL.endTime - slowSQL.startTime;
        slowSQL.explains = ExplainSQL.doExplain(slowSQL.url, slowSQL.user, slowSQL.passwd, slowSQL.sql);
        THREAD_LOCAL.remove();
        slowSQL.check();
    }

    //获取hikari连接池的连接信息
    private void resolveStatement(Statement statement) throws Exception {
        ProxyConnection connection = (ProxyConnection) statement.getConnection();
        Field delegate = ProxyConnection.class.getDeclaredField("delegate");
        delegate.setAccessible(true);
        ConnectionImpl impl = (ConnectionImpl) delegate.get(connection);
        this.host = impl.getHostPortPair();
        this.dataBase = impl.getDatabase();
        this.user = impl.getUser();
        this.url = impl.getURL();
        Field password = impl.getClass().getDeclaredField("password");
        password.setAccessible(true);
        this.passwd = (String) password.get(impl);
    }


    private void check() {
        if (endTime - startTime > maxDuration) {
            System.out.println(this);
        }
    }

}