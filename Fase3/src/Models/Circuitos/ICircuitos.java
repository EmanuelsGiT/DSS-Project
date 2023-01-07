package src.Models.Circuitos;

import java.util.ArrayList;
import java.util.Set;

import src.Models.Circuitos.Circuito.GDU;

public interface ICircuitos {
    
    public void adicionarCircuito(Circuito circuito);

    public void removerCircuito(String nome);

    public boolean existeCircuito(String nome);

    public boolean existeCircuitos();

    public int calcularNRetas(int nChicanes, int nCurvas);

    ArrayList<Circuito> getCircuitos();

    public Circuito getCircuito(String nome);
}
