package src.DAOsExclude;

import src.Models.Carros.SC;
import src.DAOs.DataBaseData; //provisorio

import java.sql.*;
import java.util.*;

public class SCDAO implements Map<String, SC> {
    private static SCDAO singleton = null;

    private SCDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS SCs (" +
                    "FOREIGN KEY (Modelo) REFERENCES carros(Modelo) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static SCDAO getInstance() {
        if (SCDAO.singleton == null) {
            SCDAO.singleton = new SCDAO();
        }
        return SCDAO.singleton;
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
    public SC get(Object key) {
        return null;
    }

    @Override
    public SC put(String key, SC value) {
        /* 
        CarroDAO carroDAO = carroDAO.getInstance();
        carroDAO.put(value.getModelo(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO SCs (Nome,PalavraPasse,PontuacaoTotal) VALUES (?,?,?);");){

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
    public SC remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends SC> m) {
    }

    @Override
    public void clear() {    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<SC> values() {
        return null;
    }

    @Override
    public Set<Entry<String, SC>> entrySet() {
        return null;
    }
}
