package src.Controllers;

import UI.Menu;
import src.Models.Campeonatos.Campeonato;
import src.Models.Circuitos.Circuito;
import src.Models.Circuitos.CircuitoFacade;
import src.Models.Circuitos.ICircuitos;
import src.Models.Utilizadores.*;
import src.Views.CircuitoView;
import src.Views.UtilizadorView;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {

    // Model
    private IUtilizadores modelUtilizador;
    private ICircuitos modelCircuto;
    // View
    private UtilizadorView viewUtilizador;
    private CircuitoView viewCircuito;
    // Scanner
    private static final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.modelUtilizador = new UtilizadorFacade();
        this.viewUtilizador = new UtilizadorView();
        this.modelCircuto = new CircuitoFacade();
        this.viewCircuito = new CircuitoView();
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
            Utilizador u = new Registado(credenciais[0], credenciais[1]);
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
            this.menuPrincipalAdmin();
        });
        menuTipoLogin.setHandler(2, () -> {
            String[] credenciais = Menu.lerCredenciais();
            if (this.modelUtilizador.autenticaUtilizador(credenciais[0], credenciais[1])) {
                System.out.println("Autenticado com Sucesso"); // Ir buscar o jogador!

            }
            this.menuPrincipalJogador();
        });

        menuTipoLogin.run();
    }

    public void menuPrincipalAdmin() {
        Menu menuInicial = new Menu("Menu Principal", new String[] {"Adicionar Campeonato", "Adicionar Circuito", "Adicionar Carro", "opcoes","velhinhos"});
        menuInicial.setHandler(1, this::adicionarCampeonato);
        menuInicial.setPreCondition(1, () -> this.modelCircuto.existeCircuitos());
        menuInicial.setHandler(2, this::adicionarCircuito);
        menuInicial.setHandler(3, this::adicionarCarro);

        menuInicial.run();
    }

    public void menuPrincipalJogador() {


    }

    private void adicionarCarro() {
    }

    private void adicionarCircuito() {

    }

    private void adicionarCampeonato() {
        String nome = Menu.lerLinha("Nome do campeonato: ");
        ArrayList<Circuito> circuitos = this.modelCircuto.getCircuitos();
        ArrayList<Circuito> circuitosEscolhidos = new ArrayList<>();
        int op;
        do {
            this.viewCircuito.apresentarCircuitos(circuitos);
            op = Menu.readOption(circuitos.size());
            if (op > 0) {
                circuitosEscolhidos.add(circuitos.get(op-1));
                circuitos.remove(op-1);
            }
        } while (op != 0);

        Campeonato c = new Campeonato();
        // CORRIDAS ? CIRCUITOS ?
    }
}
