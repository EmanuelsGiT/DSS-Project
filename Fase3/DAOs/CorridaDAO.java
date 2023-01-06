package DAOs;

import src.Models.Campeonatos.Corrida;
import src.Models.Campeonatos.Registo;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CorridaDAO implements Map<Integer, Corrida> {

    private static CorridaDAO singleton = null;

    private CorridaDAO() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Corridas (" +
                    "Id INT AUTO_INCREMENT PRIMARY KEY," +
                    "AdminHosting VARCHAR(255) NOT NULL," +
                    "WeatherVariability DECIMAL(11,10) NOT NULL," +
                    "Circuit VARCHAR(255) NOT NULL," +
                    "Finished BOOLEAN NOT NULL,"+
                    "FOREIGN KEY (AdminHosting) REFERENCES users(Username) ON DELETE CASCADE ON UPDATE CASCADE," +
                    ");";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS CorridaResults (" +
                    "Id INT ," +
                    "Position INT NOT NULL," +
                    "Registo VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (Id,Registo)," +
                    "FOREIGN KEY (Id) REFERENCES Corridas(Id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Registo) REFERENCES Registos(Registo) ON DELETE CASCADE ON UPDATE CASCADE," +
                    ");";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS CorridaReady (" +
                    "Id INT ," +
                    "Ready BOOLEAN NOT NULL," +
                    "Registo VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (Id,Registo)," +
                    "FOREIGN KEY (Id) REFERENCES Corridas(Id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Registo) REFERENCES Registos(Registo) ON DELETE CASCADE ON UPDATE CASCADE," +
                    ");";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTCorrida();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CorridaDAO getInstance() {
        if (CorridaDAO.singleton == null) {
            CorridaDAO.singleton = new CorridaDAO();
        }
        return CorridaDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Corridas;")) {
            if (rs.next())
                i = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTCorrida();
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
        if (!(Integer.class.isInstance(key)))
            return false;
        boolean r=false;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Id FROM Corridas WHERE Id= ?;");){
            ps.setInt(1,(Integer)key);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    r=true;
            }
        } catch (SQLException e) {
            e.printStackTCorrida();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public List<Registo> Registos(Integer key){
        List<Registo> res = new ArrayList<>();
        try (Connection conn = DataBaseData.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT Registo FROM CorridaResults WHERE Id= ? ORDER BY Position ASC;");){
            ps.setInt(1,key);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()){
                    res.add(RegistoDAO.getInstance().get(rs.getString("Registo")));
                }
            }
            } catch (SQLException e) {
                e.printStackTCorrida();
                throw new NullPointerException(e.getMessage());
            }
        return res;
    }
    public Map<Registo,Boolean> ready(Integer key){
        Map<Registo,Boolean> res = new HashMap<>();
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Registo,Ready FROM CorridaReady WHERE Id= ?;");){
            ps.setInt(1,key);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()){
                    res.put(RegistoDAO.getInstance().get(rs.getString("Registo")),rs.getBoolean("Ready"));
                }
            }
        } catch (SQLException e) {
            e.printStackTCorrida();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Corrida get(Object key) {
        if (!(Integer.class.isInstance(key)))
            return null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Id,AdminHosting,WeatherVariability,Circuit,Finished FROM Corridas WHERE Id= ?;");){
            ps.setInt(1,(Integer)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    boolean b=rs.getBoolean("Finished");
                    return new Corrida(
                            rs.getInt("Id"),
                            AdministradorDAO.getInstance().get(rs.getString("AdminHosting")),
                            b,
                            new Weather(rs.getDouble("WeatherVariability")),
                            CircuitoDAO.getInstance().get(rs.getString("Circuit")),
                            b?Registos((Integer) key): new ArrayList<Registo>(),
                            ready((Integer) key)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTCorrida();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof Corrida)) return false;
        Corrida p = (Corrida) value;
        return p.equals(get(p.getId()));
    }

    @Override
    public Corrida put(Integer key, Corrida Corrida) {
        String sql = "";
        if (key == null)
            sql = "INSERT INTO Corridas (AdminHosting,WeatherVariability,Circuit,Finished) VALUES (?,?,?,?);";
        else
            sql = "INSERT INTO Corridas (Id,AdminHosting,WeatherVariability,Circuit,Finished) VALUES (?,?,?,?,?);";

        try (Connection conn = DataBaseData.getConnection();) {
            conn.setAutoCommit(false);
            try (
                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ) {
                int n = 1;
                if (key != null) {
                    ps.setInt(n, Corrida.getId());
                    n++;
                }
                ps.setString(n, Corrida.getAdminHosting().getNome());
                n++;
                ps.setDouble(n, Corrida.getWeatherConditions().getVariability());
                n++;
                ps.setString(n, Corrida.getTrack().getName());
                n++;
                ps.setBoolean(n, Corrida.getFinished());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys();) {
                    if (rs.next())
                        Corrida.setId(rs.getInt(1));
                }
                } catch (SQLException e) {
                    return null;
                }
            try (
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaResults (Id,Position,Registo) VALUES (?,?,?) ");
            ) {
                List<Registo> t = Corrida.getResults();
                for (int i=0;i<t.size();i++){
                    ps.setInt(1,Corrida.getId());
                    ps.setInt(2,i);
                    ps.setString(3,t.get(i).getManager().getNome());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                return null;
            }
            try (
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaReady (Id,Ready,Registo) VALUES (?,?,?) ");
            ) {
                for (Entry<Registo,Boolean>e: Corrida.getReady().entrySet()){
                    ps.setInt(1,Corrida.getId());
                    ps.setBoolean(2,e.getValue());
                    ps.setString(3,e.getKey().getManager().getNome());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                return null;
            }
            conn.commit();
            conn.setAutoCommit(true);
            } catch (SQLException e) {
                return null;
            }
        return Corrida;
    }

    public Corrida put(Corrida Corrida) {
        return this.put(Corrida.getId(),Corrida);
    }


    public Corrida update(Corrida Corrida) {
        if (Corrida.getId()==null)
                return null;
        try (
                Connection conn = DataBaseData.getConnection();){
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement("UPDATE Corridas SET AdminHosting=?,WeatherVariability=?,Circuit=?,Finished=? WHERE Id=?;");){
                ps.setString(1,Corrida.getAdminHosting().getNome());
                ps.setDouble(2,Corrida.getWeatherConditions().getVariability());
                ps.setString(3,Corrida.getTrack().getName());
                ps.setBoolean(4,Corrida.getFinished());
                ps.setInt(5,Corrida.getId());
                ps.executeUpdate();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaResults (Id,Position,Registo) VALUES (?,?,?) ON DUPLICATE KEY UPDATE Position=?;");){
                List<Registo> t = Corrida.getResults();
                for (int i=0;i<t.size();i++) {
                    ps.setInt(1, Corrida.getId());
                    ps.setInt(2, i);
                    ps.setString(3, t.get(i).getManager().getNome());
                    ps.setInt(4, i);
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaReady (Id,Ready,Registo) VALUES (?,?,?) ON DUPLICATE KEY UPDATE Ready=?;");){
                for (Entry<Registo,Boolean>e:Corrida.getReady().entrySet()) {
                    ps.setInt(1, Corrida.getId());
                    ps.setBoolean(2, e.getValue());
                    ps.setString(3, e.getKey().getManager().getNome());
                    ps.setBoolean(4, e.getValue());
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Corrida;
    }


    @Override
    public Corrida remove(Object key) {
        Corrida value = this.get(key);
        if (value==null)
            return null;
        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Corridas WHERE Id = ?;");
        ){
            ps.setInt(1,(Integer)key);
            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Corrida> m) {
        try (Connection conn = DataBaseData.getConnection();) {
            conn.setAutoCommit(false);
            for (Entry<? extends Integer, ? extends Corrida> en:m.entrySet()) {
                Integer key = en.getKey();
                Corrida Corrida = en.getValue();
                String sql = "";
                if (key == null)
                    sql = "INSERT INTO Corridas (AdminHosting,WeatherVariability,Circuit,Finished) VALUES (?,?,?,?);";
                else
                    sql = "INSERT INTO Corridas (Id,AdminHosting,WeatherVariability,Circuit,Finished) VALUES (?,?,?,?,?);";
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                    int n = 1;
                    if (key != null) {
                        ps.setInt(n, Corrida.getId());
                        n++;
                    }
                    ps.setString(n, Corrida.getAdminHosting().getNome());
                    n++;
                    ps.setDouble(n, Corrida.getWeatherConditions().getVariability());
                    n++;
                    ps.setString(n, Corrida.getTrack().getName());
                    n++;
                    ps.setBoolean(n, Corrida.getFinished());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys();) {
                        if (rs.next())
                            Corrida.setId(rs.getInt(1));
                    }
                } catch (SQLException e) {
                    return;
                }
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaResults (Id,Position,Registo) VALUES (?,?,?) ");) {
                    List<Registo> t = Corrida.getResults();
                    for (int i = 0; i < t.size(); i++) {
                        ps.setInt(1, Corrida.getId());
                        ps.setInt(2, i);
                        ps.setString(3, t.get(i).getManager().getNome());
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    return;
                }
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO CorridaReady (Id,Ready,Registo) VALUES (?,?,?) ");) {
                    for (Entry<Registo, Boolean> e : Corrida.getReady().entrySet()) {
                        ps.setInt(1, Corrida.getId());
                        ps.setBoolean(2, e.getValue());
                        ps.setString(3, e.getKey().getManager().getNome());
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    return;
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM Corridas;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Set<Integer> keySet() {
        Set<Integer> r=new HashSet<Integer>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Id FROM Corridas;");
        ){
            while(rs.next())
                r.add(rs.getInt("Id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Corrida> values() {
        Collection<Corrida> r = new HashSet<Corrida>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Id,AdminHosting,WeatherVariability,Circuit,Finished FROM Corridas;");
        ){

                while(rs.next()) {
                    int id =rs.getInt("Id");
                    boolean b = rs.getBoolean("Finished");
                    r.add(new Corrida(
                            id,
                            AdministradorDAO.getInstance().get(rs.getString("AdminHosting")),
                            b,
                            new Weather(rs.getDouble("WeatherVariability")),
                            CircuitoDAO.getInstance().get(rs.getString("Circuit")),
                            b ? Registos(id) : new ArrayList<Registo>(),
                            ready(id)
                    ));
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<Integer, Corrida>> entrySet() {
        return values().stream()
                       .collect(Collectors.toMap(x->x.getId(), x -> x))
                       .entrySet();
    }
}