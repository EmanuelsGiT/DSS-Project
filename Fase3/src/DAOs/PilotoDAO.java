package src.DAOs;

import src.Models.Circuitos.Circuito;
import src.Models.Pilotos.Piloto;
import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PilotoDAO implements Map<String, Piloto> {
    private static PilotoDAO singleton = null;
    private PilotoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS pilotos (" +
                    "Nome VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "Cts DOUBLE NOT NULL," +
                    "Sva DOUBLE NOT NULL);";
            stm.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PilotoDAO getInstance() {
        if (PilotoDAO.singleton == null) {
            PilotoDAO.singleton = new PilotoDAO();
        }
        return PilotoDAO.singleton;
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
    public Piloto get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome,Cts,Sva FROM pilotos WHERE Nome= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Piloto(
                            rs.getString("Nome"),
                            rs.getDouble("Cts"),
                            rs.getDouble("Sva")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Piloto put(String key, Piloto value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO pilotos (Nome,Cts,Sva) VALUES (?,?,?);");) {

            ps.setString(1, value.getNome());
            ps.setDouble(2, value.getCTS());
            ps.setDouble(3, value.getSVA());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Piloto remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Piloto> values() {
        Collection<Piloto> r = new HashSet<>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Nome FROM pilotos;");
        ){
            while(rs.next()) {
                Piloto c = this.get(rs.getString("Nome"));
                r.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Piloto>> entrySet() {
        return null;
    }
}
