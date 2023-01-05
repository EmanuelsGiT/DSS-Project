package DAOs;

import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RegistadoDAO implements Map<String, Registado> {
    private static RegistadoDAO singleton = null;

    private RegistadoDAO() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "Username VARCHAR(255) NOT NULL PRIMARY KEY," +
                    "Password CHAR(60) NOT NULL)," +
                    "Pontuacao INT NOT NULL);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RegistadoDAO getInstance() {
        if (RegistadoDAO.singleton == null) {
            RegistadoDAO.singleton = new RegistadoDAO();
        }
        return RegistadoDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM users")) {
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
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT Username FROM users WHERE Username= ?;");
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
        if (!(value instanceof Registado)) return false;
        Registado p = (Registado) value;
        return p.equals(get(p.getNome()));
    }

    @Override
    public Registado get(Object key) {
        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT Username,Password, Pontuacao FROM users WHERE Username= ?;");
        ) {
            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    return new Registado(rs.getString(1), rs.getString(2), Integer.rs.getString(3)); //passar para int (mas ja e int na BD)
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Registado put(String key, Registado user) {
        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (Username,Password,Pontuacao) VALUES (?,?,?);");
        ) {
            ps.setString(1, user.getNome());
            ps.setString(2, user.getPass());
            ps.setString(3, user.getPontuacaoTotal());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public Registado put(Registado u) {
        return put(u.getNome(), u);
    }

    @Override
    public Registado remove(Object key) {
        Registado user = this.get(key);
        if (user == null)
            return null;
        try (
                Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE Username = ?;");
        ) {
            ps.setString(1, key.toString());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Registado> m) {
        try (Connection conn = DataBaseData.getConnection();) {
            conn.setAutoCommit(false);
            try (PreparedStatement stm = conn.prepareStatement("INSERT INTO users (Username,Password,Pontuacao) VALUES (?,?,?);");) {
                for (Entry e : m.entrySet()) {
                    stm.setString(1, (String) e.getKey());
                    stm.setString(2, ((Registado) e.getValue()).getPass());
                    stm.setString(3, (String) Integer.e.getValue()).getPontuacaoTotal();
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
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
        ) {
            stm.executeUpdate("DELETE FROM users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r = new HashSet<String>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Username FROM users;");
        ){
            while (rs.next())
                r.add(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Collection<Registado> values() {
        Collection<Registado> r = new HashSet<Registado>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Username,Password,Pontuacao FROM users;");
        ){
            while (rs.next())
                r.add(new Registado(rs.getString(1), rs.getString(2), Integer.(rs.getString(3))));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Registado>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(Registado::getNome, x -> x)).entrySet();
    }
}
