package src.Models.Utilizadores;

public class Administrador extends Utilizador{
    
    /**
     * Variáveis de instância
     */
    private String pass;

    /**
     * Construtor vazio/não parameterizado
     */
    public Administrador() {
        super();
        this.pass = "";
    }   

    /**
     * Construtor parameterizado
     * @param nome
     * @param pass
     */
    public Administrador(String nome, String pass) {
        super(nome);
        this.pass = pass;
    }

    /**
     * Construtor de cópia
     * @param administrador
     */
    public Administrador(Administrador administrador) {
        super(administrador.getNome());
        this.pass = administrador.pass;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Administrador clone() {
        return new Administrador(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Administrador -");
        sb.append(super.toString());
        sb.append("\nPassword: ");sb.append(this.pass);
        return sb.toString();
    }

}
