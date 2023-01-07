package src.Models.Utilizadores;

public interface IUtilizadores {
    
    public void registaUtilizador(Utilizador utilizador) throws Exception;

    public boolean autenticaUtilizador(String nome, String pass);

    public boolean autenticaJogador(String nome, String pass);

    public boolean autenticaAdministrador(String nome, String pass);

    public boolean utilizadorExiste(String nome);
    public boolean registadoExiste(String nome);

}

// SD 2, IA 4, DSS 6, CC 11/12, CP 13