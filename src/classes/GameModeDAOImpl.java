package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameModeDAOImpl implements GameModeDAO {
    private static GameModeDAOImpl instance = null;

    public static GameModeDAOImpl getInstance() {
        if (instance == null) {
            instance = new GameModeDAOImpl();
        }
        return instance;
    }

    @Override
    public void create(GameMode gameMode) {
        AuditService.log("create", "gamemode");
        String sql = "INSERT INTO gamemode (name) VALUES (?)";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameMode.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameMode read(int id) {
        AuditService.log("read", "gamemode");
        String sql = "SELECT * FROM gamemode WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return new GameMode(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GameMode> readAll() {
        AuditService.log("readAll", "gamemode");
        List<GameMode> gameModes = new ArrayList<>();
        String sql = "SELECT * FROM gamemode";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                gameModes.add(new GameMode(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameModes;
    }

    @Override
    public void update(GameMode gameMode) {
        AuditService.log("update", "gamemode");
        String sql = "UPDATE gamemode SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameMode.getName());
            stmt.setInt(2, gameMode.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        AuditService.log("delete", "gamemode");
        String sql = "DELETE FROM gamemode WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}