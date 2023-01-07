package src.DAOsExclude;

import src.Models.Carros.GT;
import src.DAOs.DataBaseData; //provisorio

import java.sql.*;
import java.util.*;

public class GTDAO implements Map<String, GT> {
    private static GTDAO singleton = null;

    private GTDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS GTs (" +
                    "Hibrido INT NOT NULL DEFAULT 0," +
                    "potenciaHibrido INT NOT NULL," +
                    "FOREIGN KEY (Modelo) REFERENCES carros(Modelo) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static GTDAO getInstance() {
        if (GTDAO.singleton == null) {
            GTDAO.singleton = new GTDAO();
        }
        return GTDAO.singleton;
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
    public GT get(Object key) {
        return null;
    }

    @Override
    public GT put(String key, GT value) {
        /* 
        CarroDAO carroDAO = carroDAO.getInstance();
        carroDAO.put(value.getModelo(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO GTs (Nome,PalavraPasse,PontuacaoTotal) VALUES (?,?,?);");){

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
    public GT remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends GT> m) {
    }

    @Override
    public void clear() {    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<GT> values() {
        return null;
    }

    @Override
    public Set<Entry<String, GT>> entrySet() {
        return null;
    }
}
