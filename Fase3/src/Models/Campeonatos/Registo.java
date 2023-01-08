package src.Models.Campeonatos;

import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Pilotos.Piloto;
import src.Models.Utilizadores.Jogador;

public class Registo {
    
    /**
     * Variáveis de instância
     */
    private Integer id;
    private int numAfinacoes;
    private Piloto piloto;
    private Jogador jogador;
    private CarroSetup carroSetup;

    public Registo() {
        this.id = null;
        this.numAfinacoes = 0;
        this.piloto = new Piloto();
        this.jogador = null;
        this.carroSetup = null;
    }

    public Registo(int numAfinacoes, Piloto piloto, Jogador jogador, CarroSetup carroSetup) {
        this.id = null;
        this.numAfinacoes = numAfinacoes;
        this.piloto = piloto.clone();
        this.jogador = jogador.clone();
        this.carroSetup = carroSetup.clone();
    }
    public Registo(int id, int numAfinacoes, Piloto piloto, Jogador jogador, CarroSetup carroSetup) {
        this.id = id;
        this.numAfinacoes = numAfinacoes;
        this.piloto = piloto.clone();
        this.jogador = jogador.clone();
        this.carroSetup = carroSetup.clone();
    }
    public Registo(Registo registo) {
        this.id = registo.getId();
        this.numAfinacoes = registo.numAfinacoes;
        this.piloto = registo.getPiloto();
        this.jogador = registo.getJogador();
        this.carroSetup = registo.getCarroSetup();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumAfinacoes() {
        return this.numAfinacoes;
    }

    public Piloto getPiloto() {
        return this.piloto.clone();
    }

    public Jogador getJogador() {
        return this.jogador.clone();
    }

    public CarroSetup getCarroSetup() {
        return this.carroSetup;
    }

    public void setNumAfinacoes(int numAfinacoes) {
        this.numAfinacoes = numAfinacoes;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto.clone();
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador.clone();
    }

    public void setCarroSetup(CarroSetup carroSetup) {
        this.carroSetup = carroSetup.clone();
    }

    public Registo clone() {
        return new Registo(this);
    }

    public void incrementaNumAfinacoes() {
        this.numAfinacoes += 1;
    }
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Registo -");
        sb.append("\nNúmero de afinações: ");sb.append(this.numAfinacoes);
        sb.append("\nPiloto: ");sb.append(this.getPiloto());
        sb.append("\nJogador: ");sb.append(this.getJogador());
        sb.append("\nSetup do Carro: ");sb.append(this.getCarroSetup());
        return sb.toString();
    }





}
