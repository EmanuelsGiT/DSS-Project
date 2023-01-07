package src.DAOs;

import src.Models.Circuitos.Circuito;
import src.Models.Utilizadores.Registado;

import java.sql.*;
import java.util.*;

public class CircuitoDAO implements Map<String, Circuito> {
    private static CircuitoDAO singleton = null;

    private CircuitoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS circuitos (" +
                    "Nome VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "Distancia DOUBLE PRECISION NOT NULL," +
                    "NumeroChicanes INT NOT NULL," +
                    "NumeroVoltas INT NOT NULL);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS gdus (" +
                    "Nome VARCHAR(50) NOT NULL," +
                    "Tipo VARCHAR(10) NOT NULL," +
                    "Gdu INTEGER NOT NULL," +
                    "FOREIGN KEY (Nome) REFERENCES circuitos(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
    public static CircuitoDAO getInstance() {
        if (CircuitoDAO.singleton == null) {
            CircuitoDAO.singleton = new CircuitoDAO();
        }
        return CircuitoDAO.singleton;
    }
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM circuitos;")) {
            if (rs.next())
                i = rs.getInt(1);
        } catch (Exception e) {
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
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Circuito get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Nome, Distancia, NumeroChicanes, NumeroVoltas FROM circuitos WHERE Nome= ?;")) {
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    String nome = rs.getString("Nome");
                    Double distancia = rs.getDouble("Distancia");
                    int nChicanes = rs.getInt("NumeroChicanes");
                    int nVoltas = rs.getInt("NumeroVoltas");

                    ArrayList<Circuito.GDU> retasGDU = new ArrayList<>();
                    ArrayList<Circuito.GDU> curvasGDU = new ArrayList<>();

                    PreparedStatement nps = conn.prepareStatement("SELECT Tipo, Gdu FROM gdus WHERE Nome= ?;");
                    nps.setString(1, (String)key);
                    try (ResultSet nrs = nps.executeQuery()) {
                        while (nrs.next()) {
                            String tipo = nrs.getString("Tipo");
                            Circuito.GDU gdu = Circuito.GDU.fromInt(nrs.getInt("Gdu"));
                            if (tipo.equals("Curva"))
                                curvasGDU.add(gdu);
                            else if (tipo.equals("Reta"))
                                retasGDU.add(gdu);
                        }
                    }

                    int nCurvas = curvasGDU.size();
                    return new Circuito(nome, distancia, nVoltas, nChicanes, nCurvas, retasGDU, curvasGDU);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Circuito put(String key, Circuito value) {

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO circuitos (Nome,Distancia,NumeroChicanes,NumeroVoltas) VALUES (?,?,?,?);");){

            ps.setString(1, value.getNome());
            ps.setDouble(2, value.getDistancia());
            ps.setInt(3, value.getNChicanes());
            ps.setInt(4, value.getNVoltas());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Circuito.GDU gdu : value.getCurvasGDU()) {
            try (Connection conn = DataBaseData.getConnection();
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO gdus (Nome,Tipo,Gdu) VALUES (?,?,?);");){

                ps.setString(1, value.getNome());
                ps.setString(2, "Curva");
                ps.setInt(3, gdu.getGduValue());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Circuito.GDU gdu : value.getRetasGDU()) {
            try (Connection conn = DataBaseData.getConnection();
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO gdus (Nome,Tipo,Gdu) VALUES (?,?,?);");){

                ps.setString(1, value.getNome());
                ps.setString(2, "Reta");
                ps.setInt(3, gdu.getGduValue());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Circuito remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Circuito> m) {

    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM gdus;");
            stm.executeUpdate("DELETE FROM circuitos;");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Circuito> values() {
        Collection<Circuito> r = new HashSet<>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Nome FROM circuitos;");
        ){
            while(rs.next()) {
                Circuito c = this.get(rs.getString("Nome"));
                r.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        return null;
    }
}
