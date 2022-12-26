import java.util.HashMap;

import Models.Campeonato;

public class ICampeonatos {
    
    /**
     * Variáveis de instância
     */
    private HashMap<String, Campeonato> campeonatos; // key: nome do campeonato; value: campeonato

    public ICampeonatos() {
        this.campeonatos = new HashMap<>();
    }

    public ICampeonatos(HashMap<String, Campeonato> campeonatos) {
        this.campeonatos = new HashMap<>(campeonatos);
    }

    public ICampeonatos(ICampeonatos iCampeonatos) {
        this.campeonatos = new HashMap<>(iCampeonatos.getCampeonatos());
    }

    public HashMap<String, Campeonato> getCampeonatos() {
        return new HashMap<>(this.campeonatos);
    }
    
    public void setCampeonatos(HashMap<String, Campeonato> campeonatos) {
        this.campeonatos = new HashMap<>(campeonatos);
    }

    

    
}
