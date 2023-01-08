package src.Models.Pilotos;

import java.util.Collection;
import java.util.Map;

//import src.DAOsExclude.PilotoDAO;
import src.DAOs.PilotoDAO;
import src.Models.Pilotos.IPilotos;
import src.Models.Pilotos.Piloto;

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

    public Collection<Piloto> getPilotos() {
        return this.pilotos.values();
    }
}
