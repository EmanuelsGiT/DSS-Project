package src.Models.Circuitos;

import java.util.ArrayList;
import java.util.Set;

import src.Models.Circuitos.Circuito.GDU;

public interface ICircuitos {
    
    public void adicionarCircuito(Circuito circuito);

    public void adicionarCircuito(double distancia, int nVoltas, int nChicanes, int nCurvas, int nRetas, ArrayList<GDU> retasGDU, ArrayList<GDU> curvasGDU);
    
    public void removerCircuito(String nome);

    public boolean existeCircuitos();

    ArrayList<Circuito> getCircuitos();
}
