package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseData {
    private static final String DB_Driver = "jdbc:mariadb";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_DATABASE = "F1Manager";

    private static final String DB_USER = "F1Manager";
    private static final String DB_PASSWORD = "root";

    private static final String DB_URL = DB_Driver+"://"+DB_HOST+":"+DB_PORT+"/";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_DATABASE, DB_USER, DB_PASSWORD);
    }
    public static Connection getConnectionNoDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static String getDatabaseName() {
        return DB_DATABASE;
    }
}

