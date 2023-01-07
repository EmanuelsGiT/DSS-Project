package src;

import src.DAOs.CircuitoDAO;
import src.DAOs.DataBaseData;
import src.Controllers.Controller;
import src.DAOs.UtilizadorDAO;
import src.Models.Circuitos.Circuito;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println(DataBaseData.getConnectionNoDatabase());

        UtilizadorDAO uDAO = UtilizadorDAO.getInstance();
        CircuitoDAO cDAO = CircuitoDAO.getInstance();

        uDAO.clear();
        cDAO.clear();

        Controller controller = new Controller();
        controller.run();
    }
}
