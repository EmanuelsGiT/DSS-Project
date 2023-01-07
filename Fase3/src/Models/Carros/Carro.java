package src.Models.Carros;

public abstract class Carro {
    
    /**
     * Variáveis de instância
     */
    private String marca; // marca do carro
    private String modelo; // modelo do carro
    private int cilindrada; // cilindrada do carro
    private int potencia; // potencia do carro
    private double fiabilidade; // fiabilidade do carro
    private double pac;

    /**
     * Construtor vazio/não parameterizado
     */
    public Carro() {
        this.marca = "";
        this.modelo = "";
        this.cilindrada = 0;
        this.potencia = 0;
        this.fiabilidade = 0;
        this.pac = 0;
    }

    /**
     * Construtor parameterizado
     * @param marca
     * @param modelo
     * @param cilindrada
     * @param potencia
     * @param fiabilidade
     */
    public Carro(String marca, String modelo, int cilindrada, int potencia, double fiabilidade, double pac) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.fiabilidade = fiabilidade;
        this.pac = pac;
    }

    /**
     * Construtor de cópia
     * @param carro
     */
    public Carro(Carro carro) {
        this.marca = carro.marca;
        this.modelo = carro.modelo;
        this.cilindrada = carro.cilindrada;
        this.potencia = carro.potencia;
        this.fiabilidade = carro.fiabilidade;
        this.pac= carro.pac;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public int getCilindrada() {
        return this.cilindrada;
    }

    public int getPotencia() {
        return this.potencia;
    }

    public double getFiabilidade() {
        return this.fiabilidade;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public void setFiabilidade(double fiabilidade) {
        this.fiabilidade = fiabilidade;
    }

    //public abstract boolean validaPotencia();

    public abstract Carro clone();

    public abstract boolean validaCilindrada();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMarca: ");sb.append(this.marca);
        sb.append("\nModelo: ");sb.append(this.modelo);
        sb.append("\nCilindrada: ");sb.append(this.cilindrada);
        sb.append("\nPotencia: ");sb.append(this.potencia);
        sb.append("\nFiabiliade: ");sb.append(this.fiabilidade);
        return sb.toString();
    }

    public double getPAC() {
        return this.pac;
    }
}