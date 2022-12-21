
/**
 * Write a description of class Campeonato here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;
import java.io.Serializable;

public class Campeonato implements Serializable
{
    private List<Corrida> corridas;
    private Map<String,Integer> classificacao;
    private Map<String,Integer> classificacaoH;
    private int prova; //incrementa a cada prova realizada (aponta para a prova a realizar)
    
    public Campeonato()
    {
        this.corridas = new ArrayList<Corrida>();
        this.classificacao = new HashMap<String,Integer>();
        this.classificacaoH = new HashMap<String,Integer>();
        this.prova = 0;
    }
    
    public Campeonato(List<Corrida> cor, Map<String,Integer> cla, Map<String,Integer> claH, int prova)
    {
        this();
        ArrayList<Corrida> aux = new ArrayList<Corrida>();
        for(Corrida co : cor)
        {
            aux.add(co.clone());
        }
        this.corridas = aux;
        HashMap<String,Integer> aux2 = new HashMap<String,Integer>();
        for(String c: cla.keySet())
        {
            aux2.put(c, cla.get(c));
        }
        HashMap<String,Integer> aux3 = new HashMap<String,Integer>();
        for(String c: claH.keySet())
        {
            aux3.put(c, claH.get(c));
        }
        this.classificacao = aux3;
        this.prova = prova;
    }
    
    public Campeonato(Campeonato c)
    {
        this.corridas = c.getCorridas();
        this.classificacao = c.getClassificacao();
        this.classificacaoH = c.getClassificacaoH();
        this.prova = c.getProva();
    }
    
    public List<Corrida> getCorridas()
    {
        ArrayList<Corrida> aux = new ArrayList<Corrida>();
        for(Corrida co : this.corridas)
        {
            aux.add(co.clone());
        }
        return aux;
    }
    
    public Map<String, Integer> getClassificacao()
    {
        HashMap<String,Integer> aux = new HashMap<String,Integer>();
        for(String c : this.classificacao.keySet())
        {
            aux.put(c, this.classificacao.get(c));
        }
        return aux;
    }
    
    public Map<String, Integer> getClassificacaoH()
    {
        HashMap<String,Integer> aux = new HashMap<String,Integer>();
        for(String c : this.classificacaoH.keySet())
        {
            aux.put(c, this.classificacaoH.get(c));
        }
        return aux;
    }
    
    public int getProva()
    {
        return this.prova;
    }
    //Metodos
    /**
     * Adicionar corrida ao campeonato
     */
    public void addCorrida(Corrida c)
    {
        this.corridas.add(c.clone());
    }
    
    /**
     * Simular proxima corrida
     */
    public String simularProximaCorrida()
    {
        //StringBuilder sb = new StringBuilder();
        String res;
        if(this.corridas.size() == this.prova)
        {
            //sb.append("\nNÃO HÁ CORRIDAS POR REALIZAR!!!");
            res = "\nNÃO HÁ CORRIDAS POR REALIZAR!!!";
        }
        else
        {
            this.corridas.get(this.prova).simulaCorrida();
            //sb.append(this.corridas.get(this.prova).printResultados());
            res = this.corridas.get(this.prova).printResultados();
            this.prova++;
        }
        
        //return sb.toString();
        return res;
    }
    
    /**
     * Obter corrida nr x
     */
    public Corrida getCorrida(int x)
    {
        return this.corridas.get(x-1).clone();
    }
    
    /**
     * Corridas agendadas por realizar
     */
    public String agendaCorridas()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAGENDA");
        for(int i=0;i<this.corridas.size();i++)
        {
            sb.append("\n");
            sb.append(i+1);sb.append(" - ");sb.append(this.corridas.get(i).getCircuito().getNome());
            if(i<this.prova)
                sb.append(" REALIZADA");
        }
        return sb.toString();
    }
    
    /**
     * Lista a classificacao atual
     */
    public String printClassificacao()
    {
        //chamo o ordena e faço print!!
        List<Map.Entry<String, Integer>> aux = this.ordenaClassificacao(this.classificacao);
        StringBuilder sb = new StringBuilder();
        sb.append("\nClassificacao Geral");
        sb.append("\n=========================");
        for(int i=0;i<aux.size();i++)
        {
            sb.append("\n");
            sb.append(i+1);sb.append("º: ");sb.append(aux.get(i));
            //sb.append("\t");sb.append(aux.get(i));
            //i++;
        }
        List<Map.Entry<String, Integer>> aux2 = this.ordenaClassificacao(this.classificacaoH);
        sb.append("\n\nClassificacao Geral Hibrido");
        sb.append("\n=========================");
        for(int i=0;i<aux2.size();i++)
        {
            sb.append("\n");
            sb.append(i+1);sb.append("º: ");sb.append(aux2.get(i));
            //sb.append("\t");sb.append(aux.get(i));
            //i++;
        }
        return sb.toString();
    }
    
    /**
     * Atualizar classificacao campeonato
     */
    public void atualizarClassificacao()
    {
            int i = this.prova-1;
            Set<Carro> aux = this.corridas.get(i).getResultados();
            int x=4, old_value;
            for(Carro c : aux)
            {    
                if(!(c instanceof Hibrido))
                {
                old_value = 0;
                String g = c.getMarca()+" "+c.getModelo() +" \t"+c.getEquipa().getNome()+" \t"+c.getClass().getName();
                if(this.classificacao.containsKey(g))
                {
                    old_value = this.classificacao.get(g);
                }
                if(x==4)
                {
                    this.classificacao.put(g, old_value+16);
                }
                if(x==3)
                {
                   this.classificacao.put(g, old_value+8);
                }
                if(x==2)
                {
                   this.classificacao.put(g, old_value+4);
                }
                if(x==1)
                {
                   this.classificacao.put(g, old_value+2);
                }
                if(x==0)
                {
                   this.classificacao.put(g, old_value+1);
                }
                if(x<0)
                {
                   this.classificacao.put(g, 0+old_value); 
                }
                x--;
                }
            }
            
            Map<Carro,Integer> aux2 = this.corridas.get(i).getDNF();
            for(Carro q : aux2.keySet())
            {
                if(!(q instanceof Hibrido))
                {
                    old_value = 0;
                    String a = q.getMarca()+" "+q.getModelo() +" \t"+q.getEquipa().getNome()+" \t"+q.getClass().getName();
                    if(this.classificacao.containsKey(a))
                        old_value = this.classificacao.get(a);
                    this.classificacao.put(a,0+old_value);
                }
            }
    }
    
    /**
     * Atualizar classificacao campeonato hibrido
     */
    public void atualizarClassificacaoHibrido()
    {
            int i = this.prova-1;
            Set<Carro> aux = this.corridas.get(i).getResultados();
            int x=4, old_value;
            for(Carro c : aux)
            { 
                if(c instanceof Hibrido)
                {
                old_value = 0;
                String g = c.getMarca()+" "+c.getModelo() +" \t"+c.getEquipa().getNome()+" \t"+c.getClass().getName();
                if(this.classificacaoH.containsKey(g))
                {
                    old_value = this.classificacaoH.get(g);
                }
                if(x==4)
                {
                    this.classificacaoH.put(g, old_value+16);
                }
                if(x==3)
                {
                   this.classificacaoH.put(g, old_value+8);
                }
                if(x==2)
                {
                   this.classificacaoH.put(g, old_value+4);
                }
                if(x==1)
                {
                   this.classificacaoH.put(g, old_value+2);
                }
                if(x==0)
                {
                   this.classificacaoH.put(g, old_value+1);
                }
                if(x<0)
                {
                   this.classificacaoH.put(g, 0+old_value); 
                }
                x--;
                }
            }
            
            Map<Carro,Integer> aux2 = this.corridas.get(i).getDNF();
            for(Carro q : aux2.keySet())
            {
                if(q instanceof Hibrido)
                {
                    old_value = 0;
                    String a = q.getMarca()+" "+q.getModelo() +" \t"+q.getEquipa().getNome()+" \t"+q.getClass().getName();
                    if(this.classificacaoH.containsKey(a))
                        old_value = this.classificacaoH.get(a);
                    this.classificacaoH.put(a,0+old_value);
                }
            }
    }
    
    private List<Map.Entry<String, Integer>> ordenaClassificacao(Map<String,Integer> classificacao)
    {
        List<Map.Entry<String, Integer>> ordenado = new ArrayList<Map.Entry<String, Integer>>(classificacao.entrySet());
        Collections.sort(ordenado, new Comparator<Map.Entry<String, Integer>>() 
        {
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) 
            {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        return ordenado;
    }
    
    /**
     * Info corrida x
     */
    public String resultadosCorrida(int x)
    {
        //StringBuilder sb = new StringBuilder();
        //if(this.prova < x)
        //{
          //  String s = ("\nA prova escolhida não existe ou ainda não foi realizada!");
            //return s;
        //}
        //else
        //{
        return this.getCorrida(x).printResultados();
        //}
        //return sb.toString();
    }
    
    /**
     * Verificar se corrida já foi realizada
     */
    public boolean corridaRealizada(int x)
    {
        return ((x-1) < this.prova);
    }
    
    /**
     * Lista Carros a participar em proxa nr x
     */
    public String listaParticipantes(int x)
    {
        StringBuilder sb = new StringBuilder();
        Corrida aux = this.corridas.get((x-1));
        sb.append(aux.listaCarrosParticipantes());
        return sb.toString();
    }
    
    /**
     * Lista Circuitos
     */
    public String listaCircuitos()
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.corridas.size();i++)
        {
            sb.append("\n");
            sb.append(i+1);sb.append("- ");sb.append(this.corridas.get(i).getCircuito().getNome());
        }
        return sb.toString();
    }
}
