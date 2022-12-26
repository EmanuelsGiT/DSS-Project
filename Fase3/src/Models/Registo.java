package Models;

import Models.CarroSetup;
import Models.Jogador;
import Models.Piloto;

public class Registo {
    
    /**
     * Variáveis de instância
     */
    private int numAfinacoes;
    private Piloto piloto;
    private Jogador jogador;
    private CarroSetup carroSetup;

    public Registo() {
        this.numAfinacoes = 0;
        this.piloto = new Piloto();
        this.jogador = new Jogador(); // again abstrato...
        this.carroSetup = new CarroSetup();
    }

    public Registo(int numAfinacoes, Piloto piloto, Jogador jogador, CarroSetup carroSetup) {
        this.numAfinacoes = numAfinacoes;
        this.piloto = piloto.clone();
        this.jogador = jogador.clone();
        this.carroSetup = carroSetup.clone();
    }

    public Registo(Registo registo) {
        this.numAfinacoes = registo.numAfinacoes;
        this.piloto = registo.getPiloto();
        this.jogador = registo.getJogador();
        this.carroSetup = registo.getCarroSetup();
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
        return this.carroSetup.clone();
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