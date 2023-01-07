package src.DAOsExclude;

import src.DAOs.AdministradorDAO;
import src.DAOs.DataBaseData;
import src.Models.Campeonatos.Campeonato;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CampeonatoDAO implements Map<String, Campeonato> {
    private static CampeonatoDAO singleton = null;
    private static AdministradorDAO adb = AdministradorDAO.getInstance();

    private CampeonatoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS campeonatos (" +
                    "Nome INT AUTO_INCREMENT PRIMARY KEY," +
                    "Administrador VARCHAR(255) NOT NULL,"+
                    "FOREIGN KEY (Administrador) REFERENCES users(Username) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CampeonatoDAO getInstance() {
        if (singleton==null)
            singleton=new CampeonatoDAO();
        return singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM campeonatos;");){
            if (rs.next())
                i = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof Integer)) return false;
        boolean r=false;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome FROM pilotos WHERE Nome= ?;");){
            ps.setInt(1,(Integer) key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    r = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof Campeonato)) return false;
        Campeonato p = (Campeonato) value;
        return p.equals(get(p.getNome()));
    }

    @Override
    public Campeonato get(Object key) {
        if (!(key instanceof String)) return null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome FROM campeonatos WHERE Nome= ?;");
        ) {
            ps.setInt(1,(Integer)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Campeonato(new Campeonato(
                            rs.getString("Nome"),
                            rs.getString("Corridas"),
                            rs.getString("Registos")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Campeonato put(String key, Campeonato value) {
        String sql = "";
        if (key == null) {
            sql = "INSERT INTO campeonatos (Administrador) VALUES (?);";
        } else {
            sql = "INSERT INTO campeonatos (Nome,Administrador) VALUES (?,?);";
        }
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            int n = 1;
            if (key != null) {
                ps.setString(n, value.getNome());
                n++;
            }
            ps.setString(n, value.getNome());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys();) {
                if (rs.next())
                    value.setNome(rs.getString(1));
            }
            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    public Campeonato put(Campeonato value) {
        return put(value.getNome(),value);
    }


    @Override
    public Campeonato remove(Object key) {
        Campeonato cha = get(key);
        if (cha == null)
            return null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM campeonatos WHERE Nome = ?;");
        ) {
            ps.setInt(1, (int) key);
            ps.executeUpdate();
            return cha;
        } catch (SQLException e) {
            return null;
        }
    }


    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {
        try (Connection conn = DataBaseData.getConnection();) {
            conn.setAutoCommit(false);
            String sql="";
            for (Entry e : m.entrySet()) {
                if (e.getKey()!=null) sql="INSERT INTO campeonatos (Nome,Corridas,Registos) VALUES (?,?,?);";
                else sql="INSERT INTO campeonatos (Administrador) VALUES (?);";
            try (PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                int n = 1;
                if (e.getKey() != null) {
                    ps.setInt(n, (Integer) e.getKey());
                    n++;
                }
                ps.setString(n, ((Campeonato)e.getValue()).getNome());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys();) {
                    if (rs.next())
                        ((Campeonato)e.getValue()).setNome(rs.getString(1));
                }
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
             Statement stm = conn.createStatement();
        ) {
            stm.executeUpdate("DELETE FROM campeonatos;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r = new HashSet<>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Nome FROM campeonatos;");
        ) {
            while (rs.next())
                r.add(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Campeonato> values() {
        Collection<Campeonato> r = new HashSet<Campeonato>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Nome,Corridas,Registos FROM campeonatos;");
        ) {
            while (rs.next())
                r.add(new Campeonato(
                    rs.getString("Nome"),
                    rs.getString("Corridas"),
                    rs.getString("Registos")));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return r;
    }

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(Campeonato::getNome, x -> x)).entrySet();
    }
}