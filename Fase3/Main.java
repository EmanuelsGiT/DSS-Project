import DAOs.DataBaseData;

import java.sql.SQLException;

public class Main 
{
    public static void main(String[] args) throws SQLException 
    {
        System.out.println(DataBaseData.getConnectionNoDatabase());
    }
}