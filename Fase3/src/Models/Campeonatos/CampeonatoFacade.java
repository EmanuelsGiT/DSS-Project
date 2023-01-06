package src.Models.Campeonatos;

import java.util.HashMap;
import java.util.Map;

import src.Models.Campeonatos.CarroSetup;

public class CampeonatoFacade implements ICampeonatos {
    
     /**
     * Variáveis de instância
     */

     //substituir depois para os DAOS
     private Map<String,Campeonato> campeonatos;

     public CampeonatoFacade() {
          this.campeonatos = new HashMap<>();
     }

     private Campeonato getCampeonato(String nomeCampeonato) {
          Campeonato c = CampeonatoDAO
     }


     @Override
     public void adicionarCampeonato(Campeonato campeonato) {
          this.campeonatos.put(campeonato.getNome(), campeonato);
     }

     @Override
     // campeonatoNaoExistenteException
     public void removerCampeonato(String nomeCampeonato) {
          this.campeonatos.remove(nomeCampeonato);
     }

     @Override
     public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          c.regista(nomeJogador, piloto, carro);
     }
     
     @Override
     public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac) {

     }

     @Override
     public void validarAfinacao(String nomeJogador, String nomeCampeonato) {
          Campeonato c = this.campeonatos
     }
 
     @Override
     public void getResultados(String nomeCampeonato);
 
     @Override
     public void prepararCorrida(String nomeCampeonato);
 
     @Override
     public void getResultadosCorrida(String nomeCampeonato, int corrida);
}
