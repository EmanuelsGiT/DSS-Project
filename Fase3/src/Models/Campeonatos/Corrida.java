package src.Models.Campeonatos;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

import src.Models.Circuitos.Circuito;

public class Corrida {
    
    public enum Clima {
        MOLHADO,
        SECO
    }

    /**
     * Variáveis de instância
     */
    private Integer id;
    private Circuito circuito;
    private Clima clima;
    private HashMap<String, Integer> resultados; // key: nome do jogador; value: resultado da corrida
    private HashMap<String, Boolean> jogadoresProntos;

    /**
     * Construtor vazio/não parameterizado
     */
    public Corrida() {
        this.id = null;
        this.circuito = new Circuito();
        this.clima = Clima.SECO;
        this.resultados = new HashMap<>();
        this.jogadoresProntos = new HashMap<>();
    }

    public Corrida(Circuito circuito) {
        this.id = null;
        this.circuito = circuito.clone();
        this.clima = Clima.SECO;
        this.resultados = new HashMap<>();
        this.jogadoresProntos = new HashMap<>();
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
        this.jogadoresProntos = new HashMap<>();
    }
    /**
     * Contrutor não parameterizado
     * @param id
     * @param circuito
     * @param clima
     * @param resultados
     */
    public Corrida(int id, Circuito circuito, Clima clima, HashMap<String, Integer> resultados) {
        this.id = id;
        this.circuito = circuito.clone();
        this.clima = clima;
        this.resultados = new HashMap<>(resultados);
        this.jogadoresProntos = new HashMap<>();

    }

    /**
     * Contrutor não parameterizado
     * @param id
     * @param circuito
     * @param clima
     * @param resultados
     */
    public Corrida(int id, Circuito circuito, Clima clima, HashMap<String, Integer> resultados, HashMap<String, Boolean> jogadoresProntos) {
        this.id = id;
        this.circuito = circuito.clone();
        this.clima = clima;
        this.resultados = new HashMap<>(resultados);
        this.jogadoresProntos = new HashMap<>(jogadoresProntos);

    }
    /**
     * Construtor de cópia
     * @param corrida
     */
    public Corrida(Corrida corrida) {
        this.id = corrida.id;
        this.circuito = corrida.getCircuito();
        this.clima = corrida.clima;
        this.resultados = corrida.getResultados();
        this.jogadoresProntos = corrida.getJogadoresProntos();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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
    public HashMap<String, Boolean> getJogadoresProntos() {
        return new HashMap<>(this.jogadoresProntos);
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

    public void colocarPronto(String nomeJogador) {
        this.jogadoresProntos.put(nomeJogador,true);
    }

    public boolean todosJogadoresProntos() {
        for(Boolean b : jogadoresProntos.values()) {
            if(!b)
                return false;
        }
        return true;
    }

    public void simularCorrida() {
        int voltas = this.circuito.getNVoltas();
        
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
