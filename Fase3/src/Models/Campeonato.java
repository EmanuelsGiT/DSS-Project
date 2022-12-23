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

    public boolean validaAfinacao(String nomeJogador) {
        Registo r = this.registos.get(nomeJogador);
        int numAfinacoes = r.getNumAfinacoes();
        
        return (numAfinacoes <= (2/3)*this.corridas.size());
    }

    public Campeonato clone() {
        return new Campeonato(this);
    }

    public void adicionarRegisto(Jogador jogador, Piloto piloto, CarroSetup carroSetup) {
        String nomeJogador = jogador.getNome();
        this.registos.put(nomeJogador, new Registo(0, piloto, jogador, carroSetup));
    }

    public HashMap<Registo, Integer> calculaClassificacaoFinal() {
        // TODO
        //iterar sobre todos os registos e calcular a pontuaçao total de cada jogador
        HashMap<Registo, Integer> classificacaoFinal = new HashMap<>();
        for (Registo r : this.registos.values()) {
            Jogador jog = r.getJogador(); // verificar se está registado
            classificacaoFinal.put(r, jog.getPontuacaoTotal()); // so existe este metodo no registado ainda n sei como fazer isto
        }
        return classificacaoFinal;
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
