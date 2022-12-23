package Models;

import Models.Carro;

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
    private double pac;
    private Pneus pneus;
    private ModoMotor modoMotor;
    private Carro carro;

    public CarroSetup() {
        this.pac = 0;
        this.pneus = Pneus.MACIO;
        this.modoMotor = ModoMotor.CONSERVADOR;
        this.carro = new C1();
    }

    public CarroSetup(double pac, Pneus pneus, ModoMotor modoMotor, C1 carro) {
        this.pac = pac;
        this.pneus = pneus;
        this.modoMotor = modoMotor;
        this.carro = new C1(carro); // como fazer esta shit
    }

    public CarroSetup(CarroSetup carroSetup) {
        this.pac = carroSetup.pac;
        this.pneus = carroSetup.pneus;
        this.modoMotor = carroSetup.modoMotor;
        this.carro = carroSetup.getCarro(); // IDK
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
        return this.carro; /// ISTO TA MAL MAS COM CALMA AMIGOS, ACHO Q SCLR TENHO Q FAZER UM ABSTRACT CLONE E O CRL
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

    public void setCarro(Carro carro) {
        this.carro = carro.clone(); // heheheheheheheh
    }

    public CarroSetup clone() {
        return new CarroSetup(this);
    }

    public boolean validaPac() {
        return (this.pac >= 0 && this.pac <=1 );
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