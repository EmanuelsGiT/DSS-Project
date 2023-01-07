package src.Models.Circuitos;

import java.util.ArrayList;

public class CircuitoFacade implements ICircuitos {

    @Override
    public void adicionarCircuito(Circuito circuito) {

    }

    @Override
    public void adicionarCircuito(double distancia, int nVoltas, int nChicanes, int nCurvas, int nRetas, ArrayList<Circuito.GDU> retasGDU, ArrayList<Circuito.GDU> curvasGDU) {

    }

    @Override
    public void removerCircuito(String nome) {

    }

    @Override
    public boolean existeCircuitos() {
        return false;
    }

    @Override
    public ArrayList<Circuito> getCircuitos() {
        return null;
    }

    @Override
    public int calcularNRetas(int nChicanes, int nCurvas) {
        return nChicanes+nCurvas;
    }
}
