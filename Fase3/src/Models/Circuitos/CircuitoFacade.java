package src.Models.Circuitos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import src.DAOs.CircuitoDAO;

public class CircuitoFacade implements ICircuitos {

    private Map<String,Circuito> circuitos;

    public CircuitoFacade(){
        this.circuitos = CircuitoDAO.getInstance();
    }

    public Circuito getCircuito(String nome){
        return circuitos.get(nome);
    }

    @Override
    public void adicionarCircuito(Circuito circuito) {
        circuitos.put(circuito.getNome(), circuito);
    }

    @Override
    public void removerCircuito(String nome) {
        circuitos.remove(nome);
    }

    @Override
    public boolean existeCircuito(String nome) {
        return circuitos.containsKey(nome);
    }

    @Override
    public boolean existeCircuitos() {
        return !circuitos.isEmpty();
    }

    @Override
    public int calcularNRetas(int nChicanes, int nCurvas) {
        return nChicanes+nCurvas;
    }

    @Override
    public ArrayList<Circuito> getCircuitos() {
        return new ArrayList<>(circuitos.values());
    }

}
