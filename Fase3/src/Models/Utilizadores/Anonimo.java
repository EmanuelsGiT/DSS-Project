package src.Models.Utilizadores;

public class Anonimo extends Jogador {
    
    /**
     * Variáveis de instância
     */

    public Anonimo() {
        super();
    }

    public Anonimo(String nome) {
        super(nome);
    }

    public Anonimo(Anonimo anonimo) {
        super(anonimo);
    }

    @Override
    public Jogador clone() {
        return new Anonimo(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Anonimo -");
        sb.append(super.toString());
        return sb.toString();
    }

    
}
