package src.Models.Carros;

import src.DAOs.CarroDAO;

import java.util.Collection;
import java.util.Map;

public class CarroFacade implements ICarros {

    private Map<String, Carro> carros;

    public CarroFacade() {
        this.carros = CarroDAO.getInstance();
    }
    public void adicionarCarro(Carro carro) {
        this.carros.put(carro.getModelo(), carro);
     }

    @Override
    public boolean existeCarro(String modelo) {
        return carros.containsKey(modelo);
    }

    @Override
    public Collection<Carro> getCarros() {
        return carros.values();
    }
}
