package src.DAOs;

import src.Models.Utilizadores.Anonimo;
import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class AnonimoDAO implements Map<String, Anonimo> {
    private static AnonimoDAO singleton = null;

    private AnonimoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS anonimos (" +
                    "Nome VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Nome) REFERENCES jogadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE)";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static AnonimoDAO getInstance() {
        if (AnonimoDAO.singleton == null) {
            AnonimoDAO.singleton = new AnonimoDAO();
        }
        return AnonimoDAO.singleton;
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
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Anonimo get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Nome FROM anonimos WHERE Nome= ?;");){
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next())
                    return new Anonimo(rs.getString("Nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Anonimo put(String key, Anonimo value) {

        UtilizadorDAO utilizadorDAO = UtilizadorDAO.getInstance();
        utilizadorDAO.put(value.getNome(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO anonimos (Nome) VALUES (?);");){

            ps.setString(1, value.getNome());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Anonimo remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Anonimo> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Anonimo> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Anonimo>> entrySet() {
        return null;
    }
}
