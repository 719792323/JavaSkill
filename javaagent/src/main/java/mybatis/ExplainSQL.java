package mybatis;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExplainSQL {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Explain> doExplain(String url, String user, String passwd, String sql) {
        sql = "EXPLAIN " + sql;
        List<Explain> explains = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, passwd);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                explains.add(
                        Explain.builder().id(resultSet.getInt(1))
                                .selectType(resultSet.getString(2))
                                .table(resultSet.getString(3))
                                .partitions(resultSet.getString(4))
                                .type(resultSet.getString(5))
                                .possibleKeys(resultSet.getString(6))
                                .key(resultSet.getString(7))
                                .keyLen(resultSet.getInt(8))
                                .ref(resultSet.getString(9))
                                .rows(resultSet.getInt(10))
                                .filtered(resultSet.getDouble(11))
                                .extra(resultSet.getString(12)).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return explains;
    }

    public static void main(String[] args) {
        List<Explain> explains = doExplain("jdbc:mysql://192.168.0.242:3306/mybatis?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8", "root", "123456", "select *        from people;");
        System.out.println(explains);
    }
}

@Builder
@ToString
class Explain {
    private Integer id;
    private String selectType;
    private String table;
    private String partitions;
    private String type;
    private String possibleKeys;
    private String key;
    private Integer keyLen;
    private String ref;
    private Integer rows;
    private Double filtered;
    private String extra;
}