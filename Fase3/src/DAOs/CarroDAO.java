package src.DAOs;

import src.Models.Carros.*;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CarroDAO implements Map<String, Carro> {
    private static CarroDAO singleton = null;

    private CarroDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    "Modelo VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "Marca VARCHAR(50) NOT NULL," +
                    "Classe VARCHAR(50) NOT NULL," +
                    "Cilindrada INT NOT NULL," +
                    "Potencia INT NOT NULL," +
                    "Fiabilidade DOUBLE(4,2) NOT NULL CHECK (Fiabilidade BETWEEN 0 AND 1)," +
                    "Pac DOUBLE(4,2) NOT NULL CHECK (Pac BETWEEN 0 AND 1)," +
                    "PotenciaEletrica INT NULL);";
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
        return false;
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
        String sql = "INSERT INTO carros (Marca,Modelo,Classe,Cilindrada,Potencia,Fiabilidade,Pac,PotenciaEletrica) VALUES (?,?,?,?,?,?,?);";
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);){

            ps.setString(1, value.getMarca());
            ps.setString(2, value.getModelo());
            ps.setString(3, value.getClass().getName());
            ps.setInt(4, value.getCilindrada());
            ps.setInt(5, value.getPotencia());
            ps.setDouble(6, value.getFiabilidade());
            ps.setDouble(7, value.getPAC());

            if (value instanceof C1) {
                if (((C1) value).getHibrido() == 1)
                    ps.setInt(8, ((C1) value).getPotenciaHibrido());
                else
                    ps.setNull(8, Types.INTEGER);

            }
            else if (value instanceof C2) {
                if (((C2) value).getHibrido() == 1)
                    ps.setInt(8, ((C2) value).getPotenciaHibrido());
                else
                    ps.setNull(8, Types.INTEGER);
            }
            else if (value instanceof GT) {
                if (((GT) value).getHibrido() == 1)
                    ps.setInt(8, ((GT) value).getPotenciaHibrido());
                else
                    ps.setNull(8, Types.INTEGER);
            } else
                ps.setNull(8, Types.INTEGER);

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
