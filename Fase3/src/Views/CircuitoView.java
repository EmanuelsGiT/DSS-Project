package src.Views;

import src.Models.Circuitos.Circuito;

import java.util.ArrayList;
import java.util.Set;

public class CircuitoView {
    public void apresentarCircuitos(ArrayList<Circuito> circuitos) {
        System.out.println("Circuitos Disponiveis: ");
        for (int i = 0; i < circuitos.size(); i++) {
            System.out.println("-- Opcao [" + (i+1) + "] -- ");
            System.out.println(circuitos.get(i));
        }
    }

    public void apresentarGDUS() {
        System.out.println("[1] - Possivel.");
        System.out.println("[2] - Dificil.");
        System.out.println("[3] - Impossivel.");

    }
    
    public void sucessoAdicionarCircuito() {
       System.out.println("Circuito adicionado com sucesso!");
    }
    
    public void pedirGDU(String s, int numero) {
        System.out.println("GDU da " + s + " número " + numero+1 + ": ");
    }

    
    public void opcaoInvalida() {
        System.out.println("Opção inválida!");
    }
        
}
