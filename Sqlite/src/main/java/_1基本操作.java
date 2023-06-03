import java.io.*;
import java.sql.*;

//https://blog.csdn.net/qq_38322527/article/details/125717093
public class _1基本操作 {
    public static Connection connection;
    public static String dbPath = "Sqlite/data/db.file";

    //建立连接
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println(connection);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //建表
    public static void createTable() throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Code\\Java\\JavaSkill\\Sqlite\\src\\main\\resources\\table.sql"))) {
            StringBuilder builder = new StringBuilder();
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                builder.append(s);
            }
            String sql = builder.toString();
            System.out.println(sql);
            try (Statement statement = connection.createStatement()) {
                System.out.println(statement.executeUpdate(sql));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static String insertValueSql = "INSERT INTO entry VALUES (?,?,?,?)";

    //注意下标从1开始
    public static void insertValue(String sql) {
        delete(deleteSql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);
            try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
                try (DataOutputStream dataOut = new DataOutputStream(byteOut)) {
                    byte[] command = "hello".getBytes();
                    dataOut.writeInt(command.length);
                    dataOut.write(command);
                }
                preparedStatement.setBytes(4, byteOut.toByteArray());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String insertValuesSql = "INSERT INTO entry VALUES (?,?,?,?),(?,?,?,?)";

    public static void insertValues(String sql) {
        delete(deleteSql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);

            preparedStatement.setInt(5, 2);
            preparedStatement.setInt(6, 1);
            preparedStatement.setInt(7, 1);
            try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
                try (DataOutputStream dataOut = new DataOutputStream(byteOut)) {
                    byte[] command = "hello".getBytes();
                    dataOut.writeInt(command.length);
                    dataOut.write(command);
                    preparedStatement.setBytes(4, byteOut.toByteArray());
                    byteOut.reset();
                    command = "world".getBytes();
                    dataOut.writeInt(command.length);
                    dataOut.write(command);
                    preparedStatement.setBytes(8, byteOut.toByteArray());
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String deleteSql = "DELETE FROM entry";


    public static void delete(String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static String selectSql = "select * from entry";

    //注意column下标从1开始
    public static void select(String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int index = resultSet.getInt(1);
                    int term = resultSet.getInt(2);
                    int kind = resultSet.getInt(3);
                    byte[] bytes = resultSet.getBytes(4);
                    try (DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(bytes))) {
                        byte[] command = new byte[inputStream.readInt()];
                        inputStream.read(command);
                        System.out.println(String.format("%s,%s,%s,%s", index, term, kind, new String(command)));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        insertValues(insertValuesSql);
    }
}
