package src.Views;

import src.Models.Utilizadores.Utilizador;

public class UtilizadorView {
    public void registoSucesso(Utilizador u) {
        System.out.println("Utilizador " + u.getNome() + " registado com sucesso!");
    }

    public void autenticarSucesso(Utilizador u) {
        System.out.println("Utilizador " + u.getNome() + " autenticado com sucesso!");
    }
    
    public void utilizadorJaRegistado() {
       System.out.println("Este utilizador já se encontra registado!");
    }

    public void sucessoAutenticacao() {
        System.out.println("Autenticado com sucesso!");
    }

    public void insucessoAutenticacao() {
        System.out.println("Erro ao autenticar!");
    }

}
