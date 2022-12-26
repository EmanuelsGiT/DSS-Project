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

    public void adicionarPiloto(String nome, double cts, double sva) {
        this.pilotos.put(nome, new Piloto(nome, sva, cts));
    }

    public void removerPiloto(String nome) {
        this.pilotos.remove(nome);
    }

}
