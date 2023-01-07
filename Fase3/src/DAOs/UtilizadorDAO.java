package src.DAOs;

import src.Models.Utilizadores.Utilizador;

import java.sql.*;
import java.util.*;

public class UtilizadorDAO implements Map<String, Utilizador> {
    private static UtilizadorDAO singleton = null;

    private UtilizadorDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS utilizadores (" +
                    "Nome VARCHAR(50) NOT NULL PRIMARY KEY);";
            stm.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) {
            UtilizadorDAO.singleton = new UtilizadorDAO();
        }
        return UtilizadorDAO.singleton;
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
        boolean r=false;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome FROM utilizadores WHERE Nome= ?;");){
            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    r=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Utilizador get(Object key) {
        return null;
    }

    @Override
    public Utilizador put(String key, Utilizador value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO utilizadores (Nome) VALUES (?);");) {

            ps.setString(1, value.getNome());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizador remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> m) {

    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM anonimos;");
            stm.executeUpdate("DELETE FROM registados;");
            stm.executeUpdate("DELETE FROM administradores;");
            stm.executeUpdate("DELETE FROM jogadores;");
            stm.executeUpdate("DELETE FROM utilizadores;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Utilizador> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Utilizador>> entrySet() {
        return null;
    }

}
