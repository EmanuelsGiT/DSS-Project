
public abstract class Jogador extends Utilizador {

    /**
     * Variáveis de instância
     */

    public Jogador() {
        super();
    } 

    public Jogador(String nome) {
        super(nome);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Jogador -");
        sb.append(super.toString());
        return sb.toString();
    }
    
}
