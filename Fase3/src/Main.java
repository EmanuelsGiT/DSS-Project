package src;

import src.DAOs.DataBaseData;
import src.Controllers.Controller;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println(DataBaseData.getConnectionNoDatabase());

        Controller controller = new Controller();
        controller.run();
    }
}
