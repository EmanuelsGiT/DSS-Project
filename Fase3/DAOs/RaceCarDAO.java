package DAOs;

import Models.cars.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RaceCarDAO implements Map<Integer, CombustionRaceCar> {
    private static RaceCarDAO singleton = null;

    private RaceCarDAO() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS cars (" +
                    "Id INT AUTO_INCREMENT PRIMARY KEY," +
                    "Class CHAR(33) NOT NULL," +
                    "Tyre VARCHAR(12) NOT NULL," +
                    "BodyWork VARCHAR(6) NOT NULL," +
                    "EngineMode VARCHAR(6) NOT NULL," +
                    "EngineCapacity INT NOT NULL," +
                    "EnginePower INT);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RaceCarDAO getInstance() {
        if (RaceCarDAO.singleton == null) {
            RaceCarDAO.singleton = new RaceCarDAO();
        }
        return RaceCarDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM cars")) {
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
             PreparedStatement ps = conn.prepareStatement("SELECT Id FROM cars WHERE Id= ?;");
        ) {
            ps.setInt(1, (int) key);
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
        if (!(value instanceof CombustionRaceCar)) return false;
        CombustionRaceCar p = (CombustionRaceCar) value;
        return p.equals(get(p.getId()));
    }

    @Override
    public CombustionRaceCar get(Object key) {
        CombustionRaceCar r = null;
        try (Connection conn = DataBaseData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Id,Class,Tyre,BodyWork,EngineMode,EngineCapacity,EnginePower FROM cars WHERE Id= ?;");
        ) {
            ps.setInt(1, (Integer) key);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("Id");
                    Class<? extends CarClass> cc = (Class<? extends CarClass>) Class.forName(rs.getString("Class"));
                    CarClass c = (CarClass)cc.getMethod("getInstance").invoke(null, null);
                    Tyre tyre = new Tyre(Tyre.TyreType.valueOf(rs.getString("Tyre")));
                    BodyWork bodyWork = new BodyWork(BodyWork.DownforcePackage.valueOf(rs.getString("BodyWork")));
                    Engine.EngineMode eM = Engine.EngineMode.valueOf(rs.getString("EngineMode"));
                    CombustionEngine ce = new CombustionEngine(eM, rs.getInt("EngineCapacity"));
                    r = new CombustionRaceCar(id, c, tyre, ce, bodyWork);
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
        }
        return r;
    }

    @Override
    public CombustionRaceCar put(Integer key, CombustionRaceCar car) {
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
                ps.setInt(n, car.getId());
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
                    car.setId(rs.getInt(1));
            }
            return car;
        } catch (SQLException e) {
            return null;
        }
    }

    public CombustionRaceCar update(Integer key, CombustionRaceCar car) {
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


    public CombustionRaceCar put(CombustionRaceCar u) {
        return put(u.getId(), u);
    }

    @Override
    public CombustionRaceCar remove(Object key) {
        CombustionRaceCar car = get(key);
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
    public void putAll(Map<? extends Integer, ? extends CombustionRaceCar> m) {
        return;
    }

    @Override
    public void clear() {
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
        ) {
            stm.executeUpdate("DELETE FROM cars;");
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO MUDAR ISTO
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> r = new HashSet<>();
        try (Connection conn = DataBaseData.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM cars;");
        ) {
            while (rs.next())
                r.add(rs.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return r;
    }

    @Override
    public Collection<CombustionRaceCar> values() {
        Collection<CombustionRaceCar> r = new HashSet<CombustionRaceCar>();
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
                CombustionRaceCar rt = new CombustionRaceCar(id, c.newInstance(), tyre, ce, bodyWork);
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
    public Set<Entry<Integer, CombustionRaceCar>> entrySet() {
        return values().stream().collect(
                Collectors.toMap(CombustionRaceCar::getId, x -> x)).entrySet();
    }


}