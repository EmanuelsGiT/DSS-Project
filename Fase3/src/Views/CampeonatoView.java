package src.Views;

import src.Models.Campeonatos.Campeonato;

import java.util.Collection;

public class CampeonatoView {

    public void apresentarCampeonatos(Collection<Campeonato> campeonatos) {
        System.out.println("------------ Campeonatos Disoniveis ------------");
        int i = 1;
        for (Campeonato c : campeonatos) {
            System.out.println("[" + i + "] - " + c.getNome() +  " - " + c.getCorridas().size() + " corrida(s)");
        }
        System.out.println("------------------------------------------------");
    }
}
