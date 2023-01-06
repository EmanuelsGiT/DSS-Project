package src.Models.Campeonatos;

import src.Models.Utilizadores.Anonimo;
import src.Models.Utilizadores.Jogador;
import src.Models.Utilizadores.Registado;

import java.util.ArrayList;
import java.util.HashMap;

import DAOs.RegistadoDAO;
import DAOs.RegistoDAO;
import src.Models.Pilotos.Piloto;


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

    //vou fazer ja para cobrir os DAOS 
    // Exceptions --> 
    public void regista(String nomeJogador, Piloto piloto, Carro carro) {
        Jogador jog = RegistadoDAO.getInstance().get(nomeJogador);
        if (jog == null) {
            //exception jogador n existe
        }
        if (RegistoDAO.getInstance(this.nome).get(nomeJogador) != null) { 
            // exception jogador ja esta registado
        }
        RegistoDAO.getInstance(this.nome).put(jog,new Registo(0, piloto, jog, new CarroSetup(carro)));
            
    }
    
    
    public boolean validaAfinacao(String nomeJogador) {
        Registo r = this.registos.get(nomeJogador);
        int numAfinacoes = r.getNumAfinacoes();
        
        return (numAfinacoes <= (2/3)*this.corridas.size());
    }

    public Campeonato clone() {
        return new Campeonato(this);
    }

    // adiciona ao hashmap de registos um jogador registado
    public void adicionarRegistoRegistado(Registado registado, Piloto piloto, CarroSetup carroSetup) {
        String nomeJogador = registado.getNome();
        this.registos.put(nomeJogador, new Registo(0, piloto, registado, carroSetup));
    }

    // adiciona ao hashmap de registos um jogador anonimo
    public void adicionaRegistoAnonimo(Anonimo anonimo, Piloto piloto, CarroSetup carroSetup) {
        String nomeJogador = anonimo.getNome();
        this.registos.put(nomeJogador, new Registo(0, piloto, anonimo, carroSetup));
    }

    public HashMap<Registo, Integer> calculaClassificacaoFinal() {
        HashMap<Registo, Integer> classificacaoFinal = new HashMap<>();
        for (Registo r : this.registos.values()) { 
            Jogador jog = r.getJogador();
            if(jog instanceof Registado) {
                classificacaoFinal.put(r, ((Registado)jog).getPontuacaoTotal()); 
            }
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
