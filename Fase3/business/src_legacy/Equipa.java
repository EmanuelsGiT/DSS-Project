
/**
 * Write a description of class Equipa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class Equipa implements Serializable
{
    //Variaveis de instancia
    private String nome;
    private Piloto p1;
    private Piloto p2;
    
    //Construtores
    public Equipa()
    {
        this.nome = "";
        this.p1 = new Piloto();
        this.p2 = new Piloto();
    }
    
    public Equipa(String nome, Piloto p1, Piloto p2)
    {
        this.nome = nome;
        this.p1 = p1.clone();
        this.p2 = p2.clone();
    }
    
    public Equipa(Equipa e)
    {
        this.nome = e.getNome();
        this.p1 = e.getPiloto1();
        this.p2 = e.getPiloto2();
    }
    
    //Gets e Sets
    public String getNome()
    {
        return this.nome;
    }
    
    public Piloto getPiloto1()
    {
        return this.p1.clone();
    }
    
    public Piloto getPiloto2()
    {
        return this.p2.clone();
    }
    
    //Metodos usuais
    public Equipa clone()
    {
        return new Equipa(this);
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome Equipa: ");sb.append(this.nome);
        sb.append("\nPiloto 1: ");sb.append(this.p1.toString());
        sb.append("\nPiloto 2: ");sb.append(this.p2.toString());
        return sb.toString();
    }
    
    public boolean equals(Object o)
    {
        if(this==o)
            return true;
            
        if((o == null) || (this.getClass() != o.getClass()))
        return false;
        
        Equipa e = (Equipa) o;
        return (this.nome.equals(e.getNome()) &&
                this.p1.equals(e.getPiloto1()) &&
                this.p2.equals(e.getPiloto2()));
    }
}
