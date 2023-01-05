package src.Controllers;

import UI.Menu;
import src.Models.Utilizadores.Administrador;
import src.Models.Utilizadores.Anonimo;
import src.Models.Utilizadores.Utilizador;
import src.Models.Utilizadores.UtilizadorFacade;
import src.Views.UtilizadorView;

import java.util.Scanner;

public class Controller {

    // Model
    private UtilizadorFacade modelUtilizador;
    // View
    private UtilizadorView viewUtilizador;
    // Scanner
    private static final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.modelUtilizador = new UtilizadorFacade();
        this.viewUtilizador = new UtilizadorView();
    }

    public void run() {
        Menu menuAutenticacao = new Menu("Autenticacao", new String[]{"Sign in.", "Sign up."});
        menuAutenticacao.setHandler(1, this::SignIn);
        menuAutenticacao.setHandler(2, this::SignUp);
        menuAutenticacao.run();
    }

    private void SignUp() {
        Menu menuTipoRegisto = new Menu("Tipo de Registo", new String[] {"Adiministrador", "Jogador"});
        menuTipoRegisto.setHandler(1, () -> {
            String[] credenciais = Menu.lerCredenciais();
            Utilizador u = new Administrador(credenciais[0], credenciais[1]);
            this.modelUtilizador.registaUtilizador(u);
            this.viewUtilizador.registoSucesso(u);
        } );

        menuTipoRegisto.setHandler(2, () -> {
            String[] credenciais = Menu.lerCredenciais();
            Utilizador u = new Administrador(credenciais[0], credenciais[1]);
            this.modelUtilizador.registaUtilizador(u);
            this.viewUtilizador.registoSucesso(u);
        });

        menuTipoRegisto.run();
    }


    private void SignIn() {

        Menu menuTipoLogin = new Menu("Tipo de Login", new String[] {"Anonimo", "Jogador/Administrador"});
        menuTipoLogin.setHandler(1, () -> {
            String nome = Menu.lerNome();
            Utilizador u = new Anonimo(nome);
            this.modelUtilizador.registaUtilizador(u);
            this.viewUtilizador.autenticarSucesso(u);
            this.menuPrincipal();
        });
        menuTipoLogin.setHandler(2, () -> {
            String[] credenciais = Menu.lerCredenciais();
            if (this.modelUtilizador.autenticaUtilizador(credenciais[0], credenciais[1])) {
                System.out.println("Autenticado com Sucesso"); // Ir buscar o jogador!

            }
            this.menuPrincipal();
        });

        menuTipoLogin.run();
    }

    public void menuPrincipal() {
        Menu menuInicial = new Menu("Menu Principal", new String[] {"quais", "sao", "as", "opcoes","velhinhos"});

        menuInicial.run();
    }
}
