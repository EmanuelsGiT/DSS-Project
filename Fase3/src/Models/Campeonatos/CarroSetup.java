package src.Models.Campeonatos;

import src.Models.Carros.Carro;


public class CarroSetup {
    
    public enum Pneus {
        MACIO,
        DURO,
        CHUVA
    }

    public enum ModoMotor {
        CONSERVADOR,
        NORMAL,
        AGRESSIVO
    }

    /**
     * Variáveis de instância
     */
    private Integer id;
    private double pac;
    private Pneus pneus;
    private ModoMotor modoMotor;
    private Carro carro;
    private boolean dnf;
    private long tempo;

    public CarroSetup(double pac, Pneus pneus, ModoMotor modoMotor, Carro carro) {
        this.id = null;
        this.pac = pac;
        this.pneus = pneus;
        this.modoMotor = modoMotor;
        this.carro = carro.clone(); 
    }

    public CarroSetup(int id, double pac, Pneus pneus, ModoMotor modoMotor, Carro carro) {
        this.id = id;
        this.pac = pac;
        this.pneus = pneus;
        this.modoMotor = modoMotor;
        this.carro = carro.clone();
    }
    
    public CarroSetup(Carro carro) {
        this.id = null;
        this.pac = 0;
        this.pneus = null;
        this.modoMotor = null;
        this.carro = carro;
    }

    public CarroSetup(CarroSetup carroSetup) {
        this.id = carroSetup.id;
        this.pac = carroSetup.pac;
        this.pneus = carroSetup.pneus;
        this.modoMotor = carroSetup.modoMotor;
        this.carro = carroSetup.getCarro();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPac() {
        return this.pac;
    }

    public Pneus getPneus() {
        return this.pneus;
    }

    public ModoMotor getModoMotor() {
        return this.modoMotor;
    }

    public Carro getCarro() {
        return this.carro.clone();
    }
    
    public void setPac(double pac) {
        this.pac = pac;
    }

    public void setPneus(Pneus pneus) {
        this.pneus = pneus;
    }

    public void setModoMotor(ModoMotor modoMotor) {
        this.modoMotor = modoMotor;
    }

    
    public Boolean getDNF() {
        return this.dnf; 
    }

    public void setDNF(Boolean dnf) {
        this.dnf = dnf;
    }

    
    public void setCarro(Carro carro) {
        this.carro = carro.clone(); 
    }


    public CarroSetup clone() {
        return new CarroSetup(this);
    }
    

    public boolean validaPac() {
        return (this.pac >= 0 && this.pac <=1 );
    }

    
    public boolean DNF(int volta,int totalvoltas,int clima){
        return true;
    }

    public long getTempo()
    {
        return this.tempo;
    }

    public void setTempo(long tempo)
    {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Setup do Carro -");
        sb.append("\nPAC: ");sb.append(this.pac);
        sb.append("\nPneus: ");sb.append(this.pneus);
        sb.append("\nModo do motor: ");sb.append(this.modoMotor);
        sb.append("\nCarro: ");sb.append(this.getCarro());       
        return sb.toString();
    }
}