package src.DAOs;

import src.Models.Campeonatos.Campeonato;
import src.Models.Campeonatos.CarroSetup;
import src.Models.Campeonatos.Corrida;
import src.Models.Campeonatos.Registo;
import src.Models.Circuitos.Circuito;
import src.Models.Pilotos.Piloto;
import src.Models.Utilizadores.Jogador;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class CampeonatoDAO implements Map<String, Campeonato> {
    private static CampeonatoDAO singleton = null;

    private CampeonatoDAO() {
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS campeonatos (" +
                    "Nome VARCHAR(50) NOT NULL PRIMARY KEY);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS corridas (" +
                    "Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "Nome_Campeonato VARCHAR(50) NOT NULL," +
                    "Nome_Circuito VARCHAR(50) NOT NULL," +
                    "Clima VARCHAR(10) NOT NULL," +
                    "FOREIGN KEY (Nome_Campeonato) REFERENCES campeonatos(Nome) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Circuito) REFERENCES circuitos(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS jogadoresProntos(" +
                    "Id_Corrida INT NOT NULL," +
                    "Nome_Jogador VARCHAR(50) NOT NULL," +
                    "Pronto BOOLEAN NOT NULL," +
                    "FOREIGN KEY (Id_Corrida) REFERENCES corridas(Id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Jogador) REFERENCES jogadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";

            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS resultados(" +
                    "Id_Corrida INT NOT NULL," +
                    "Pontos INT NOT NULL," +
                    "Nome_Jogador VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Id_Corrida) REFERENCES corridas(Id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Jogador) REFERENCES jogadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS registos(" +
                    "Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "NumAfinacoes INT NOT NULL," +
                    "Nome_Piloto VARCHAR(50) NOT NULL," +
                    "Nome_Jogador VARCHAR(50) NOT NULL," +
                    "Nome_Campeonato VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Nome_Piloto) REFERENCES pilotos(Nome) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Jogador) REFERENCES jogadores(Nome) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Nome_Campeonato) REFERENCES campeonatos(Nome) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS carrosSetup(" +
                    "Id_Registo INT NOT NULL," +
                    "Pneus VARCHAR(10) NULL," +
                    "ModoMotor VARCHAR(25) NULL," +
                    "Pac INT NOT NULL," +
                    "Modelo_Carro VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (Modelo_Carro) REFERENCES carros(Modelo) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (Id_Registo) REFERENCES registos(Id) ON DELETE CASCADE ON UPDATE CASCADE);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CampeonatoDAO getInstance() {
        if (CampeonatoDAO.singleton == null) {
            CampeonatoDAO.singleton = new CampeonatoDAO();
        }
        return CampeonatoDAO.singleton;
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
    public Campeonato get(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Nome FROM campeonatos WHERE Nome= ?;")) {
            ps.setString(1,(String)key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    String nome = rs.getString("Nome");

                    ArrayList<Corrida> corridas = new ArrayList<>();

                    PreparedStatement nps = conn.prepareStatement("SELECT Id, Clima, Nome_Circuito FROM corridas WHERE Nome_Campeonato= ?;");
                    nps.setString(1, (String)key);
                    try (ResultSet nrs = nps.executeQuery()) {
                        while (nrs.next()) {
                            Circuito c = CircuitoDAO.getInstance().get(nrs.getString("Nome_Circuito"));
                            int id = nrs.getInt("Id");
                            String strClima = nrs.getString("Clima");
                            Corrida.Clima clima;
                            if (strClima.equals("SECO"))
                                clima = Corrida.Clima.SECO;
                            else
                                clima = Corrida.Clima.MOLHADO;

                            HashMap<String, Integer> resultados = this.getResultados(id);
                            HashMap<String, Boolean> jogadoresProntos = this.getJogadoresProntos(id);
                            Corrida corrida = new Corrida(id, c, clima, resultados, jogadoresProntos);
                            corridas.add(corrida);
                        }
                    }
                    return new Campeonato(nome, corridas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    public Registo getRegisto(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Id,NumAfinacoes,Nome_Piloto,Nome_Jogador,Id_CarroSetup FROM registos WHERE Id= ?;")) {
            ps.setInt(1, (Integer) key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    int id = rs.getInt("Id");
                    int numAfinacoes = rs.getInt("NumAfincoes");
                    Piloto piloto = PilotoDAO.getInstance().get(rs.getString("Nome_Piloto"));
                    Jogador jogador = JogadorDAO.getInstance().get("Nome_Jogador");
                    CarroSetup carroSetup = this.getCarroSetup("ID_CarroSetup");
                    return new Registo(id, numAfinacoes, piloto, jogador, carroSetup);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    public CarroSetup getCarroSetup(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Id,Pneus,ModoMotor,Pac,Modelo_Carro FROM carrosSetup WHERE Id= ?;")) {
            ps.setInt(1, (Integer) key);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    int id = rs.getInt("Id");
                    String strPeneus = rs.getString("Pneus");
                    CarroSetup.Pneus pneus;
                    if (strPeneus.equals("MACIO"))
                        pneus = CarroSetup.Pneus.MACIO;
                    else if (strPeneus.equals("DURO"))
                        pneus = CarroSetup.Pneus.DURO;
                    else if (strPeneus.equals("CHUVA"))
                        pneus = CarroSetup.Pneus.CHUVA;
                    else
                        pneus = null;

                    String strModoMotor = rs.getString("ModoMotor");
                    CarroSetup.ModoMotor modoMotor;
                    if (strModoMotor.equals("CONSERVADOR"))
                        modoMotor = CarroSetup.ModoMotor.CONSERVADOR;
                    else if (strModoMotor.equals("NORMAL"))
                        modoMotor = CarroSetup.ModoMotor.NORMAL;
                    else if (strModoMotor.equals("AGRESSIVO"))
                        modoMotor = CarroSetup.ModoMotor.AGRESSIVO;
                    else
                        modoMotor = null;

                    double pac = rs.getDouble("Pac");
                    String modelo = rs.getString("Modelo_Carro");
                    return new CarroSetup(id, pac, pneus, modoMotor, CarroDAO.getInstance().get(modelo));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return  null;
    }

    public HashMap<String, Integer> getResultados(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Id_Corrida,Nome_Jogador,Pontos FROM resultados WHERE Id_Corrida= ?;")) {
            ps.setInt(1, (Integer) key);
            try (ResultSet rs = ps.executeQuery();){
                HashMap<String, Integer> resultados = new HashMap<>();
                while (rs.next()) {
                    String nome = rs.getString("Nome_Jogador");
                    int pontos = rs.getInt("Pontos");
                    resultados.put(nome, pontos);
                }
                return resultados;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public HashMap<String, Boolean> getJogadoresProntos(Object key) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT Nome_Jogador,Pronto FROM jogadoresProntos WHERE Id_Corrida= ?;")) {
            ps.setInt(1,(Integer) key);
            try (ResultSet rs = ps.executeQuery();){
                HashMap<String, Boolean> jogadoresProntos = new HashMap<>();
                while (rs.next()) {
                    String nome = rs.getString("Nome_Jogador");
                    boolean pronto = rs.getBoolean("Pronto");
                    jogadoresProntos.put(nome, pronto);
                }
                return jogadoresProntos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Campeonato put(String key, Campeonato value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO campeonatos (Nome) VALUES (?);")){

            ps.setString(1, value.getNome());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Corrida c : value.getCorridas()) {
            try (Connection conn = DataBaseData.getConnection();
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO corridas (Nome_Campeonato,Nome_Circuito,Clima) VALUES (?,?,?);",
                         Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, value.getNome());
                ps.setString(2, c.getCircuito().getNome());
                Corrida.Clima clima = c.getClima();
                if (clima == Corrida.Clima.MOLHADO)
                    ps.setString(3, "MOLHADO");
                else if (clima == Corrida.Clima.SECO)
                    ps.setString(3, "SECO");

                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    c.setId(generatedKeys.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Entry<String, Integer> resultado : c.getResultados().entrySet()) {
                try (Connection conn = DataBaseData.getConnection();
                     PreparedStatement ps = conn.prepareStatement("INSERT INTO resultados (Id_Corrida,Pontos,Nome_Jogador) VALUES (?,?,?);")) {

                    ps.setInt(1, c.getId());
                    ps.setInt(2, resultado.getValue());
                    ps.setString(3, resultado.getKey());

                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            for (Entry<String, Boolean> jogadorPronto : c.getJogadoresProntos().entrySet()) {
                try (Connection conn = DataBaseData.getConnection();
                     PreparedStatement ps = conn.prepareStatement("INSERT INTO jogadoresProntos (Id_Corrida,Nome_Jogador,Pronto) VALUES (?,?,?);")) {

                    ps.setInt(1, c.getId());
                    ps.setString(2, jogadorPronto.getKey());
                    ps.setBoolean(3, jogadorPronto.getValue());

                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }
    public void putRegisto(String key, Registo value) {
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO registos (NumAfinacoes,Nome_Piloto,Nome_Jogador,Nome_Campeonato) VALUES (?,?,?,?);",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, value.getNumAfinacoes());
            ps.setString(2, value.getPiloto().getNome());
            ps.setString(3, value.getJogador().getNome());
            ps.setString(4, key);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                putCarroSetup(id, value.getCarroSetup());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int putCarroSetup(int key, CarroSetup value) {
            try (Connection conn = DataBaseData.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO carrosSetup (Id_Registo,Pneus,ModoMotor,Pac,Modelo_Carro) VALUES (?,?,?,?,?);",
                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, key);

            if (value.getPneus() == CarroSetup.Pneus.MACIO)
                ps.setString(2, "MACIO");
            else if (value.getPneus() == CarroSetup.Pneus.DURO)
                ps.setString(2, "DURO");
            else if (value.getPneus() == CarroSetup.Pneus.CHUVA)
                ps.setString(2, "CHUVA");
            else
                ps.setNull(2, Types.VARCHAR);

            if (value.getModoMotor() == CarroSetup.ModoMotor.AGRESSIVO)
                ps.setString(3, "AGRESSIVO");
            else if (value.getModoMotor() == CarroSetup.ModoMotor.CONSERVADOR)
                ps.setString(3, "CONSERVADOR");
            else if (value.getModoMotor() == CarroSetup.ModoMotor.NORMAL)
                ps.setString(3, "NORMAL");
            else
                ps.setNull(3, Types.VARCHAR);

            ps.setDouble(4, value.getPac());
            ps.setString(5, value.getCarro().getModelo());

            ps.executeUpdate();
            return 1;


        } catch (SQLException e) {
            e.printStackTrace();
        }
            return  -1;
    }

    @Override
    public Campeonato remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {

    }

    @Override
    public void clear() {
        try ( Connection conn = DataBaseData.getConnection();
              Statement stm = conn.createStatement();){
            stm.executeUpdate("DELETE FROM carrosSetup;");
            stm.executeUpdate("DELETE FROM registos;");
            stm.executeUpdate("DELETE FROM jogadoresProntos;");
            stm.executeUpdate("DELETE FROM resultados;");
            stm.executeUpdate("DELETE FROM corridas;");
            stm.executeUpdate("DELETE FROM campeonatos;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Campeonato> values() {
        Collection<Campeonato> r = new HashSet<>();
        try (
                Connection conn = DataBaseData.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Nome FROM campeonatos;");
        ){
            while(rs.next()) {
                Campeonato c = this.get(rs.getString("Nome"));
                r.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        return null;
    }
}
