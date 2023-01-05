package src.Models.Utilizadores;

public class UtilizadorFacade implements IUtilizadores {

    @Override
    public void registaUtilizador(Utilizador utilizador) {

    }

    @Override
    public void registaAdministrador(String nomeAdmin, String passAdmin) {

    }

    @Override
    public void registaJogador(String nome, String pass) {

    }

    @Override
    public void registaRegistado(String nome, String pass, int pontuacaoTotal) {

    }

    @Override
    public void registaAnonimo(String nome) {

    }

    @Override
    public boolean autenticaUtilizador(String nome, String pass) {
        return false;
    }

    @Override
    public boolean utilizadorExiste(String nome, String pass) {
        return false;
    }
}
