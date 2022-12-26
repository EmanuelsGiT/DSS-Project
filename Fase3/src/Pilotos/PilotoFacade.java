package Pilotos;

import java.util.Map;

import DAOs.PilotoDAO;

public class PilotoFacade implements IPilotos {
    
    /**
     * Variáveis de instância
     */
    private Map<String, Piloto> pilotos;

    public PilotoFacade() {
        this.pilotos = PilotoDAO.getInstance();
    }

    public void adicionarPiloto(Piloto piloto) {
        this.pilotos.put(piloto.getNome(), piloto);
    }

    public void adicionarPiloto(String nome, double cts, double sva) {
        adicionarPiloto(new Piloto(nome, sva, cts));
    }

    public void removerPiloto(String nome) {
        this.pilotos.remove(nome);
    }

}
