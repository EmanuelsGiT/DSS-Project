package src.Views;

import src.Models.Utilizadores.Utilizador;

public class UtilizadorView {
    public void registoSucesso(Utilizador u) {
        System.out.println("Utilizador " + u.getNome() + " registado com sucesso!");
    }

    public void autenticarSucesso(Utilizador u) {
        System.out.println("Utilizador " + u.getNome() + " autenticado com sucesso!");
    }

}
