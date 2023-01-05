package DAOs;

import src.Models.Pilotos.Piloto;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class PilotoDAO implements Map<String, Piloto> {

    private static PilotoDAO singleton = null;

    private PilotoDAO() {

        try (Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS pilotos(" +
                                 "NomePiloto VARCHAR(50) NOT NULL PRIMARY KEY," +
                                 "PilotoCTS DECIMAL(1,3) NOT NULL," +
                                 "PilotoSVA DECIMAL(1,3) NOT NULL)";

            stm.executeUpdate(sql);
        } catch (SQLException error) {
            error.printStackTrace();
            throw new RuntimeException(error.getMessage());

        }

    }

    public static PilotoDAO getInstance() {

        if (PilotoDAO.singleton == null) PilotoDAO.singleton = new PilotoDAO();
        return PilotoDAO.singleton;

    }

    
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM pilotos;");){
                if (rs.next())
                    i = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r=false;
        try (Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT NomePiloto FROM pilotos WHERE NomePiloto= ?;");){
            ps.setString(1,key.toString());
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    r = true;
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public Piloto get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT PilotoCTS,PilotoSVA FROM pilotos WHERE NomePiloto=?;");){
            ps.setString(1,key.toString());
            try(ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Piloto(
                            key.toString(),
                            rs.getFloat("PilotoCTS"),
                            rs.getFloat("PilotoSVA")
                    );
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof Piloto)) return false;
        Piloto p = (Piloto) value;
        return p.equals(get(p.getNome()));
    }


    @Override
    public Piloto put(String key, Piloto piloto) {
        try (
            Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pilotos (NomePiloto,PilotoCTS,PilotoSVA) VALUES (?,?,?);");){
            ps.setString(1,piloto.getNome());
            ps.setDouble(2,piloto.getCTS());
            ps.setDouble(3,piloto.getSVA());
            ps.executeUpdate();
            return piloto;
        } catch (SQLException e) {
            return null;
        }
    }

    public Piloto put(Piloto piloto) {
        return this.put(piloto.getNome(), piloto);
    }

    @Override
    public Piloto remove(Object key) {
        Piloto piloto = this.get(key);
        if (piloto==null){
            return null;
        }
        try(Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pilotos WHERE NomePiloto = ?;");){
            ps.setString(1,key.toString());
            ps.executeUpdate();
            return piloto;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {
        try (Connection conn = DataBaseData.getConnection();){
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO pilotos (NomePiloto,PilotoCTS,PilotoSVA) VALUES (?,?,?);");) {
                for (Entry<? extends String, ? extends Piloto> e : m.entrySet()) {
                    ps.setString(1, (String) e.getKey());
                    ps.setDouble(2, ((Piloto) e.getValue()).getCTS());
                    ps.setDouble(3, ((Piloto) e.getValue()).getSVA());
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
        try (Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM pilotos;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r=new HashSet<String>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT NomePiloto FROM pilotos;");){
                while(rs.next())
                    r.add(rs.getString("NomePiloto"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Piloto> values() {
        Collection<Piloto> r = new HashSet<Piloto>();
        try (
            Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT NomePiloto,PilotoCTS,PilotoSVA FROM pilotos;");
            ){
                while(rs.next())
                    r.add(new Piloto(
                        rs.getString("NomePiloto"),
                        rs.getDouble("PilotoCTS"),
                        rs.getDouble("PilotoSVA")
                    ));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Piloto>> entrySet() {
        return values().stream().collect(
               Collectors.toMap(Piloto::getNome, x -> x)).entrySet();
    }
}
