package src.DAOsExclude;

import src.DAOs.DataBaseData;
import src.DAOs.RegistadoDAO;
import src.Models.Campeonatos.Registo;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RegistoDAO implements Map<String, Registo> {

    private static RegistoDAO singleton = null;

    private RegistoDAO() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Registos (" +
                    "Registo VARCHAR(255) NOT NULL PRIMARY KEY," +
                    "CarroSetup INT NOT NULL UNIQUE," +
                    "Piloto VARCHAR(50) NOT NULL UNIQUE," +
                    "NumAfinacoes INT NOT NULL,"+
                    "FOREIGN KEY (Registo) REFERENCES users(Username) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (CarroSetup) REFERENCES CarroSetups(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Piloto) REFERENCES Pilotos(PilotoName) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RegistoDAO getInstance() {
        if (RegistoDAO.singleton == null) {
            RegistoDAO.singleton = new RegistoDAO();
        }
        return RegistoDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Registos;")) {
            if (rs.next())
                i = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r=false;
        try (Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Registo FROM Registos WHERE Registo= ?;");){
            ps.setString(1,key.toString());
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
    public Registo get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Registo,CarroSetup,Piloto,NumAfinacoes FROM Registos WHERE Registo= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Registo(
                        rs.getInt("NumAfinacoes"),
                        RegistadoDAO.getInstance().get(rs.getString("Piloto")),
                        RegistadoDAO.getInstance().get(rs.getString("Registo")),
                        CarroSetupDAO.getInstance().get(rs.getInt("CarroSetup"))
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof Registo)) return false;
        Registo p = (Registo) value;
        return p.equals(get(p.getManager().getNome()));
    }

    @Override
    public Registo put(String key, Registo part) {
        try (
            Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Registos (Registo,CarroSetup,Piloto,NumAfinacoes) VALUES (?,?,?,?);");
            ){
            ps.setString(1, key);
            ps.setInt(2,part.getCarroSetup().getId());
            ps.setString(3,part.getPiloto().getNome());
            ps.setInt(4,part.getNumAfinacoes());
            ps.executeUpdate();
            return part;
        } catch (SQLException e) {
            return null;
        }
    }

    public Registo put(Registo part) {
        return this.put(part.getManager().getNome(),part);
    }

    public Registo update(Registo part) {
        try (
            Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE Registos SET CarroSetup=?,Piloto=?,NumAfinacoes=? WHERE Registo=?;");
            ){
            ps.setInt(1,part.getCarroSetup().getId());
            ps.setString(2,part.getPiloto().getNome());
            ps.setInt(3,part.getNumAfinacoes());
            ps.setString(4,part.getManager().getNome());
            ps.executeUpdate();
            return part;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Registo remove(Object key) {
        Registo value = this.get(key);
        if (value==null)
            return null;
        try (
            Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Registos WHERE Registo = ?;");
            ){
            ps.setString(1,(key.toString()));
            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Registo> m) {
        try (Connection conn = DataBaseData.getConnection();){
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Registos (Registo,CarroSetup,Piloto,NumAfinacoes) VALUES (?,?,?,?);");) {
                for (Map.Entry e : m.entrySet()) {
                    ps.setString(1, ((String) e.getKey()));
                    ps.setInt(2, ((Registo) e.getValue()).getCarroSetup().getId());
                    ps.setString(3, ((Registo) e.getValue()).getPiloto().getNome());
                    ps.setInt(4, ((Registo) e.getValue()).getNumAfinacoes());
                    ps.executeUpdate();
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM Registos;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Set<String> keySet() {
        Set<String> r=new HashSet<String>();
        try (
            Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Registo FROM Registos;");
            ){
                while(rs.next())
                    r.add(rs.getString("Registo"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Registo> values() {
        Collection<Registo> r = new HashSet<Registo>();
        try (
            Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Registo,CarroSetup,Piloto,NumAfinacoes FROM Registos;");
            ){
            while(rs.next())
                r.add(new Registo(
                        rs.getInt("NumAfinacoes"),
                        RaceCarroSetupDAO.getInstance().get(rs.getInt("CarroSetup")),
                        RegistadoDAO.getInstance().get(rs.getString("Piloto")),
                        RegistadoDAO.getInstance().get(rs.getString("Registo"))
                ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Registo>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(x->x.getManager().getNome(), x -> x)).entrySet();
    }
}
