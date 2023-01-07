package src.Models.Circuitos;

import java.util.HashMap;
import java.util.Map;

import DAOs.CircuitoDAO;

public class CircuitoFacade implements ICircuitos {

    private Map<String,Circuito> circuitos;

    public CircuitoFacade(){
        this.circuitos = CircuitoDAO.getinstance();
    }

    private Circuito getCircuito(String nomeCircuito){
        Circuito c = circuitos.get(nomeCircuito);
        if (c==null);
        return c;
    }

    @Override
    public void adicionarCircuito(Circuito circuito) {
        circuitos.put(circuito);
    }

    @Override
    public void adicionarCircuito(String nome,double distancia, int nVoltas, int nChicanes, int nCurvas, int nRetas, ArrayList<Circuito.GDU> retasGDU, ArrayList<Circuito.GDU> curvasGDU) {
        adiconarCircuito(new Circuito(nome, distancia, nVoltas, nChicanes, nCurvas, nRetas, retasGDU, curvasGDU));
    }

    @Override
    public void removerCircuito(String nome) {
        circuitos.remove(nome);
    }

    @Override
    public boolean existeCircuitos() {
        return circuitos.isEmpty();
    }

    @Override
    public Map<String,Circuito> getCircuitos() {
        return null;
    }

    @Override
    public int calcularNRetas(int nChicanes, int nCurvas) {
        return nChicanes+nCurvas;
    }
}
