package DAOs;

import business.users.Player;
import business.users.User;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerDAO implements Map<String, Player> {
    private static PlayerDAO singleton = null;

    private PlayerDAO() {
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement()) {
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

    public static PlayerDAO getInstance() {
        if (PlayerDAO.singleton == null) {
            PlayerDAO.singleton = new PlayerDAO();
        }
        return PlayerDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM users WHERE Premium IS NULL")) {
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
        boolean r = false;
        try (
                Connection conn = DatabaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT Username FROM users WHERE Username= ? AND Premium IS NULL;");
        ) {
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
        if (!(value instanceof Player)) return false;
        Player p = (Player) value;
        return p.equals(get(p.getUsername()));
    }

    @Override
    public Player get(Object key) {
        try (
                Connection conn = DatabaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT Username,Password FROM users WHERE Username= ? AND Premium IS NULL;");
        ) {
            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    return new Player(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Player put(String key, Player user) {
        try (
                Connection conn = DatabaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (Username,Password) VALUES (?,?);");
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getHashedPassword());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public Player put(Player u) {
        return put(u.getUsername(), u);
    }

    @Override
    public Player remove(Object key) {
        Player user = this.get(key);
        if (user == null)
            return null;
        try (
                Connection conn = DatabaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE Username = ? AND Premium IS NULL;");
        ) {
            ps.setString(1, key.toString());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Player> m) {
        try (Connection conn = DatabaseData.getConnection();) {
            conn.setAutoCommit(false);
            try (PreparedStatement stm = conn.prepareStatement("INSERT INTO users (Username,Password) VALUES (?,?);");) {
                for (Entry e : m.entrySet()) {
                    stm.setString(1, (String) e.getKey());
                    stm.setString(2, ((Player) e.getValue()).getHashedPassword());
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
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement();
        ) {
            stm.executeUpdate("DELETE FROM users WHERE Premium IS NULL;");
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO MUDAR ISTO
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r = new HashSet<String>();
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Username FROM users WHERE Premium IS NULL;");
        ){
            while (rs.next())
                r.add(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Player> values() {
        Collection<Player> r = new HashSet<Player>();
        try (Connection conn = DatabaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Username,Password FROM users WHERE Premium IS NULL;");
        ){
            while (rs.next())
                r.add(new Player(rs.getString(1), rs.getString(2)));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Player>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(User::getUsername, x -> x)).entrySet();
    }
}
