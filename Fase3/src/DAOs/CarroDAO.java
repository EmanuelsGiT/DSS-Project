package src.DAOs;

import src.Models.Carros.*;
import src.Models.Pilotos.Piloto;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
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
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Modelo,Marca,Classe,Cilindrada,Potencia,Fiabilidade,Pac,PotenciaEletrica FROM carros WHERE Modelo= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    String modelo = rs.getString("Modelo");
                    String marca = rs.getString("Marca");
                    String classe = rs.getString("Classe");
                    int cilindrada = rs.getInt("Cilindrada");
                    int potencia = rs.getInt("Potencia");
                    double fiabilidade = rs.getDouble("Fiabilidade");
                    double pac = rs.getDouble("Pac");
                    int potenciaEletrica = rs.getInt("PotenciaEletrica");
                    int eHibrido = 1;
                    if (rs.wasNull()) {
                        eHibrido = 0;
                    }

                    if (classe.equals(C1.class.getSimpleName()))
                        return new C1(marca, modelo, potencia, eHibrido, potenciaEletrica, pac);
                    else if (classe.equals(C2.class.getSimpleName()))
                        return new C2(marca, modelo, cilindrada, potencia, eHibrido, potenciaEletrica, pac);
                    else if (classe.equals(GT.class.getSimpleName()))
                        return new GT(marca, modelo, cilindrada, potencia, eHibrido, potenciaEletrica, pac);
                    else if (classe.equals(SC.class.getSimpleName()))
                        return new SC(marca, modelo, potencia, pac);
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Carro put(String key, Carro value) {
        String sql = "INSERT INTO carros (Marca,Modelo,Classe,Cilindrada,Potencia,Fiabilidade,Pac,PotenciaEletrica) VALUES (?,?,?,?,?,?,?,?);";
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);){

            ps.setString(1, value.getMarca());
            ps.setString(2, value.getModelo());
            ps.setString(3, value.getClass().getSimpleName());
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
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM carros;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Carro> values() {
        Collection<Carro> r = new HashSet<>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Modelo FROM carros;");
        ){
            while(rs.next()) {
                Carro c = this.get(rs.getString("Modelo"));
                r.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Carro>> entrySet() {
        return null;
    }
}
