import java.sql.*;

/**
 * sqlite的元信息存储在sqlite_master表中
 * 该表记录了数据库所有表信息和索引信息
 */
public class _2元信息 {

    private static Connection connection;
    private static String dbPath = "Sqlite/data/db.file";

    //建立连接
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 查询sqlite中所有的table
     * SELECT * FROM sqlite_master WHERE type='table';
     */
    public static void showTables() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sqlite_master WHERE type='table'");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            //表名
            System.out.println(resultSet.getString(2));
            //建表sql
            System.out.println(resultSet.getString(5));
        }
    }




    public static void main(String[] args) throws SQLException {
        showTables();
    }
}
