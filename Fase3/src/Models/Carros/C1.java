package src.Models.Carros;

public class C1 extends Carro {
    /**
     * Variáveis de instância
     */
    private int hibrido; // 0 -> Não híbrido; 1 -> Híbrido
    private int potenciaHibrido;

    /**
     * Construtor vazio/não parameterizado
     */ 
    public C1() {
        super();
        this.hibrido = 0;
        this.potenciaHibrido = 0;
    }
    
    /**
     * Construtor parameterizado
     * Não recebe cilindrada visto que esta é sempre 6000cm3
     * Não recebe fiabilidade visto que esta é sempre 95%
     * @param marca
     * @param modelo
     * @param potencia
     */
    public C1(String marca, String modelo, int potencia, int hibrido, int potenciaHibrido, double pac) {
        super(marca, modelo, 6000, (potencia + (hibrido*potenciaHibrido)), 0.95, pac);
        this.hibrido = hibrido;
        this.potenciaHibrido = potenciaHibrido;
    }
    
    /**
     * Construtor de cópia
     * @param c1
     */
    public C1(C1 c1) {
        super(c1);
        this.hibrido = c1.hibrido;
        this.potenciaHibrido = c1.potenciaHibrido;
    }

    public int getHibrido() {
        return hibrido;
    }

    public int getPotenciaHibrido() {
        return potenciaHibrido;
    }

    public Carro clone() {
        return new C1(this);
    }

    public boolean validaCilindrada() {
        return (this.getCilindrada() == 6000);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - C1 -");
        sb.append(super.toString());
        sb.append("\nHíbrido: ");sb.append(this.hibrido);
        sb.append("\nPotência híbrido: ");sb.append(this.potenciaHibrido);
        return sb.toString();
    }

}