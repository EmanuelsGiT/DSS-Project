package DAOs;

import Models.Piloto;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class DriverDAO implements Map<String, Driver> {

    private static DriverDAO singleton = null;

    private DriverDAO() {

        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS drivers(" +
                                 "DriverName VARCHAR(50) NOT NULL PRIMARY KEY," +
                                 "DriverCTS DECIMAL(10,2) NOT NULL," +
                                 "DriverSVA DECIMAL(10,2) NOT NULL)";

            stm.executeUpdate(sql);
        } catch (SQLException error) {

            error.printStackTrace();
            throw new RuntimeException(error.getMessage());

        }

    }

    public static DriverDAO getInstance() {

        if (DriverDAO.singleton == null) DriverDAO.singleton = new DriverDAO();
        return DriverDAO.singleton;

    }

    
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM drivers;");){
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
            PreparedStatement ps = conn.prepareStatement("SELECT DriverName FROM drivers WHERE DriverName= ?;");){
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
    public Driver get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT DriverCTS,DriverSVA FROM drivers WHERE DriverName=?;");){
            ps.setString(1,key.toString());
            try(ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Driver(
                            key.toString(),
                            rs.getFloat("DriverCTS"),
                            rs.getFloat("DriverSVA")
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
        if (!(value instanceof Driver)) return false;
        Driver p = (Driver) value;
        return p.equals(get(p.getDriverName()));
    }


    @Override
    public Driver put(String key, @NotNull Driver driver) {
        try (
            Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO drivers (DriverName,DriverCTS,DriverSVA) VALUES (?,?,?);");){
            ps.setString(1,driver.getDriverName());
            ps.setFloat(2,driver.getDriverCTS());
            ps.setFloat(3,driver.getDriverSVA());
            ps.executeUpdate();
            return driver;
        } catch (SQLException e) {
            return null;
        }
    }

    public Driver put(@NotNull Driver driver) {
        return this.put(driver.getDriverName(),driver);
    }

    @Override
    public Driver remove(Object key) {
        Driver driver = this.get(key);
        if (driver==null){
            return null;
        }
        try(Connection conn = DataBaseData.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM drivers WHERE DriverName = ?;");){
            ps.setString(1,key.toString());
            ps.executeUpdate();
            return driver;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Driver> m) {
        try (Connection conn = DataBaseData.getConnection();){
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO drivers (DriverName,DriverCTS,DriverSVA) VALUES (?,?,?);");) {
                for (Entry e : m.entrySet()) {
                    ps.setString(1, (String) e.getKey());
                    ps.setFloat(2, ((Driver) e.getValue()).getDriverCTS());
                    ps.setFloat(3, ((Driver) e.getValue()).getDriverSVA());
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
            stm.executeUpdate("DELETE FROM drivers;");
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO MUDAR ISTO
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r=new HashSet<String>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT DriverName FROM drivers;");){
                while(rs.next())
                    r.add(rs.getString("DriverName"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Driver> values() {
        Collection<Driver> r = new HashSet<Driver>();
        try (
            Connection conn = DataBaseData.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT DriverName,DriverCTS,DriverSVA FROM drivers;");
            ){
                while(rs.next())
                    r.add(new Driver(
                        rs.getString("DriverName"),
                        rs.getFloat("DriverCTS"),
                        rs.getFloat("DriverSVA")
                    ));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Driver>> entrySet() {
        return values().stream().collect(
               Collectors.toMap(Driver::getDriverName, x -> x)).entrySet();
    }
}
