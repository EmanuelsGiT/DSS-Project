package src.Models.Utilizadores;
public abstract class Utilizador {
    
    /**
     * Variáveis de instância
     */
    private String nome;

    /**
     * Construtor vazio/não parameterizado
     */
    public Utilizador() {
        this.nome = null;
    }

    /**
     * Construtor paramaterizado
     * @param nome
     */
    public Utilizador(String nome) {
        this.nome = nome;
    }

    /**
     * Construtor de cópia
     * @param utilizador
     */
    public Utilizador(Utilizador utilizador) {
        this.nome = utilizador.nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ");sb.append(this.nome);
        return sb.toString();
    }

}
