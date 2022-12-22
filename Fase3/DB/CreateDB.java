package DB;

import DAOs.DB_Data;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDatabase 
{
    public static void main(String[] args) 
    {
        try(Connection conn = DatabaseData.getConnectionNoDatabase();
            Statement stmt = conn.createStatement();) 
        {
            String sql = "CREATE DATABASE IF NOT EXISTS "+DatabaseData.getDatabaseName()+";";
            int result = stmt.executeUpdate(sql);
            if (result == 1) System.out.println("Database created successfully...");
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
