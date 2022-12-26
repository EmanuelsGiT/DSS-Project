package DB;

import DAOs.DataBaseData;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDataBase 
{
    public static void main(String[] args) 
    {
        try(Connection conn = DataBaseData.getConnectionNoDatabase();
            Statement stmt = conn.createStatement();) 
        {
            String sql = "CREATE DATABASE IF NOT EXISTS "+DataBaseData.getDatabaseName()+";";
            int result = stmt.executeUpdate(sql);
            if (result == 1) System.out.println("Database created successfully...");
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
