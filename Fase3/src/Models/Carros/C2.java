package src.Models.Carros;

public class C2 extends Carro {
    /**
     * Variáveis de instância
     */
    private int hibrido; // 0 -> Não híbrido; 1 -> Híbrido
    private int potenciaHibrido;

    /**
     * Construtor vazio/não parameterizado
     */ 
    public C2() {
        super();
        this.hibrido = 0;
        this.potenciaHibrido = 0;
    }
    
    /**
     * Construtor parameterizado
     * Não recebe fiabilidade visto que esta é sempre 80% + valor em função da cilindrada: quanto maior a cilindrada maior a fiabilidade
     * @param marca
     * @param modelo
     * @param cilindrada
     * @param potencia
     */
    public C2(String marca, String modelo, int cilindrada, int potencia, int hibrido, int potenciaHibrido, double pac) {
        super(marca, modelo, cilindrada, (potencia + (hibrido*potenciaHibrido)), 0.8, pac);
        this.hibrido = hibrido;
        this.potenciaHibrido = potenciaHibrido;
    }
    
    /**
     * Construtor de cópia
     * @param c2
     */
    public C2(C2 c2) {
        super(c2);
        this.hibrido = c2.hibrido;
        this.potenciaHibrido = c2.potenciaHibrido;
    }

    public int getPotenciaHibrido() {
        return potenciaHibrido;
    }

    public int getHibrido() {
        return hibrido;
    }

    public Carro clone() {
        return new C2(this);
    }

    public boolean validaCilindrada() {
        return (this.getCilindrada()>3000 && this.getCilindrada()<5000);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - C2 -");
        sb.append(super.toString());
        sb.append("\nHíbrido: ");sb.append(this.hibrido);
        sb.append("\nPotência híbrido: ");sb.append(this.potenciaHibrido);
        return sb.toString();
    }

}