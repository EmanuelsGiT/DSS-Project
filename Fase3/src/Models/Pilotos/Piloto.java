package src.Models.Pilotos;

public class Piloto {
    
    /**
     * Variáveis de instância
     */
    private String nome;
    private double sva;
    private double cts;

    /**
     * Construtor vazio/não parameterizado
     */
    public Piloto() {
        this.nome = "";
        this.sva = 0;
        this.cts = 0;
    }

    /**
     * Construtor paramterizado
     * @param nome
     * @param sva
     * @param cts
     */
    public Piloto(String nome, double sva, double cts) {
        this.nome = nome;
        this.sva = sva;
        this.cts = cts;
    }

    /**
     * Construtor de cópia
     * @param piloto
     */
    public Piloto(Piloto piloto) {
        this.nome = piloto.nome;
        this.sva = piloto.sva;
        this.cts = piloto.cts;
    }

    public String getNome() {
        return this.nome;
    }

    public double getSVA() {
        return this.sva;
    }

    public double getCTS() {
        return this.cts;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSVA(double sva) {
        this.sva = sva;
    }

    public void setCTS(double cts) {
        this.cts = cts;
    }

    /**
     * Valida se os níveis de experiência SVA e CTS estão entre 0 e 1
     * @return
     */
    public boolean validaNivelExperiencia() {
        return ((this.sva>0 && this.sva<1) && (this.cts>0 && this.cts<1));
    }

    public Piloto clone() {
        return new Piloto(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Piloto -");
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nSVA: ");sb.append(this.sva);
        sb.append("\nCTS: ");sb.append(this.cts);
        return sb.toString();
    }
}
