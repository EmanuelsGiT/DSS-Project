package src.DAOs;

import src.Models.Utilizadores.Jogador;
import src.Models.Utilizadores.Registado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class JogadorDAO implements Map<String, Jogador> {

    private static JogadorDAO singleton = null;

    private JogadorDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS jogadores (" +
                    "Nome VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Nome) REFERENCES utilizadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static JogadorDAO getInstance() {
        if (JogadorDAO.singleton == null) {
            JogadorDAO.singleton = new JogadorDAO();
        }
        return JogadorDAO.singleton;
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
    public Jogador get(Object key) {
        return null;
    }

    public Jogador put(String key, Jogador value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO jogadores (Nome) VALUES (?);");) {

                 ps.setString(1, value.getNome());

                 ps.executeUpdate();

                 return value;
        } catch (SQLException e) {
                 e.printStackTrace();
        }
        return null;
    }

    @Override
    public Jogador remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Jogador> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Jogador> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Jogador>> entrySet() {
        return null;
    }
}
