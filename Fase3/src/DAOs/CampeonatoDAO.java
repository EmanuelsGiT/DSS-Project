package src.DAOs;

import src.Models.Campeonatos.Campeonato;
import src.Models.Campeonatos.Corrida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CampeonatoDAO implements Map<String, Campeonato> {
    private static CampeonatoDAO singleton = null;

    private CampeonatoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS campeonatos (" +
                    "Nome VARCHAR(50) NOT NULL PRIMARY KEY);";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS corridas (" +
                    "Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "Nome_Campeonato VARCHAR(50) NOT NULL," +
                    "Nome_Circuito VARCHAR(50) NOT NULL," +
                    "Clima VARCHAR(10) NOT NULL," +
                    "FOREIGN KEY (Nome_Campeonato) REFERENCES campeonatos(Nome) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Circuito) REFERENCES circuitos(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CampeonatoDAO getInstance() {
        if (CampeonatoDAO.singleton == null) {
            CampeonatoDAO.singleton = new CampeonatoDAO();
        }
        return CampeonatoDAO.singleton;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Campeonato get(Object key) {
        return null;
    }

    @Override
    public Campeonato put(String key, Campeonato value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO campeonatos (Nome) VALUES (?);")){

            ps.setString(1, value.getNome());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Corrida c : value.getCorridas()) {
            try (Connection conn = DataBaseData.getConnection();
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO corridas (Nome_Campeonato,Nome_Circuito,Clima) VALUES (?,?,?);")) {

                ps.setString(1, value.getNome());
                ps.setString(2, c.getCircuito().getNome());
                Corrida.Clima clima = c.getClima();
                if (clima == Corrida.Clima.MOLHADO)
                    ps.setString(3, "Molhado");
                else if (clima == Corrida.Clima.SECO)
                    ps.setString(3, "SECO");

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public Campeonato remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {

    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM corridas;");
            stm.executeUpdate("DELETE FROM campeonatos;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Campeonato> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        return null;
    }
}
