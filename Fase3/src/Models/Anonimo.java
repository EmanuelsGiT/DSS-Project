package Models;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Anonimo -");
        sb.append(super.toString());
        return sb.toString();
    }
}
