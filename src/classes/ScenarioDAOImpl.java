package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.*;

public class ScenarioDAOImpl implements ScenarioDAO {

    private static ScenarioDAOImpl instance = null;

    public static ScenarioDAOImpl getInstance() {
        if (instance == null) {
            instance = new ScenarioDAOImpl();
        }
        return instance;
    }

    @Override
    public void create(Scenario scenario) {
        AuditService.log("create", "scenario");
        String sql = "INSERT INTO scenario (name) VALUES (?)";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scenario.getClass().getSimpleName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Scenario read(int id) {
        AuditService.log("read", "scenario");
        String sql = "SELECT * FROM scenario WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // may want to use the name to determine the scenario index
                return new Scenario(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Scenario scenario) {
        AuditService.log("update", "scenario");
        String sql = "UPDATE scenario SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scenario.getClass().getSimpleName());
            stmt.setInt(2, 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        AuditService.log("delete", "scenario");
        String sql = "DELETE FROM scenario WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}