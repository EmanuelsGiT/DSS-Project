package DAOs;

import Models.Circuito;

import java.util.*;

public class CircuitDAO implements Map<String , Circuito> {

    private static CircuitDAO singleton = null;

    public static CircuitDAO getInstance() {

        if (CircuitDAO.singleton == null) CircuitDAO.singleton = new CircuitDAO();
        return CircuitDAO.singleton;

    }

    private Map<String,Circuito> Circuitos=Map.of(
            "Monza",new Circuito("Monza",5.793F,53, new ArrayList<CircuitoSection>(Arrays.asList(
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.75F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CHICANE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.2F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.5F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CHICANE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.05F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.6F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.8F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F)
            ))),
            "SPA",new Circuito("SPA",7.004F,44,new ArrayList<CircuitoSection>(Arrays.asList(
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.5F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.2F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.2F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.5F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.05F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.6F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.8F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CURVE, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.STRAIGHT, 0.15F),
                    new CircuitoSection(CircuitoSection.CircuitoSectionType.CHICANE,0.2F)
            )))
    );

    @Override
    public int size() {
        return Circuitos.size();
    }

    @Override
    public boolean isEmpty() {
        return Circuitos.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return Circuitos.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return Circuitos.containsValue(value);
    }

    @Override
    public Circuito get(Object key) {
        return Circuitos.get(key);
    }

    @Nullable
    @Override
    public Circuito put(String key, Circuito value) {
        return Circuitos.put(key, value);
    }

    @Override
    public Circuito remove(Object key) {
        return Circuitos.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends Circuito> m) {
        Circuitos.putAll(m);
    }

    @Override
    public void clear() {
        Circuitos.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return Circuitos.keySet();
    }

    @NotNull
    @Override
    public Collection<Circuito> values() {
        return Circuitos.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        return Circuitos.entrySet();
    }
}
