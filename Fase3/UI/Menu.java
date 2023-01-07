package UI;

import src.Models.Circuitos.Circuito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.reflect.Type;

public class Menu {



    public interface Handler {
        void execute() throws Exception;
    }

    public interface PreCondition {
        boolean validate();
    }

    // Varíável de classe para suportar leitura

    private static BufferedReader  is = new BufferedReader(new InputStreamReader(System.in));

    // Variáveis de instância

    private String titulo;                  // Titulo do menu (opcional)
    private List<String> opcoes;            // Lista de opções
    private List<PreCondition> disponivel;  // Lista de pré-condições
    private List<String> textoIndesponivel; // Lista de textos quando indesponivel
    private List<Handler> handlers;         // Lista de handlers

    // Construtor

    /**
     * Constructor vazio para objectos da classe Menu.
     *
     * Cria um menu vazio, ao qual se podem adicionar opções.
     */
    public Menu() {
        this.titulo = "Menu";
        this.opcoes = new ArrayList<>();
        this.disponivel = new ArrayList<>();
        this.textoIndesponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    /**
     * Constructor para objectos da classe Menu.
     *
     * Cria um menu de opções sem event handlers.
     * Utilização de listas é útil para definir menus dinâmicos.
     *
     * @param titulo O titulo do menu
     * @param opcoes Uma lista de Strings com as opções do menu.
     */
    public Menu(String titulo, List<String> opcoes) {
        this.titulo = titulo;
        this.opcoes = new ArrayList<>(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.textoIndesponivel = new ArrayList<>();
        this.opcoes.forEach(s-> {
            this.disponivel.add(()->true);
            this.handlers.add(()->System.out.println("\nATENÇÃO: Opção não implementada!"));
            this.textoIndesponivel.add(("Opção indesponivel!"));
        });
    }

    /**
     * Constructor para objectos da classe Menu.
     *
     * Cria um menu de opções sem event handlers.
     * Utilização de listas é útil para definir menus estáticos. P.e.:
     * Utilização de listas é útil para definir menus dinâmicos.
     *
     * @param opcoes Uma lista de Strings com as opções do menu.
     */
    public Menu(List<String> opcoes) { this("Menu", opcoes); }

    /**
     * Constructor para objectos da classe Menu.
     *
     * Cria um menu de opções sem event handlers.
     * Utilização de arrays é útil para definir menus estáticos. P.e.:
     *
     * new Menu(String[]{
     *     "Opção 1",
     *     "Opção 2",
     *     "Opção 3"
     * })
     *
     * @param titulo O titulo do menu
     * @param opcoes Um array de Strings com as opções do menu.
     */
    public Menu(String titulo, String[] opcoes) {
        this(titulo, Arrays.asList(opcoes));
    }

    /**
     * Constructor para objectos da classe Menu.
     *
     * Cria um menu de opções sem event handlers.
     * Utilização de arrays é útil para definir menus estáticos. P.e.:
     *
     * new Menu(String[]{
     *     "Opção 1",
     *     "Opção 2",
     *     "Opção 3"
     * })
     *
     * @param opcoes Um array de Strings com as opções do menu.
     */
    public Menu(String[] opcoes) {
        this(Arrays.asList(opcoes));
    }

    // Métodos de instância

    /**
     * Adicionar opções a um Menu.
     *
     * @param name A opção a apresentar.
     * @param p A pré-condição da opção.
     * @param h O event handler para a opção.
     */
    public void option(String name, PreCondition p, Handler h) {
        this.opcoes.add(name);
        this.disponivel.add(p);
        this.handlers.add(h);
    }

    /**
     * Correr o menu uma vez.
     */
    public void runOnce() throws Exception {
        int op;
        show();
        op = Menu.readOption(this.opcoes.size());
        // testar pré-condição
        if (op>0 && !this.disponivel.get(op-1).validate()) {
            System.out.println("Opção indisponível!");
        } else if (op>0) {
            // executar handler
            this.handlers.get(op-1).execute();
        }
    }

    /**
     * Correr o menu multiplas vezes.
     *
     * Termina com a opção 0 (zero).
     */
    public void run() throws Exception {
        int op;
        do {
            show();
            op = Menu.readOption(this.opcoes.size());
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
    }

    /**
     * Método que regista uma uma pré-condição numa opção do menu.
     *
     * @param i índice da opção (começa em 1)
     * @param b pré-condição a registar
     */
    public void setPreCondition(int i, PreCondition b) {
        this.disponivel.set(i-1,b);
    }

    /**
     * Método para registar um handler numa opção do menu.
     *
     * @param i indice da opção  (começa em 1)
     * @param h handlers a registar
     */
    public void setHandler(int i, Handler h) {
        this.handlers.set(i-1, h);
    }

    /**
     * Método para registar um texto indesponivel numa opção do menu.
     *
     * @param i indice da opção  (começa em 1)
     * @param t texto a registar
     */
    public void setTextoIndesponivel(int i, String t) {this.textoIndesponivel.set(i-1, t); }
    // Métodos auxiliares

    /** Apresentar o menu */
    private void show() {
        System.out.println("#---------------# " + this.titulo + " #---------------#");
        for (int i = 0; i < this.opcoes.size(); i++) {
            System.out.print("[" + (i+1) + "] - ");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i): this.textoIndesponivel.get(i));
        }
        System.out.println("[0] - Sair");
        System.out.println("#---------------# "+ this.titulo.replaceAll(".", "-") + " #---------------#");
    }

    /** Ler uma opção válida */
    public static int readOption(int nOpcoes) {
        int op;

        System.out.print("Opção: ");
        try {
            String line = is.readLine();
            op = Integer.parseInt(line);
        }
        catch (NumberFormatException | IOException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op < 0 || op > nOpcoes) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }

    public static String[] lerCredenciais() {
        return new String[] {lerNome(), lerPalavraPasse()};
    }

    public static String lerNome() {
        return lerLinha("Nome do utilizador:");
    }

    public static String lerPalavraPasse() {
        return lerLinha("Palavra-passe");
    }

    public static String lerLinha(String texto) {
        System.out.println(texto);
        String l;
        try {
            l = is.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static Double lerDouble(String texto) {
        double r;
        try {
            r = Double.parseDouble(lerLinha(texto));
        } catch (Exception e) {
            System.out.println("Numero invalido!");
            return Menu.lerDouble(texto);
        }
        return r;
    }

    public static int lerInt(String texto) {
        int r;
        try {
            r = Integer.parseInt(lerLinha(texto));
        } catch (Exception e) {
            System.out.println("Numero invalido!");
            return Menu.lerInt(texto);
        }
        return r;
    }

    public static int lerInt(String s, int min, int max) {
        int x = lerInt(s);
        return x >= min && x <= max ? x : lerInt(s, min, max);
    }
    public static double lerDouble(String s, double min, double max) {
        double x = lerDouble(s);
        return x >= min && x <= max ? x : lerDouble(s, min, max);
    }
    public static boolean confirmOption(String s) throws IOException {
        System.out.println(s);
        String r = is.readLine();
        if (r.equalsIgnoreCase("y"))
            return true;
        else if (r.equalsIgnoreCase("n"))
            return false;
        else
            return confirmOption(s);
    }
}