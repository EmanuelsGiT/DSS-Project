
/**
 * Write a description of class Aposta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Aposta implements Serializable
{
    private int valor;
    private List<Carro> carros;
    private int corrida; //nr da prova no array de corridas do campeonato
    private int ganho;
    
    private Aposta()
    {
        this.valor = 0;
        this.carros = null;
        this.corrida = -1;
        this.ganho = 0;
    }
    
    public Aposta(int v, List<Carro> ca, int co, int g)
    {
        this();
        this.valor = v;
        List<Carro> aux = new ArrayList<Carro>();
        for(Carro c : ca)
        {
            aux.add(c.clone());
        }
        this.carros = aux;
        this.corrida = co;
        this.ganho = g;
    }
    
    public Aposta(Aposta a)
    {
        this.valor = a.getValor();
        this.carros = a.getCarros();
        this.corrida = a.getCorrida();
        this.ganho = a.getGanho();
    }
    
    public int getValor()
    {
        return this.valor;
    }
    
    public List<Carro> getCarros()
    {
       List<Carro> aux = new ArrayList<Carro>(2);
       for(Carro c : this.carros)
       {
            aux.add(c.clone());
       }
       return aux;
    }
    
    public int getCorrida()
    {
        return this.corrida;
    }
    
    public int getGanho()
    {
        return this.ganho;
    }
    
    public void setGanho(int g)
    {
        this.ganho = g;
    }
    
    public Aposta clone()
    {
        return new Aposta(this);
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nValor:");sb.append(this.valor);
        sb.append("\nCarros: ");
        for(Carro c: this.carros)
        {
            sb.append(c.getMarca());sb.append(" ");
            sb.append(c.getModelo());sb.append(" ");
            sb.append(c.getEquipa().getNome());sb.append(" ");
            sb.append("\t");
        }
        sb.append("\nCorrida nº: ");sb.append(this.corrida+1);
        sb.append("\nGanho: ");sb.append(this.ganho);
        sb.append("\n--------------------");
        return sb.toString();
    }
}
