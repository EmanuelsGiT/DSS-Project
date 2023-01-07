package src.DAOs;

import src.Models.Campeonatos.Registo;
import src.Models.Utilizadores.Jogador;
import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.*;

public class RegistadoDAO implements Map<String, Registado> {
    private static RegistadoDAO singleton = null;

    private RegistadoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS registados (" +
                    "Nome VARCHAR(50) NOT NULL," +
                    "PalavraPasse VARCHAR(50) NOT NULL," +
                    "PontuacaoTotal INT NOT NULL DEFAULT 0," +
                    "FOREIGN KEY (Nome) REFERENCES jogadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
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
    public Registado get(Object key) {
        return null;
    }

    @Override
    public Registado put(String key, Registado value) {

        UtilizadorDAO utilizadorDAO = UtilizadorDAO.getInstance();
        utilizadorDAO.put(value.getNome(), value);

        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        jogadorDAO.put(value.getNome(), value);

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO registados (Nome,PalavraPasse,PontuacaoTotal) VALUES (?,?,?);");){

            ps.setString(1, value.getNome());
            ps.setString(2, value.getPass());
            ps.setInt(3, value.getPontuacaoTotal());

            ps.executeUpdate();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Registado remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Registado> m) {
    }

    @Override
    public void clear() {    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Registado> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Registado>> entrySet() {
        return null;
    }

}
