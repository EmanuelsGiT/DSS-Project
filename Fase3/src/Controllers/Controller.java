package src.Controllers;

import UI.Menu;

import src.DAOs.CampeonatoDAO;
import src.DAOs.UtilizadorDAO;
import src.Main;
import src.Models.Campeonatos.Campeonato;
import src.Models.Campeonatos.CampeonatoFacade;
import src.Models.Campeonatos.Corrida;
import src.Models.Campeonatos.ICampeonatos;
import src.Models.Carros.*;
import src.Models.Circuitos.Circuito;
import src.Models.Circuitos.CircuitoFacade;
import src.Models.Circuitos.ICircuitos;
import src.Models.Pilotos.IPilotos;
import src.Models.Pilotos.Piloto;
import src.Models.Pilotos.PilotoFacade;
import src.Models.Utilizadores.*;
import src.Views.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    // Model
    private final IUtilizadores modelUtilizador;
    private final ICircuitos modelCircuto;
    private final ICarros modelCarro;
    private final IPilotos modelPiloto;
    private final ICampeonatos modelCampeonato;
    
    // View
    private final UtilizadorView viewUtilizador;
    private final CircuitoView viewCircuito;
    private final PilotoView viewPiloto;
    private final CarroView viewCarro;
    private final CampeonatoView viewCampeonato;
    

    private String nomeUtilizador = null;
    // Scanner
    private static final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.modelUtilizador = new UtilizadorFacade();
        this.modelCircuto = new CircuitoFacade();
        this.modelCarro = new CarroFacade();
        this.modelPiloto = new PilotoFacade();
        this.modelCampeonato = new CampeonatoFacade();
        this.viewUtilizador = new UtilizadorView();
        this.viewCircuito = new CircuitoView();
        this.viewPiloto = new PilotoView();
        this.viewCarro = new CarroView();
        this.viewCampeonato = new CampeonatoView();
    }

    public void run() throws Exception {
        Menu menuAutenticacao = new Menu("Autenticacao", new String[]{"Sign in.", "Sign up."});
        menuAutenticacao.setHandler(1, this::SignIn);
        menuAutenticacao.setHandler(2, this::SignUp);
        menuAutenticacao.run();
    }

    private void SignUp() throws Exception {
        Menu menuTipoRegisto = new Menu("Tipo de Registo", new String[] {"Adiministrador", "Jogador"});
        menuTipoRegisto.setHandler(1, () -> {
            String[] credenciais = Menu.lerCredenciais();
            Utilizador u = new Administrador(credenciais[0], credenciais[1]);
            this.modelUtilizador.registaUtilizador(u);
            this.viewUtilizador.registoSucesso(u);
        });

        menuTipoRegisto.setHandler(2, () -> {
            String[] credenciais = Menu.lerCredenciais();
            Utilizador u = new Registado(credenciais[0], credenciais[1]);
            this.modelUtilizador.registaUtilizador(u);
            this.viewUtilizador.registoSucesso(u);
        });

        menuTipoRegisto.run();
    }


    private void SignIn() throws Exception {

        Menu menuTipoLogin = new Menu("Tipo de Login", new String[] {"Administrador", "Jogador", "Anonimo"});
        menuTipoLogin.setHandler(1, () -> {
            String[] credenciais = Menu.lerCredenciais();
            if (this.modelUtilizador.autenticaAdministrador(credenciais[0], credenciais[1])) {
                System.out.println("Autenticado com Sucesso");
                this.nomeUtilizador = credenciais[0];
                this.menuPrincipalAdmin();
            }
            else {
                System.out.println("Erro ao autenticar");
            }
        });
        menuTipoLogin.setHandler(2, () -> {
            String[] credenciais = Menu.lerCredenciais();
            if (this.modelUtilizador.autenticaJogador(credenciais[0], credenciais[1])) {
                System.out.println("Autenticado com Sucesso");
                this.nomeUtilizador = credenciais[0];
                this.menuPrincipalJogador();
            }
            else {
                System.out.println("Erro ao autenticar");
            }
        });

        menuTipoLogin.setHandler(3, () -> {
            String nome = Menu.lerNome();
            Utilizador u = new Anonimo(nome);
            if (!this.modelUtilizador.registadoExiste(nome)) {
                this.modelUtilizador.registaUtilizador(u);
                this.viewUtilizador.autenticarSucesso(u);
                this.menuPrincipalJogador();
            } else {
                this.viewUtilizador.utilizadorJaRegistado();
            }
        });

        menuTipoLogin.run();
    }

    public void menuPrincipalAdmin() throws Exception {
        Menu menuInicial = new Menu("Menu Principal", new String[] {"Adicionar Campeonato", "Adicionar Circuito", "Adicionar Carro", "Adicionar Piloto"});
        menuInicial.setHandler(1, this::adicionarCampeonato);
        menuInicial.setPreCondition(1, this.modelCircuto::existeCircuitos);
        menuInicial.setHandler(2, this::adicionarCircuito);
        menuInicial.setHandler(3, this::adicionarCarro);
        menuInicial.setHandler(4, this::adicionarPiloto);
        menuInicial.run();
    }

    private void adicionarPiloto() {
        String nome = Menu.lerLinha("Nome do piloto: ");
        double cts = Menu.lerDouble("Grau de Pericia CTS (Chuva vs Tempo Seco): ", 0, 1);
        double sva = Menu.lerDouble("Grau de SVA (Seguranca vs agressividade):", 0, 1);

        Piloto p = new Piloto(nome, sva, cts);
        this.modelPiloto.adicionarPiloto(p);
        System.out.println("Piloto adicionado com sucesso!");


    }

    public void menuPrincipalJogador() throws Exception {
        Menu menuInicial = new Menu("Menu Principal", new String[] {"Registar Campeonato", "Preparar Corrida", "Consultar Classificacao Campeonato", "Consultar Classificacao Corrida"});
        menuInicial.setHandler(1, this::registarNumCampeonato);
        menuInicial.run();

    }

    private void registarNumCampeonato() throws IOException {
        ArrayList<Campeonato> campeonatos = new ArrayList<>(this.modelCampeonato.getCampeonatos());

        int op;
        do {
            this.viewCampeonato.apresentarCampeonatos(campeonatos);
            op = Menu.readOption(campeonatos.size());
            if (op > 0 && op < campeonatos.size()+1) {
                Piloto piloto = this.escolherPiloto();
                Carro carro = this.escolherCarro();
                System.out.println("\n\n\n\n\n--- Opcoes Escolhidas ---");
                System.out.println(piloto);
                System.out.println(carro);
                this.modelCampeonato.registaJogador(nomeUtilizador, campeonatos.get(op-1).getNome(), piloto, carro);
                System.out.println("Jogador registado com sucesso!");
            }
        } while (op != 0);
    }

    private Carro escolherCarro() {
        ArrayList<Carro> carros = new ArrayList<>(this.modelCarro.getCarros());
        int op;
        do {
            this.viewCarro.apresentarCarros(carros);
            op = Menu.readOption(carros.size());
        } while (op < 1 || op > carros.size());
        return carros.get(op-1);
    }

    private Piloto escolherPiloto() {
        ArrayList<Piloto> pilotos = new ArrayList<>(this.modelPiloto.getPilotos());
        int op;
        do {
            this.viewPiloto.apresentarPilotos(pilotos);
            op = Menu.readOption(pilotos.size());
        } while (op < 1 || op > pilotos.size());
        return pilotos.get(op-1);
    }

    private void adicionarCarro() throws Exception {
        Menu menuAdicionarCarro = new Menu("Menu Adicionar Carro", new String[]{"Carro C1.", "Carro C2.", "Carro GT.", "Carro SC"});
        menuAdicionarCarro.setHandler(1, () -> adicionarCarroAux(true, C1.class.getName(), false));
        menuAdicionarCarro.setHandler(2, () -> adicionarCarroAux(true, C2.class.getName(), true));
        menuAdicionarCarro.setHandler(3, () -> adicionarCarroAux(true, GT.class.getName(), true));
        menuAdicionarCarro.setHandler(4, () -> adicionarCarroAux(false, SC.class.getName(), false));

        menuAdicionarCarro.run();
    }

    private void adicionarCarroAux(boolean possivelHibrido, String classeCarro, boolean possivelCilindarada) throws IOException {
        Carro carro = null;
        String marca = Menu.lerLinha("Marca do carro: ");
        String modelo = Menu.lerLinha("Modelo do carro: ");
        if (this.modelCarro.existeCarro(modelo)) {
            System.out.println("Ja existe um carro com este nome!");
            return;
        }
        int potencia = Menu.lerInt("Potencia do carro: ");

        int cilindrada = 0;
        if (possivelCilindarada) {
            if (classeCarro.equals(C2.class.getName()))
                cilindrada = Menu.lerInt("Cilindrada do carro: ", 3000, 5000);
            else if (classeCarro.equals(GT.class.getName()))
                cilindrada = Menu.lerInt("Cilindrada do carro: ", 2000, 4000);
        }

        double pac = Menu.lerDouble("Insira o PAC: ", 0.0, 1.0);
        if (possivelHibrido) {
            boolean eHibrido = Menu.confirmOption("E hibrido?[y/n]");
            int potenciaHibrido = 0;
            if (eHibrido) {
                potenciaHibrido = Menu.lerInt("Potencia do Hibrido: ");
            }
            int hibridoValue = eHibrido ? 1 : 0;

            if (classeCarro.equals(C1.class.getName())) {
                carro = new C1(marca, modelo, potencia, hibridoValue, potenciaHibrido, pac);
            } else if (classeCarro.equals(C2.class.getName())) {
                carro = new C2(marca, modelo, cilindrada, potencia, hibridoValue, potenciaHibrido, pac);
            } else if (classeCarro.equals(GT.class.getName())) {
                carro = new GT(marca, modelo, cilindrada, potencia, hibridoValue, potenciaHibrido, pac);
            }
        }
        else {
            if (classeCarro.equals(SC.class.getName())) {
                carro = new SC(marca, modelo, potencia, pac);
            }
        }
        if (carro != null) {
            this.modelCarro.adicionarCarro(carro);
            System.out.println("Carro adicionado com sucesso!");
        } else {
            System.out.println("ERRO");
        }
    }

    private void adicionarCircuito() {
        String nome = Menu.lerLinha("Nome do circuito: ");
        if (this.modelCircuto.existeCircuito(nome)) {
            System.out.println("Ja existe um circuito com este nome, por favor insira outro nome!");
            return;
        }
        Double distancia = Menu.lerDouble("Distancia do circuito: ");
        int nCurvas = Menu.lerInt("Numero de curvas: ");
        int nChicanes = Menu.lerInt("Numero de chicanes: ");
        int nRetas = this.modelCircuto.calcularNRetas(nCurvas, nChicanes);

        String[] t = new String[] {"curva", "reta"};
        Circuito.GDU[] curvasGDU = new Circuito.GDU[nCurvas];
        Circuito.GDU[] retasGDU = new Circuito.GDU[nRetas];

        Circuito.GDU[][] tt = new Circuito.GDU[][] {curvasGDU, retasGDU};

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < tt[j].length; i++) {
                System.out.println("GDU da " + t[j] + " numero " + (i+1) + ": ");
                int op;
                do {
                    this.viewCircuito.apresentarGDUS();
                    op = Menu.readOption(3);
                    if (op == 1) {
                        tt[j][i] = Circuito.GDU.POSSIVEL;
                    } else if (op == 2) {
                        tt[j][i] = Circuito.GDU.DIFICIL;
                    } else if (op == 3) {
                        tt[j][i] = Circuito.GDU.IMPOSSIVEL;
                    } else {
                        System.out.println("Opcao invalida!");
                    }
                } while (op <= 0 || op > 3);
            }
        }
        int nVoltas = Menu.lerInt("Numero de voltas: ");

        Circuito circuito = new Circuito(nome, distancia, nVoltas, nChicanes, nCurvas, new ArrayList<>(Arrays.asList(tt[1])), new ArrayList<>(Arrays.asList(tt[0])));
        this.modelCircuto.adicionarCircuito(circuito);
        System.out.println("Circuito " + circuito.getNome() + " adicionado com sucesso!");
        System.out.println(circuito);

    }

    private void adicionarCampeonato() {
        CampeonatoDAO x = CampeonatoDAO.getInstance();
        x.clear();

        String nome = Menu.lerLinha("Nome do campeonato: ");
        ArrayList<Circuito> circuitos = this.modelCircuto.getCircuitos();
        ArrayList<Circuito> circuitosEscolhidos = new ArrayList<>();
        int op;
        do {
            this.viewCircuito.apresentarCircuitos(circuitos);
            op = Menu.readOption(circuitos.size());
            if (op > 0 && op < circuitos.size()) {
                circuitosEscolhidos.add(circuitos.get(op-1));
                circuitos.remove(op-1);
            }
        } while (op != 0);

        Campeonato c = new Campeonato(nome, circuitosEscolhidos.stream().map(Corrida::new).collect(Collectors.toCollection(ArrayList::new)));
        this.modelCampeonato.adicionarCampeonato(c);
    }

}
