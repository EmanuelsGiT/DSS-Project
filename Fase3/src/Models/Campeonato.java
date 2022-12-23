package Models;

import Models.Corrida;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Campeonato {
    
    /**
     * Variáveis de instância
     */
    private String nome;
    private ArrayList<Corrida> corridas;
    private HashMap<String, Registo> registos; // key: nome do jogador; value: registo no campeonato

    public Campeonato() {
        this.nome = "";
        this.corridas = new ArrayList<>();
        this.registos = new HashMap<>();
    }

    public Campeonato(String nome, ArrayList<Corrida> corridas, HashMap<String, Registo> registos) {
        this.nome = nome;
        this.corridas = new ArrayList<>(corridas);
        this.registos = new HashMap<>(registos);
    }

    public Campeonato(Campeonato campeonato) {
        this.nome = campeonato.nome;
        this.corridas = campeonato.getCorridas();
        this.registos = campeonato.getRegistos();
    }

    public String getNome() {
        return this.nome;
    }

    public ArrayList<Corrida> getCorridas() {
        return new ArrayList<>(this.corridas);
    }

    public HashMap<String, Registo> getRegistos() {
        return new HashMap<>(this.registos);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCorridas(ArrayList<Corrida> corridas) {
        this.corridas = new ArrayList<>(corridas);
    }

    public void setRegistos(HashMap<String, Registo> registos) {
        this.registos = new HashMap<>(registos);
    }

    public Campeonato clone() {
        return new Campeonato(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Campeonato -");
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nCorridas: ");sb.append(this.getCorridas());
        sb.append("\nRegistos: ");sb.append(this.getRegistos());
        return sb.toString();
    }




}
