package src.Models.Campeonatos;

import java.util.HashMap;
import java.util.Map;

import DAOs.CampeonatoDAO;
import src.Models.Campeonatos.CarroSetup;
import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Carros.Carro;
import src.Models.Circuitos.Circuito;
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

     /* 
     private Campeonato getCampeonato(String nomeCampeonato) {
          Campeonato c = CampeonatoDAO.getInstance().get(nomeCampeonato);
          if (c == null);
               //exception
          
          return c;
     }
     */

     @Override
     public void adicionarCampeonato(Campeonato campeonato) {
          this.campeonatos.put(campeonato.getNome(),campeonato);
     }

     @Override
     // campeonatoNaoExistenteException
     public void removerCampeonato(String nomeCampeonato) {
          this.campeonatos.remove(nomeCampeonato);
     }

     @Override
     public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          c.criaRegisto(nomeJogador, piloto, carro);
     }
     
     @Override
     public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac) {
          Campeonato camp = this.campeonatos.get(nomeCampeonato);
          if (camp != null) {
               Registo registo = camp.getRegistos().get(nomeJogador);
               if (registo != null) {
                    CarroSetup carro = registo.getCarroSetup();
                    carro.setModoMotor(motor);
                    carro.setPneus(pneus);
                    carro.setPac(pac);
                    registo.incrementaNumAfinacoes();
               }
          }

     }

     @Override
     public boolean validarAfinacao(String nomeJogador, String nomeCampeonato) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          return c.validarAfinacao(nomeJogador);
     }
 
     @Override
     // alterar return type
     public Map<Registo,Integer> getResultados(String nomeCampeonato) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          return c.calculaClassificacaoFinal();
     }
 
     @Override
     public void prepararCorrida(String nomeCampeonato, int corrida, String nomeJogador) {
     
          Corrida r = this.campeonatos.get(nomeCampeonato).getCorridas().get(corrida);
          if (r != null) {
               r.colocarPronto(nomeJogador);
          }
          boolean tdsProntos = r.todosJogadoresProntos();
          if(tdsProntos) {
               r.simularCorrida();
          }

     }
 
     @Override
     public Map<Registo,Integer> getResultadosCorrida(String nomeCampeonato, int corrida) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          return c.getResultadosCorrida(corrida);

     }

     @Override
     public void criarCorrida(String nomeCampeonato, Corrida corrida) {
          Campeonato c = this.campeonatos.get(nomeCampeonato);
          c.criarCorrida(corrida);
     }
}
