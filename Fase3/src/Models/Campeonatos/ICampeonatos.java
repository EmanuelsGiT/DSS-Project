package src.Models.Campeonatos;

import java.util.Map;

import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Carros.Carro;
import src.Models.Circuitos.Circuito;
import src.Models.Pilotos.Piloto;

public interface ICampeonatos {
    
    // Metodos para o admin adicionar/remover campeonato/corrida
    
    
    public void adicionarCampeonato(Campeonato campeonato); // done
    
    // Não deveria receber uma String com o nome do campeonato
    public void removerCampeonato(String nomeCampeonato); //done

    // n deveriam ser o piloto e o carro keys?
    public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro); // done (ver exceptions)

    public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac); // not done ver depois (ver CarSetupDAO)

    public boolean validarAfinacao(String nomeJogador, String nomeCampeonato); //done

    public Map<Registo,Integer> getResultados(String nomeCampeonato); // done

    public Map<Registo,Integer> getResultadosCorrida(String nomeCampeonato, int corrida); // done

    public void criarCorrida(String nomeCampeonato, Corrida corrida); // done ig

    public void prepararCorrida(String nomeCampeonato, int corrida, String nomeJogador); // falta o simularCorrida()


}
