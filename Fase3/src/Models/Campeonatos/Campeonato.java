package src.Models.Campeonatos;

import src.Models.Utilizadores.Anonimo;
import src.Models.Utilizadores.Jogador;
import src.Models.Utilizadores.Registado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import DAOs.RegistadoDAO;
import DAOs.RegistoDAO;
import src.DAOsExclude.CorridaDAO;
import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Carros.Carro;
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
        this.corridas = CorridaDAO.getInstance();
        this.registos = RegistoDAO.getInstance();
    }

    public Campeonato(String nome, ArrayList<Corrida> corridas) {
        this.nome = nome;
        this.corridas = new ArrayList<>(corridas);
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
        return this.corridas;
    }

    public HashMap<String, Registo> getRegistos() {
        return this.registos;
    }

    public Corrida getCorrida(int corrida) {
        return this.corridas.get(corrida);
    }
    /* 
    public ArrayList<Corrida> getCorridas() {
        if (corridas==null) {
            corridas = CorridaDAO.getInstance(nome)
                                 .values()
                                 .stream()
                                 .map(Corrida::clone)
                                 .collect(Collectors.toList());
        }
        return (ArrayList<Corrida>) corridas.stream().map(Corrida::clone).collect(Collectors.toList());
    }

        
    public HashMap<String, Registo> getRegistos() {
        if(registos == null) {
            registos=RegistoDAO.getInstance(nome)
                                   .entrySet()
                                   .stream()
                                   .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone()));
        }

        return (HashMap<String,Registo>)
               registos.entrySet()
                       .stream()
                       .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone()));
    }
    */

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

       
    // Exceptions --> 
    public void criaRegisto(String nomeJogador, Piloto piloto, Carro carro) {
        Jogador jog = JogadorDAO.getInstance().get(nomeJogador);
        if (jog == null) {
            //exception jogador n existe
        }
        if (RegistoDAO.getInstance(this.nome).get(nomeJogador) != null) { 
            // exception jogador ja esta registado
        }
        RegistoDAO.getInstance(this.nome).put(nomeJogador,new Registo(0, piloto, jog, new CarroSetup(carro)));
            
    }
    
    public boolean validarAfinacao(String nomeJogador) {
        Registo r = this.registos.get(nomeJogador);        
        return (r.getNumAfinacoes() <= ( (double) 2/3)*this.corridas.size());
    }


    public Map<Registo,Integer> calculaClassificacaoFinal(){
        Map<Registo,Integer> classificacoes = new HashMap<>();
        for (Registo r :this.registos.values())
            classificacoes.put(r,0);
        for (Corrida c: this.corridas){
            HashMap<String, Integer> ps = c.getResultados();
            for (Map.Entry<String,Integer> e : ps.entrySet()){
                    Registo reg = this.registos.get(e.getKey());
                    classificacoes.put(reg,classificacoes.get(reg)+e.getValue());
                }
        
        }
        return classificacoes;
    }

    public Map<Registo,Integer> getResultadosCorrida(int corrida) {
        Map<Registo,Integer> classificacoes = new HashMap<>();
        Map<String,Integer> results = this.corridas.get(corrida).getResultados();
        
        for (Registo r : this.registos.values()) {
            classificacoes.put(r,results.get(r.getJogador().getNome()));
        }
        return classificacoes;
    }

    /* 
    public void prepararCorrida(String nomeJogador, int corrida) {
        Corrida c = this.corridas.get(corrida);
        c.colocarPronto(nomeJogador);

    }
    */

    public void criarCorrida(Corrida corrida) {
        this.corridas.add(corrida);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Campeonato -");
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nCorridas: ");sb.append(this.corridas);
        sb.append("\nRegistos: ");sb.append(this.registos);
        return sb.toString();
    }




}
