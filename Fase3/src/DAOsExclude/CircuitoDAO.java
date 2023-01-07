package src.DAOsExclude;

import src.Models.Circuitos.Circuito;

import java.sql.*;
import java.util.*;

public class CircuitoDAO implements Map<String , Circuito> {

    private static CircuitoDAO singleton = null;

    public static CircuitoDAO getInstance() {

        if (CircuitoDAO.singleton == null) CircuitoDAO.singleton = new CircuitoDAO();
        return CircuitoDAO.singleton;
        
    }

    /*private Map<String,Circuito> Circuito=Map.of(
            "Monza",new Circuito("Monza",5.793F,53, new ArrayList<Circuitoection>(Arrays.asList(
                    new CircuitSection(Circuitoection.CircuitoectionType.STRAIGHT, 0.75F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CHICANE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.2F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.5F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CHICANE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.05F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.6F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.8F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F)
            ))),
            "SPA",new Circuito("SPA",7.004F,44,new ArrayList<Circuitoection>(Arrays.asList(
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.5F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.2F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.2F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.5F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.05F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.6F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.8F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CURVE, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.STRAIGHT, 0.15F),
                    new Circuitoection(Circuitoection.CircuitoectionType.CHICANE,0.2F)
            )))
    );*/

    @Override
    public int size() {
        return Circuito.size();
    }

    @Override
    public boolean isEmpty() {
        return Circuito.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return Circuito.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return Circuito.containsValue(value);
    }

    @Override
    public Circuito get(Object key) {
        return Circuito.get(key);
    }

    
    @Override
    public Circuito put(String key, Circuito value) {
        return Circuito.put(key, value);
    }

    @Override
    public Circuito remove(Object key) {
        return Circuito.remove(key);
    }

    @Override
    public void putAll( Map<? extends String, ? extends Circuito> m) {
        Circuito.putAll(m);
    }

    @Override
    public void clear() {
        Circuito.clear();
    }

    
    @Override
    public Set<String> keySet() {
        return Circuito.keySet();
    }

    
    @Override
    public Collection<Circuito> values() {
        return Circuito.values();
    }

    
    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        return Circuito.entrySet();
    }
}
