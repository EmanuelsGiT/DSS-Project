package src.DAOs;

import src.Models.Utilizadores.Administrador;
import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdministradorDAO implements Map<String,Administrador> {
    private static AdministradorDAO singleton = null;

    private AdministradorDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS administradores (" +
                    "PalavraPasse VARCHAR(50) NOT NULL," +
                    "Nome VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Nome) REFERENCES utilizadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";


            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static AdministradorDAO getInstance() {
        if (AdministradorDAO.singleton == null) {
            AdministradorDAO.singleton = new AdministradorDAO();
        }
        return AdministradorDAO.singleton;
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
        if (!(value instanceof Administrador)) return false;
        Administrador p = (Administrador) value;
        return p.equals(get(p.getNome()));
    }

    @Override
    public Administrador get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome,PalavraPasse FROM administradores WHERE Nome= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Administrador(
                            rs.getString("Nome"),
                            rs.getString("PalavraPasse")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Administrador put(String key, Administrador value) {

        UtilizadorDAO utilizadorDAO = UtilizadorDAO.getInstance();
        utilizadorDAO.put(value.getNome(), value);

        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO administradores (Nome,PalavraPasse) VALUES (?,?);");) {
            ps.setString(1, value.getNome());
            ps.setString(2, value.getPass());
            ps.executeUpdate();

            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Administrador remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Administrador> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Administrador> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Administrador>> entrySet() {
        return null;
    }

}