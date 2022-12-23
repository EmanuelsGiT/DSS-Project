package Models;
public class SC extends Carro {
    /**
     * Variáveis de instância
     */

    /**
     * Construtor vazio/não parameterizado
     */ 
    public SC() {
        super();
    }
    
    /**
     * Construtor parameterizado
     * TODO
     * Não recebe cilindrada visto que esta é sempre 2500
     * FIABILIDADE POR CALCULAR
     * @param marca
     * @param modelo
     * @param cilindrada
     * @param potencia
     */
    public SC(String marca, String modelo, int potencia) {
        super(marca, modelo, 2500, potencia, (0.80 + (2500*0.001)));
    }
    
    /**
     * Construtor de cópia
     * @param sc
     */
    public SC(SC sc) {
        super(sc);
    }
    
    public SC clone() {
        return new SC(this);
    }
    
    public boolean validaCilindrada() {
        return (this.getCilindrada() == 2500);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - SC -");
        sb.append(super.toString());
        return sb.toString();
    }

}