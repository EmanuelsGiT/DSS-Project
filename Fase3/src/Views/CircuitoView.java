package src.Views;

import src.Models.Circuitos.Circuito;

import java.util.ArrayList;
import java.util.Set;

public class CircuitoView {
    public void apresentarCircuitos(ArrayList<Circuito> circuitos) {
        System.out.println("Circuitos Disponiveis: ");
        for (int i = 0; i < circuitos.size(); i++) {
            System.out.println("[" + i + "]: ");
            System.out.println(circuitos.get(i));
        }
    }
}
