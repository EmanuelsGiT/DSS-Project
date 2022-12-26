package Circuitos;
import java.util.ArrayList;

public class Circuito {
    
    public enum GDU {
        POSSIVEL,
        DIFICIL,
        IMPOSSIVEL
    }

    /**
     * Variáveis de instância
     */
    private double distancia;
    private int nVoltas;
    private int nChicanes;
    private int nCurvas;
    private int nRetas;
    private ArrayList<GDU> retasGDU;
    private ArrayList<GDU> curvasGDU;

    /**
     * Contrutor vazio/não parameterizado
     */
    public Circuito() {
        this.distancia = 0;
        this.nVoltas = 0;
        this.nChicanes = 0;
        this.nCurvas = 0;
        this.nRetas = 0;
        this.retasGDU = new ArrayList<>();
        this.curvasGDU = new ArrayList<>();
    }

    /**
     * Construtor parameterizado
     * Não recebe número de resto visto que este é calculado automaticamente a partir do número de chicanes e curvas
     * @param distancia
     * @param nVoltas
     * @param nChicanes
     * @param nCurvas
     * @param retasGDU
     * @param curvasGDU
     */
    public Circuito(double distancia, int nVoltas, int nChicanes, int nCurvas, ArrayList<GDU> retasGDU, ArrayList<GDU> curvasGDU) {
        this.distancia = distancia;
        this.nVoltas = nVoltas;
        this.nChicanes = nChicanes;
        this.nCurvas = nCurvas;
        this.nRetas = calcularNRetas(nChicanes, nCurvas);
        this.retasGDU = new ArrayList<>(retasGDU);
        this.curvasGDU = new ArrayList<>(curvasGDU);
    }

    /**
     * Construtor de cópia
     * @param circuito
     */
    public Circuito(Circuito circuito) {
        this.distancia = circuito.distancia;
        this.nVoltas = circuito.nVoltas;
        this.nChicanes = circuito.nChicanes;
        this.nCurvas = circuito.nCurvas;
        this.nRetas = calcularNRetas(circuito.nChicanes, circuito.nCurvas);
        this.retasGDU = new ArrayList<>(circuito.getRetasGDU());
        this.curvasGDU = new ArrayList<>(circuito.getCurvasGDU());
    }

    public double getDistancia() {
        return this.distancia;
    }

    public int getNVoltas() {
        return this.nVoltas;
    }

    public int getNChicanes() {
        return this.nChicanes;
    }

    public int getNCurvas() {
        return this.nCurvas;
    }

    public int getNRetas() {
        return calcularNRetas(this.nChicanes, this.nCurvas);
    }

    public ArrayList<GDU> getRetasGDU() {
        return new ArrayList<>(this.retasGDU);
    }

    public ArrayList<GDU> getCurvasGDU() {
        return new ArrayList<>(this.curvasGDU);
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setNVoltas(int nVoltas) {
        this.nVoltas = nVoltas;
    }

    public void setNChicanes(int nChicanes) {
        this.nChicanes = nChicanes;
    }

    public void setNCurvas(int nCurvas) {
        this.nCurvas = nCurvas;
    }

    public void setNRetas(int nChicanes, int nCurvas) {
        this.nRetas = calcularNRetas(nChicanes, nCurvas);
    }

    public void setRetasGDU(ArrayList<GDU> retasGDU) {
        this.retasGDU = new ArrayList<>(retasGDU);
    }

    public void setCurvasGDU(ArrayList<GDU> curvasGDU) {
        this.curvasGDU = new ArrayList<>(curvasGDU);
    }

    public int calcularNRetas(int nChicanes, int nCurvas) {
        return nChicanes+nCurvas; // formula a toa
    }

    public Circuito clone() {
        return new Circuito(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n - Circuito -");
        sb.append("\nDistância: ");sb.append(this.distancia);
        sb.append("\nNúmero de voltas: ");sb.append(this.nVoltas);
        sb.append("\nNúmero de chicanes: ");sb.append(this.nChicanes);
        sb.append("\nNúmero de curvas: ");sb.append(this.nCurvas);
        sb.append("\nNúmero de retas: ");sb.append(this.nRetas);
        sb.append("\nGDU das retas:");sb.append(this.getRetasGDU());
        sb.append("\nGDU das curvas:");sb.append(this.getCurvasGDU());
        return sb.toString();
    }

}
