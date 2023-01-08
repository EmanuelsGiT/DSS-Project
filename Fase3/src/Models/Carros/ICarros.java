package src.Models.Carros;

import java.util.Collection;

public interface ICarros {
    public void adicionarCarro(Carro carro);
    public boolean existeCarro(String modelo);
    public Collection<Carro> getCarros();
}
