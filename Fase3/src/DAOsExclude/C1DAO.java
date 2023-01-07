package src.DAOsExclude;

import src.Models.Carros.C1;
import src.DAOs.DataBaseData; //provisorio

import java.sql.*;
import java.util.*;

public class C1DAO implements Map<String, C1> {
    private static C1DAO singleton = null;

    private C1DAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS C1s (" +
                    "Hibrido INT NOT NULL DEFAULT 0," +
                    "potenciaHibrido INT NOT NULL," +
                    "FOREIGN KEY (Modelo) REFERENCES carros(Modelo) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static C1DAO getInstance() {
        if (C1DAO.singleton == null) {
            C1DAO.singleton = new C1DAO();
        }
        return C1DAO.singleton;
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
    public C1 get(Object key) {
        return null;
    }

    @Override
    public C1 put(String key, C1 value) {
        /* 
        CarroDAO carroDAO = carroDAO.getInstance();
        carroDAO.put(value.getModelo(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO C1s (Nome,PalavraPasse,PontuacaoTotal) VALUES (?,?,?);");){

            ps.setString(1, value.getNome());
            ps.setString(2, value.getPass());
            ps.setInt(3, value.getPontuacaoTotal());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    public C1 remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends C1> m) {
    }

    @Override
    public void clear() {    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<C1> values() {
        return null;
    }

    @Override
    public Set<Entry<String, C1>> entrySet() {
        return null;
    }
}
