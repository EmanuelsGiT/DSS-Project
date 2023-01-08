package src.Views;

import src.Models.Carros.Carro;
import src.Models.Pilotos.Piloto;

import java.util.ArrayList;

public class CarroView {
    public void apresentarCarros(ArrayList<Carro> carros) {
        System.out.println("Carros Disponiveis: ");
        for (int i = 0; i < carros.size(); i++) {
            System.out.print("-- Opcao [" + (i+1) + "] -- ");
            System.out.println(carros.get(i));
        }
    }
}
