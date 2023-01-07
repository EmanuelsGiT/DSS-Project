package src.Models.Utilizadores;

import src.DAOs.*;

import java.util.Map;

public class UtilizadorFacade implements IUtilizadores {

    private Map<String, Utilizador> utilizadores;
    private Map<String, Jogador> jogadores;
    private Map<String, Administrador> administradores;
    private Map<String, Registado> registados;
    private Map<String, Anonimo> anonimos;

    public UtilizadorFacade() {
        this.utilizadores = UtilizadorDAO.getInstance();
        this.jogadores = JogadorDAO.getInstance();
        this.administradores = AdministradorDAO.getInstance();
        this.registados = RegistadoDAO.getInstance();
        this.anonimos = AnonimoDAO.getInstance();
    }

    @Override
    public void registaUtilizador(Utilizador utilizador) throws Exception {
        if (utilizadores.containsKey(utilizador.getNome())) {
            throw new Exception("Este utilizador ja se encontra registado!");
        } else {
            if (utilizador.getClass().isAssignableFrom(Registado.class)) {
                this.registados.put(utilizador.getNome(), (Registado) utilizador);
            } else if (utilizador.getClass().isAssignableFrom(Administrador.class)) {
                this.administradores.put(utilizador.getNome(), (Administrador) utilizador);
            } else if (utilizador.getClass().isAssignableFrom(Anonimo.class)) {
                this.anonimos.put(utilizador.getNome(), (Anonimo) utilizador);
            }
        }
    }

    @Override
    public boolean autenticaUtilizador(String nome, String pass) {
        return true;
    }

    @Override
    public boolean utilizadorExiste(String nome) {
        if (utilizadores.containsKey(nome)) {
            return true;
        }
        return false;
    }
}
