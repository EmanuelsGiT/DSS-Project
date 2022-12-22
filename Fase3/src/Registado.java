public class Registado extends Jogador {
    
    /**
     * Variáveis de instância
     */
    private String pass;
    private int pontuacaoTotal;

    /**
     * Construtor vazio/não parameterizado
     */
    public Registado() {
        super();
        this.pass = "";
        this.pontuacaoTotal = 0;
    }

    /**
     * Construtor parameterizado
     * @param nome
     * @param pass
     * @param pontuacaoTotal
     */
    public Registado(String nome, String pass, int pontuacaoTotal) {
        super(nome);
        this.pass = pass;
        this.pontuacaoTotal = pontuacaoTotal;
    }

    /**
     * Construtor de cópia
     * @param registado
     */
    public Registado(Registado registado) {
        super(registado.getNome());
        this.pass = registado.pass;
        this.pontuacaoTotal = registado.pontuacaoTotal;
    }

    public String getPass(String pass) {
        return this.pass;
    }

    public int getPontuacaoTotal(int pontuacaoTotal) {
        return this.pontuacaoTotal;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public Registado clone() {
        return new Registado(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Registado -");
        sb.append(super.toString());
        sb.append("\nPassword: ");sb.append(this.pass);
        sb.append("\nPontuação Total:");sb.append(this.pontuacaoTotal);
        return sb.toString();
    }
    
}
