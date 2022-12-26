package Utilizadores;

public interface IUtilizadores {
    
    public void registaUtilizador(Utilizador utilizador);

    public void registaAdministrador(String nomeAdmin, String passAdmin);

    public void registaJogador(Jogador jogador);

    public void registaRegistado(String nome, String pass, int pontuacaoTotal);

    public void registaAnonimo(String nome);

    public boolean autenticaUtilizador(String nome, String pass);

    public boolean utilizadorExiste(String nome, String pass);

}

// SD 2, IA 4, DSS 6, CC 11/12, CP 13