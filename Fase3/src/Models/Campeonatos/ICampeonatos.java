package src.Models.Campeonatos;

import java.util.Collection;
import java.util.Map;

import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Carros.Carro;
import src.Models.Pilotos.Piloto;

public interface ICampeonatos {
    
    public void adicionarCampeonato(Campeonato campeonato); // done

    Collection<Campeonato> getCampeonatos();
    public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro); // done (ver exceptions)

    /**
    // Não deveria receber uma String com o nome do campeonato
    public void removerCampeonato(String nomeCampeonato); //done

    // n deveriam ser o piloto e o carro keys?

    public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac); // not done ver depois (ver CarSetupDAO)

    public boolean validarAfinacao(String nomeJogador, String nomeCampeonato); //done

    public Map<Registo,Integer> getResultados(String nomeCampeonato); // done

    public void prepararCorrida(String nomeCampeonato, int corrida, String nomeJogador); // ???????

    public Map<Registo,Integer> getResultadosCorrida(String nomeCampeonato, int corrida); // done
**/
     }
