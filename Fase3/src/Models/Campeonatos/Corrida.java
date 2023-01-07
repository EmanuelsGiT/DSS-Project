package src.Models.Campeonatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;


import src.Models.Circuitos.Circuito;
import src.Models.Campeonatos.CarroSetup;

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
    private Map<CarroSetup, Integer> dnf;
    private List<CarroSetup> primeiroVolta;
    private List<CarroSetup> listaCarros;
    //private Set<Carro> resultados;

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
    /*
    public void simulaCorrida()
    {
        int voltas = this.circuito.getNVoltas();
        long t_aux, t_volta;
        ArrayList<CarroSetup> aux = new ArrayList<CarroSetup>();
        HashMap<CarroSetup,Integer> temp = new HashMap<CarroSetup,Integer>();
        for(CarroSetup c : this.listaCarros)
        {
            aux.add(c.clone());
        }
        for(int i=0; i<voltas; i++)
        {
            for(CarroSetup c : aux)
            {
                if(c.getDNF()==false) 
                {
                    if(c.DNF(i,voltas,0)==true) 
                    {
                        c.setDNF(true);
                        temp.put(c.clone(),i);
                    }
                    else
                    {
                       t_aux = c.getTempo(); 
                       if(c instanceof Hibrido)
                       {
                           Hibrido h = (Hibrido)c;
                           int motor = h.getPotenciaMotorEletrico();
                           t_volta = c.tempoProximaVolta(this.circuito, 0, i) - motor*10;
                       }
                       else
                            t_volta = c.tempoProximaVolta(this.circuito, 0, i);
                       c.setTempo(t_aux +t_volta); 
                       if(this.circuito.getRecord().getTempo() > t_volta)
                       {
                           if(i<(this.circuito.getNVoltas()/2))
                           {
                                Record r = new Record(t_volta,c.getEquipa().getPiloto1(),c.clone());
                                this.circuito.setRecord(r);
                           }
                           else
                           {
                               Record r = new Record(t_volta,c.getEquipa().getPiloto2(),c.clone());
                               this.circuito.setRecord(r);
                           }
                       }
                    }
                }
            }
            this.primeiroVolta(i,aux);
        }
        for(CarroSetup c : aux)
        {
            if(c.getDNF()==false)
            {
                    this.resultados.add(c);
            }
        }
        this.dnf = temp;
    }*/
    
    private void primeiroVolta(int volta, List<CarroSetup> l)
    {
       //Collections.sort(l);
       Iterator<CarroSetup> it = l.iterator();
       boolean f = false;
       CarroSetup c = null;
       while(it.hasNext() && f==false)
       {
           c = it.next();
           if(c.getDNF()==false)
                f=true;
       }
       if(c!=null)
            this.primeiroVolta.add(volta,c.clone());
    }

    public Map<CarroSetup,Integer> getDNF()
   {
       HashMap<CarroSetup,Integer> aux = new HashMap<CarroSetup,Integer>();
       for(CarroSetup c : this.dnf.keySet())
       {
           aux.put(c.clone(), this.dnf.get(c));
        }
        return aux;
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
