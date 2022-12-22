package DAOs;

import business.participants.Participant;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ParticipantDAO implements Map<String, Participant> {

    private static ParticipantDAO singleton = null;

    private ParticipantDAO() {
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS participants (" +
                    "Player VARCHAR(255) NOT NULL PRIMARY KEY," +
                    "Car INT NOT NULL UNIQUE," +
                    "Driver VARCHAR(50) NOT NULL UNIQUE," +
                    "NumberOfSetupChanges INT NOT NULL,"+
                    "FOREIGN KEY (Player) REFERENCES users(Username) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Car) REFERENCES cars(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Driver) REFERENCES drivers(DriverName) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static ParticipantDAO getInstance() {
        if (ParticipantDAO.singleton == null) {
            ParticipantDAO.singleton = new ParticipantDAO();
        }
        return ParticipantDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM participants;")) {
            if (rs.next())
                i = rs.getInt(1);
        } catch (Exception e) {
            // Erro a criar tabela...
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
        try (Connection conn = DatabaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Player FROM participants WHERE Player= ?;");){
            ps.setString(1,key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    r=true;
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public Participant get(Object key) {
        try (Connection conn = DatabaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Player,Car,Driver,NumberOfSetupChanges FROM participants WHERE Player= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Participant(
                        rs.getInt("NumberOfSetupChanges"),
                        RaceCarDAO.getInstance().get(rs.getInt("Car")),
                        DriverDAO.getInstance().get(rs.getString("Driver")),
                        PlayerDAO.getInstance().get(rs.getString("Player"))
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
        if (!(value instanceof Participant)) return false;
        Participant p = (Participant) value;
        return p.equals(get(p.getManager().getUsername()));
    }

    @Override
    public Participant put(String key, Participant part) {
        try (
            Connection conn = DatabaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO participants (Player,Car,Driver,NumberOfSetupChanges) VALUES (?,?,?,?);");
            ){
            ps.setString(1, key);
            ps.setInt(2,part.getCar().getId());
            ps.setString(3,part.getDriver().getDriverName());
            ps.setInt(4,part.getNumberOfSetupChanges());
            ps.executeUpdate();
            return part;
        } catch (SQLException e) {
            return null;
        }
    }

    public Participant put(Participant part) {
        return this.put(part.getManager().getUsername(),part);
    }

    public Participant update(Participant part) {
        try (
            Connection conn = DatabaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE participants SET Car=?,Driver=?,NumberOfSetupChanges=? WHERE Player=?;");
            ){
            ps.setInt(1,part.getCar().getId());
            ps.setString(2,part.getDriver().getDriverName());
            ps.setInt(3,part.getNumberOfSetupChanges());
            ps.setString(4,part.getManager().getUsername());
            ps.executeUpdate();
            return part;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Participant remove(Object key) {
        Participant value = this.get(key);
        if (value==null)
            return null;
        try (
            Connection conn = DatabaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM participants WHERE Player = ?;");
            ){
            ps.setString(1,(key.toString()));
            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Participant> m) {
        try (Connection conn = DatabaseData.getConnection();){
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO participants (Player,Car,Driver,NumberOfSetupChanges) VALUES (?,?,?,?);");) {
                for (Map.Entry e : m.entrySet()) {
                    ps.setString(1, ((String) e.getKey()));
                    ps.setInt(2, ((Participant) e.getValue()).getCar().getId());
                    ps.setString(3, ((Participant) e.getValue()).getDriver().getDriverName());
                    ps.setInt(4, ((Participant) e.getValue()).getNumberOfSetupChanges());
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
        try ( Connection conn = DatabaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM participants;");
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO MUDAR ISTO
        }
    }
    
    @Override
    public Set<String> keySet() {
        Set<String> r=new HashSet<String>();
        try (
            Connection conn = DatabaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Player FROM participants;");
            ){
                while(rs.next())
                    r.add(rs.getString("Player"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Participant> values() {
        Collection<Participant> r = new HashSet<Participant>();
        try (
            Connection conn = DatabaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Player,Car,Driver,NumberOfSetupChanges FROM participants;");
            ){
            while(rs.next())
                r.add(new Participant(
                        rs.getInt("NumberOfSetupChanges"),
                        RaceCarDAO.getInstance().get(rs.getInt("Car")),
                        DriverDAO.getInstance().get(rs.getString("Driver")),
                        PlayerDAO.getInstance().get(rs.getString("Player"))
                ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Participant>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(x->x.getManager().getUsername(), x -> x)).entrySet();
    }
}
