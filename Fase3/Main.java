import DAOs.DB_Data;

import java.sql.SQLException;

public class Main 
{
    public static void main(String[] args) throws SQLException 
    {
        System.out.println(DatabaseData.getConnectionNoDatabase());
    }
}