package Circuitos;

import java.util.ArrayList;
import Circuitos.Circuito.GDU;

public interface ICircuitos {
    
    public void adicionarCircuito(Circuito circuito);

    public void adicionarCircuito(double distancia, int nVoltas, int nChicanes, int nCurvas, int nRetas, ArrayList<GDU> retasGDU, ArrayList<GDU> curvasGDU);
    
    public void removerCircuito(String nome);
    
}
