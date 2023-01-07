package src.Models.Campeonatos;

import src.Models.Campeonatos.Campeonato;
import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Pilotos.Piloto;

public interface ICampeonatos {
    
    public void adicionarCampeonato(Campeonato campeonato);
    
    // Não deveria receber uma String com o nome do campeonato
    public void removerCampeonato(String nomeCampeonato);

    // n deveriam ser o piloto e o carro keys?
    public void registaJogador(String nomeJogador, String nomeCampeonato, Piloto piloto, Carro carro);

    public void alteraAfinacao(String nomeCampeonato, String nomeJogador, Pneus pneus, ModoMotor motor, Float pac);

    public void validarAfinacao(String nomeJogador, String nomeCampeonato);

    public void getResultados(String nomeCampeonato);

    public void prepararCorrida(String nomeCampeonato);

    public void getResultadosCorrida(String nomeCampeonato, int corrida);
}
