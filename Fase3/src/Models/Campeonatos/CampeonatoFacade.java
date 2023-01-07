package src.Models.Campeonatos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import src.DAOs.CampeonatoDAO;
import src.DAOs.JogadorDAO;
import src.Models.Campeonatos.CarroSetup;
import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Carros.Carro;
import src.Models.Pilotos.Piloto;

public class CampeonatoFacade implements ICampeonatos {

    /**
     * Variáveis de instância
     */

     //substituir depois para os DAOS
     private Map<String,Campeonato> campeonatos;

     public CampeonatoFacade() {
          this.campeonatos = CampeonatoDAO.getInstance();
     }

     private Campeonato getCampeonato(String nomeCampeonato) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          if (c == null);
               //exception
          
          return c;
     }


     @Override
     public void adicionarCampeonato(Campeonato campeonato) {
          this.campeonatos.put(campeonato.getNome(), campeonato);
     }

     public Collection<Campeonato> getCampeonatos() {
         return this.campeonatos.values();
     }

    @Override
    public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro) {
         Registo registo = new Registo(3, piloto, JogadorDAO.getInstance().get(nomeJogador), new CarroSetup(carro));
         CampeonatoDAO.getInstance().putRegisto(nomeCampeonato, registo);
     }
    /**
     @Override
     // campeonatoNaoExistenteException
     public void removerCampeonato(String nomeCampeonato) {
          CampeonatoDAO.getInstance().remove(nomeCampeonato);
     }


     @Override
     public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          c.criaRegisto(nomeJogador, piloto, carro);
     }
     
     @Override
     public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac) {
          Campeonato c = getCampeonato(nomeCampeonato);
          c.alteraAfinacao(nomeJogador,pneus,motor);
     }

     @Override
     public boolean validarAfinacao(String nomeJogador, String nomeCampeonato) {
          Campeonato c = getCampeonato(nomeCampeonato);
          return c.validarAfinacao(nomeJogador);
     }
 
     @Override
     // alterar return type
     public Map<Registo,Integer> getResultados(String nomeCampeonato) {
          Campeonato c = getCampeonato(nomeCampeonato);
          return c.calculaClassificacaoFinal();
     }
 
     @Override
     public void prepararCorrida(String nomeCampeonato, int corrida, String nomeJogador) {
          Campeonato c = getCampeonato(nomeCampeonato);
          c.prepararCorrida(nomeJogador, corrida);
     }
 
     @Override
     public Map<Registo,Integer> getResultadosCorrida(String nomeCampeonato, int corrida) {
          Campeonato c = getCampeonato(nomeCampeonato);
          return c.getResultadosCorrida(corrida);

     }
     **/
}
