package DAOs;

import src.Models.Utilizadores.Administrador;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdminDAO implements Map<String,Administrador> {
    private static AdminDAO singleton = null;

    private AdminDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "Username VARCHAR(255) NOT NULL PRIMARY KEY," +
                    "Password CHAR(60) NOT NULL," +
                    "Premium BOOLEAN);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static AdminDAO getInstance() {
        if (AdminDAO.singleton == null) {
            AdminDAO.singleton = new AdminDAO();
        }
        return AdminDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM users WHERE Premium IS NOT NULL")
        ) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
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
        boolean r = false;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Username FROM users WHERE Username= ? AND Premium IS NOT NULL;");) {
            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
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
    public boolean containsValue(Object value) {
        if (!(value instanceof Administrador)) return false;
        Administrador p = (Administrador) value;
        return p.equals(get(p.getUsername()));
    }

    @Override
    public Administrador get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Username,Password,Premium FROM users WHERE Username= ? AND Premium IS NOT NULL;");) {

            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    return new Administrador(rs.getString(1),rs.getString(2),rs.getBoolean(3));
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public Administrador put(String key, Administrador Administrador) {
        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (Username,Password,Premium) VALUES (?,?,?);");) {
            ps.setString(1, Administrador.getUsername());
            ps.setString(2, Administrador.getHashedPassword());
            ps.setBoolean(3, Administrador.getPremium());
            ps.executeUpdate();
            return Administrador;
        } catch (SQLException e) {
            return null;
        }
    }

    public Administrador put(Administrador u) {
        return put(u.getUsername(), u);
    }

    @Override
    public Administrador remove(Object key) {
        Administrador user = this.get(key);
        if (user == null) return null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE Username = ? AND Premium IS NOT NULL;");) {
            ps.setString(1, key.toString());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Administrador> m) {
        try (Connection conn = DataBaseData.getConnection();) {
            conn.setAutoCommit(false);
            try (PreparedStatement stm = conn.prepareStatement("INSERT INTO users (Username,Password,Premium) VALUES (?,?,?);");) {
                for (Entry e : m.entrySet()) {
                    stm.setString(1, (String) e.getKey());
                    stm.setString(2, ((Administrador) e.getValue()).getHashedPassword());
                    stm.setBoolean(3, ((Administrador) e.getValue()).getPremium());
                    stm.executeUpdate();
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
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();) {
            stm.executeUpdate("DELETE FROM users WHERE Premium IS NOT NULL;");
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO MUDAR ISTO
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r = new HashSet<String>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Username FROM users WHERE Premium IS NOT NULL;");
        ) {
            while (rs.next())
                r.add(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return r;
    }

    @Override
    public Collection<Administrador> values() {
        Collection<Administrador> r = new HashSet<Administrador>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Username,Password,Premium FROM users WHERE Premium IS NOT NULL;");
        ) {
            while (rs.next())
                r.add(new Administrador(rs.getString("Username"),rs.getString("Password"),rs.getBoolean("Premium")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Administrador>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(Administrador::getUsername, x -> x)).entrySet();
    }
}