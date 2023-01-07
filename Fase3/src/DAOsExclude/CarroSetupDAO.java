package src.DAOsExclude;

import src.DAOs.DataBaseData;
import src.Models.Carros.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CarroSetupDAO implements Map<String, Carro> {
    private static CarroSetupDAO singleton = null;

    private CarroSetupDAO() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    //"ID INT NOT NULL," +
                    "Marca VARCHAR(25) NOT NULL," +
                    "Modelo VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "Cilindrada INT NOT NULL," +
                    "Potencia INT NOT NULL," +
                    "Fiabilidade DOUBLE NOT NULL," +
                    "Classe CHAR(2) NOT NULL);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CarroSetupDAO getInstance() {
        if (CarroSetupDAO.singleton == null) {
            CarroSetupDAO.singleton = new CarroSetupDAO();
        }
        return CarroSetupDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM carros")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
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
        boolean r = false;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Modelo FROM carros WHERE Modelo= ?;");
        ) {
            ps.setInt(1, (int) key);
            try (ResultSet rs = ps.executeQuery();) {
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
        if (!(value instanceof Carro)) return false;
        Carro p = (Carro) value;
        return p.equals(get(p.getModelo()));
    }

    @Override
    public Carro get(Object key) {
        Carro r = null;/*
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Marca,Modelo,Cilindrada,Potencia,Fiabilidade FROM carros WHERE Modelo= ?;");
        ) {
            ps.setString(1, key.toString());
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    String modelo = rs.getString("Modelo");
                    String marca = rs.getString("Marca");
                    int cilindrada = rs.getInt("Cilindrada");

                    Class<? extends CarClass> cc = (Class<? extends CarClass>) Class.forName(rs.getString("Class"));
                    CarClass c = (CarClass)cc.getMethod("getInstance").invoke(null, null);
                    Tyre tyre = new Tyre(Tyre.TyreType.valueOf(rs.getString("Tyre")));
                    BodyWork bodyWork = new BodyWork(BodyWork.DownforcePackage.valueOf(rs.getString("BodyWork")));
                    Engine.EngineMode eM = Engine.EngineMode.valueOf(rs.getString("EngineMode"));
                    CombustionEngine ce = new CombustionEngine(eM, rs.getInt("EngineCapacity"));
                    r = new Carro(id, c, tyre, ce, bodyWork);
                    Integer ePow = rs.getInt("EnginePower");
                    if (!rs.wasNull()) {
                        EletricEngine ee = new EletricEngine(eM, ePow);
                        r = new HybridRaceCar(r, ee);
                    }
                }
            //} catch (InstantiationException e) {
             //   throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        return r;
    }

    @Override
    public Carro put(String key, Carro car) {
        String sql = "";
        if (key == null) {
            sql = "INSERT INTO cars (Class,Tyre,BodyWork,EngineMode,EngineCapacity,EnginePower) VALUES (?,?,?,?,?,?);";
        } else {
            sql = "INSERT INTO cars (Id,Class,Tyre,BodyWork,EngineMode,EngineCapacity,EnginePower) VALUES (?,?,?,?,?,?,?);";
        }
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            int n = 1;
            if (key != null) {
                ps.setString(n, car.getModelo());
                n++;
            }

            ps.setString(n, car.getCategory().getClass().getName());
            n++;

            ps.setString(n, car.getTyres().getType().name());
            n++;
            ps.setString(n, car.getDfPackage().getDfPackage().name());
            n++;
            ps.setString(n, car.getCombustionEngine().getMode().name());
            n++;
            ps.setInt(n, car.getCombustionEngine().getCapacity());
            n++;
            if (car instanceof HybridRaceCar) {
                ps.setInt(n, ((HybridRaceCar) car).getEletricEngine().getPower());
            } else {
                ps.setNull(n, Types.INTEGER);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys();) {
                if (rs.next())
                    car.setModelo(rs.getInt(1));
            }
            return car;
        } catch (SQLException e) {
            return null;
        }
    }

    public Carro update(Integer key, Carro car) {
        String sql = "UPDATE cars SET " +
                "Class=?," +
                "Tyre=?," +
                "BodyWork=?," +
                "EngineMode=?," +
                "EngineCapacity=?," +
                "EnginePower=? " +
                "WHERE Id=?;";

        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, car.getCategory().getClass().getName());

            ps.setString(2, car.getTyres().getType().name());

            ps.setString(3, car.getDfPackage().getDfPackage().name());

            ps.setString(4, car.getCombustionEngine().getMode().name());

            ps.setInt(5, car.getCombustionEngine().getCapacity());

            if (car instanceof HybridRaceCar) {
                ps.setInt(6, ((HybridRaceCar) car).getEletricEngine().getPower());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setInt(7, key);
            ps.executeUpdate();
            return car;
        } catch (SQLException e) {
            return null;
        }
    }


    public Carro put(Carro u) {
        return put(u.getModelo(), u);
    }

    @Override
    public Carro remove(Object key) {
        Carro car = get(key);
        if (car == null)
            return null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM cars WHERE Id = ?;");
        ) {
            ps.setInt(1, (int) key);
            ps.executeUpdate();
            return car;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Carro> m) {
        return;
    }

    @Override
    public void clear() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
        ) {
            stm.executeUpdate("DELETE FROM cars;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> r = new HashSet<>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Modelo FROM cars;");
        ) {
            while (rs.next())
                r.add(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return r;
    }

    @Override
    public Collection<Carro> values() {
        Collection<Carro> r = new HashSet<Carro>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id,Class,Tyre,BodyWork,EngineMode,EngineCapacity,EnginePower FROM cars;");
        ) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                Class<? extends CarClass> c = (Class<? extends CarClass>) Class.forName(rs.getString("Class"));
                Tyre tyre = new Tyre(Tyre.TyreType.valueOf(rs.getString("Tyre")));
                BodyWork bodyWork = new BodyWork(BodyWork.DownforcePackage.valueOf(rs.getString("BodyWork")));
                Engine.EngineMode eM = Engine.EngineMode.valueOf(rs.getString("EngineMode"));
                CombustionEngine ce = new CombustionEngine(eM, rs.getInt("EngineCapacity"));
                Carro rt = new Carro(id, c.newInstance(), tyre, ce, bodyWork);
                Integer ePow = rs.getInt("EnginePower");
                if (!rs.wasNull()) {
                    EletricEngine ee = new EletricEngine(eM, ePow);
                    rt = new HybridRaceCar(rt, ee);
                }
                r.add(rt);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    @Override
    public Set<Entry<String, Carro>> entrySet() {
        return values().stream()
                       .collect(Collectors.toMap(Carro::getModelo, x -> x))
                       .entrySet();
    }


}
