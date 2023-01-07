package src.Models.Campeonatos;

import java.util.HashMap;

import src.Models.Circuitos.Circuito;

public class Corrida {
    
    public enum Clima {
        MOLHADO,
        SECO
    }

    /**
     * Variáveis de instância
     */
    private Circuito circuito;
    private Clima clima;
    private HashMap<String, Integer> resultados; // key: nome do jogador; value: resultado da corrida

    /**
     * Construtor vazio/não parameterizado
     */
    public Corrida() {
        this.circuito = new Circuito();
        this.clima = Clima.SECO;
        this.resultados = new HashMap<>();
    }

    public Corrida(Circuito circuito) {
        this.circuito = circuito.clone();
        this.clima = Clima.SECO;
        this.resultados = new HashMap<>();
    }

    /**
     * Contrutor não parameterizado
     * @param circuito
     * @param clima
     * @param resultados
     */
    public Corrida(Circuito circuito, Clima clima, HashMap<String, Integer> resultados) {
        this.circuito = circuito.clone();
        this.clima = clima;
        this.resultados = new HashMap<>(resultados);
    }

    /**
     * Construtor de cópia
     * @param corrida
     */
    public Corrida(Corrida corrida) {
        this.circuito = corrida.getCircuito();
        this.clima = corrida.clima;
        this.resultados = corrida.getResultados();
    }

    public Circuito getCircuito() {
        return this.circuito.clone();
    }

    public Clima getClima() {
        return this.clima;
    }

    public HashMap<String, Integer> getResultados() {
        return new HashMap<>(this.resultados);
    }

    public void setCircuito(Circuito circuito) {
        this.circuito = circuito.clone();
    }

    public void setClima(Clima clima) {
        this.clima = clima;
    }

    public void setResultados(HashMap<String, Integer> resultados) {
        this.resultados = new HashMap<>(resultados);
    }

    public Corrida clone() {
        return new Corrida(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Corrida -");
        sb.append("\nCircuito: ");sb.append(this.getCircuito());
        sb.append("\nClima: ");sb.append(this.clima);
        sb.append("\nResultados: ");sb.append(this.getResultados());
        return sb.toString();
    }




}
