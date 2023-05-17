import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

//https://blog.csdn.net/qq_38322527/article/details/125717093
public class _1基本操作 {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static String dbPath = "Sqlite/data/db.file";

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


    public static void createTable() throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Code\\Java\\JavaSkill\\Sqlite\\src\\main\\resources\\table.sql"))) {
            StringBuilder builder = new StringBuilder();
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                builder.append(s);
            }
            System.out.println(builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        createTable();
    }
}
