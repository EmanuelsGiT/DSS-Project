package src.DAOsExclude;

import src.Models.Carros.Carro;
import src.DAOs.DataBaseData;

import java.sql.*;
import java.util.*;

public class CarroDAO implements Map<String, Carro> {
    private static CarroDAO singleton = null;

    private CarroDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    "Marca VARCHAR(50) NOT NULL," +
                    "Modelo VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "Cilindrada INT NOT NULL," +
                    "Fiabilidade DOUBLE(2,5) NOT NULL" +
                    "Classe CHAR(2) NOT NULL" +
                    ");";
            stm.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CarroDAO getInstance() {
        if (CarroDAO.singleton == null) {
            CarroDAO.singleton = new CarroDAO();
        }
        return CarroDAO.singleton;
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
             PreparedStatement ps = conn.prepareStatement("SELECT Modelo FROM carros WHERE Modelo= ?;");){
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
    public Carro get(Object key) {
        return null;
    }

    @Override
    public Carro put(String key, Carro value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO carros (Modelo) VALUES (?);");) {

            ps.setString(1, value.getModelo());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Carro remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Carro> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Carro> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Carro>> entrySet() {
        return null;
    }

}

