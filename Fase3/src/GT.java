public class GT extends Carro {
    /**
     * Variáveis de instância
     */
    private int hibrido;
    private int potenciaHibrido;
    /**
     * Construtor vazio/não parameterizado
     */ 
    public GT() {
        super();
        this.hibrido = 0;
        this.potenciaHibrido = 0;
    }
    
    /**
     * Construtor parameterizado
     * TODO
     * FIABILIDADE POR CALCULAR
     * @param marca
     * @param modelo
     * @param cilindrada
     * @param potencia
     */
    public GT(String marca, String modelo, int cilindrada, int potencia, int hibrido, int potenciaHibrido) {
        super(marca, modelo, cilindrada, (potencia + (hibrido*potenciaHibrido)), (0.80 + (cilindrada*0.001)));
        this.hibrido = hibrido;
        this.potenciaHibrido = potenciaHibrido;
    }
    
    /**
     * Construtor de cópia
     * @param gt
     */
    public GT(GT gt) {
        super(gt);
        this.hibrido = gt.hibrido;
        this.potenciaHibrido = gt.potenciaHibrido;
    }
    
    public GT clone() {
        return new GT(this);
    }

    public boolean validaCilindrada() {
        return (this.getCilindrada()>2000 && this.getCilindrada()<4000);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - GT -");
        sb.append(super.toString());
        sb.append("\nHíbrido: ");sb.append(this.hibrido);
        sb.append("\nPotência híbrido: ");sb.append(this.potenciaHibrido);
        return sb.toString();
    }

}