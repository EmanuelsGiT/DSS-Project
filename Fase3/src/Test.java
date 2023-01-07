package src;

import src.Controllers.Controller;
import src.DAOs.CircuitoDAO;
import src.DAOs.DataBaseData;
import src.Models.Circuitos.Circuito;
import src.Models.Circuitos.CircuitoFacade;
import src.Models.Circuitos.ICircuitos;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println(DataBaseData.getConnectionNoDatabase());

        ArrayList<Circuito.GDU> cGdu = new ArrayList<>();
        cGdu.add(Circuito.GDU.POSSIVEL);
        cGdu.add(Circuito.GDU.DIFICIL);
        cGdu.add(Circuito.GDU.IMPOSSIVEL);

        ArrayList<Circuito.GDU> rGdu = new ArrayList<>();
        rGdu.add(Circuito.GDU.POSSIVEL);
        rGdu.add(Circuito.GDU.DIFICIL);
        rGdu.add(Circuito.GDU.IMPOSSIVEL);
        rGdu.add(Circuito.GDU.POSSIVEL);
        rGdu.add(Circuito.GDU.DIFICIL);
        rGdu.add(Circuito.GDU.IMPOSSIVEL);

        Circuito c = new Circuito("portimao", 5, 2, 3, 3, rGdu, cGdu);
        Circuito d = new Circuito("lisboa", 10, 3, 4, 3, rGdu, cGdu);

        ICircuitos modelCircuito = new CircuitoFacade();
        CircuitoDAO cdao = CircuitoDAO.getInstance();
        cdao.clear();
        modelCircuito.adicionarCircuito(c);
        modelCircuito.adicionarCircuito(d);

        Circuito b = modelCircuito.getCircuito("portimao");

        for (Circuito circ : modelCircuito.getCircuitos())
            System.out.println(circ);
    }
}
