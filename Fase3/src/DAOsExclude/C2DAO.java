package src.DAOsExclude;

import src.Models.Carros.C2;
import src.DAOs.DataBaseData; //provisorio

import java.sql.*;
import java.util.*;

public class C2DAO implements Map<String, C2> {
    private static C2DAO singleton = null;

    private C2DAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS C2s (" +
                    "Hibrido INT NOT NULL DEFAULT 0," +
                    "potenciaHibrido INT NOT NULL," +
                    "FOREIGN KEY (Modelo) REFERENCES carros(Modelo) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static C2DAO getInstance() {
        if (C2DAO.singleton == null) {
            C2DAO.singleton = new C2DAO();
        }
        return C2DAO.singleton;
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
    public C2 get(Object key) {
        return null;
    }

    @Override
    public C2 put(String key, C2 value) {
        /* 
        CarroDAO carroDAO = carroDAO.getInstance();
        carroDAO.put(value.getModelo(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO C2s (Nome,PalavraPasse,PontuacaoTotal) VALUES (?,?,?);");){

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
    public C2 remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends C2> m) {
    }

    @Override
    public void clear() {    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<C2> values() {
        return null;
    }

    @Override
    public Set<Entry<String, C2>> entrySet() {
        return null;
    }
}
