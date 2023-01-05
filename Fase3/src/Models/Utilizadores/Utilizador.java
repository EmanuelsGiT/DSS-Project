package src.Models.Utilizadores;
public abstract class Utilizador {
    
    /**
     * Variáveis de instância
     */
    private String nome;
    private String pass;

    /**
     * Construtor vazio/não parameterizado
     */
    public Utilizador() {
        this.nome = "";
        this.pass = "";
    }

    /**
     * Construtor paramaterizado
     * @param nome
     */
    public Utilizador(String nome, String pass) {
        this.nome = nome;
        this.pass = pass;
    }

    /**
     * Construtor de cópia
     * @param utilizador
     */
    public Utilizador(Utilizador utilizador) {
        this.nome = utilizador.nome;
        this.pass = utilizador.pass;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ");sb.append(this.nome);
        return sb.toString();
    }

}
