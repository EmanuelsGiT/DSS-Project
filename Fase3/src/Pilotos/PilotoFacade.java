package Pilotos;

import java.util.HashMap;
import java.util.HashSet;

public class PilotoFacade implements IPilotos {
    
    /**
     * Variáveis de instância
     */
    private HashMap<String, Piloto> pilotos;

    public PilotoFacade() {
        this.pilotos = new HashMap<>();
    }

}
