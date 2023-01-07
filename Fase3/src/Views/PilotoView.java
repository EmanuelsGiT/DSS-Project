package src.Views;

import src.Models.Pilotos.Piloto;

import java.util.ArrayList;

public class PilotoView {
    public void apresentarPilotos(ArrayList<Piloto> pilotos) {
        System.out.println("Pilotos Disponiveis: ");
        for (int i = 0; i < pilotos.size(); i++) {
            System.out.println("-- Opcao [" + (i+1) + "] -- ");
            System.out.println(pilotos.get(i));
        }
    }
}
