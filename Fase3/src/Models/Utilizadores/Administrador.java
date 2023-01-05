package src.Models.Utilizadores;

public class Administrador extends Utilizador{
    
    /**
     * Construtor vazio/não parameterizado
     */
    public Administrador() {
        super();
    }   

    /**
     * Construtor parameterizado
     * @param nome
     * @param pass
     */
    public Administrador(String nome, String pass) {
        super(nome, pass);
    }

    /**
     * Construtor de cópia
     * @param administrador
     */
    public Administrador(Administrador administrador) {
        super(administrador.getNome(),administrador.getPass());
    }


    public Administrador clone() {
        return new Administrador(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Administrador -");
        sb.append(super.getNome().toString());
        sb.append("\nPassword: ");
        sb.append(super.getPass().toString());
        return sb.toString();
    }

}
