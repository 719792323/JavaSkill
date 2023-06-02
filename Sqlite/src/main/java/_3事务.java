import java.sql.SQLException;
import java.util.Random;

public class _3事务 {


    public static void main(String[] args) throws SQLException {
        //关闭自动提交
        _1基本操作.delete(_1基本操作.deleteSql);
        _1基本操作.connection.setAutoCommit(false);
        //可以搭配savepoint使用
//        Savepoint savepoint=null;
        try {
            _1基本操作.insertValues(_1基本操作.insertValuesSql);
//            savepoint = _1基本操作.connection.setSavepoint();
            if (new Random().nextInt() % 2 == 0) {
                throw new RuntimeException();
            }
            _1基本操作.connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            _1基本操作.connection.rollback(savepoint);
            _1基本操作.connection.rollback();
        }
    }
}
