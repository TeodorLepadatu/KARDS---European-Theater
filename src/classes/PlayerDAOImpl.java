package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.*;

public class PlayerDAOImpl implements PlayerDAO {

    private static PlayerDAOImpl instance = null;

    public static PlayerDAOImpl getInstance() {
        if (instance == null) {
            instance = new PlayerDAOImpl();
        }
        return instance;
    }

    @Override
    public void create(GenericPlayer player) {
        AuditService.log("create", "player");
        String sql = "INSERT INTO player (name, type) VALUES (?, ?)";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.name);
            stmt.setString(2, player instanceof AIPlayer ? "AI" : "HUMAN");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GenericPlayer read(int id) {
        AuditService.log("read", "player");
        String sql = "SELECT * FROM player WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                // may want to load the hand/cards separately
                if ("AI".equals(type)) {
                    return AIPlayer.getInstance(name, new java.util.HashMap<>(), 1, 1);
                } else {
                    return HumanPlayer.getInstance(name, new java.util.HashMap<>(), 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(GenericPlayer player) {
        AuditService.log("update", "player");
        String sql = "UPDATE player SET name = ?, type = ? WHERE name = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.name);
            stmt.setString(2, player instanceof AIPlayer ? "AI" : "HUMAN");
            stmt.setString(3, player.name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGameMode(int playerId, int gameModeIndex) {
        AuditService.log("updateGamemode", "player");
        String sql = "UPDATE player SET gamemode_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameModeIndex);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        AuditService.log("delete", "player");
        String sql = "DELETE FROM player WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
