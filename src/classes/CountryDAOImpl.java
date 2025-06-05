package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl implements CountryDAO {
    private static CountryDAOImpl instance = null;

    public static CountryDAOImpl getInstance() {
        if (instance == null) {
            instance = new CountryDAOImpl();
        }
        return instance;
    }

    @Override
    public void create(Country country) {
        AuditService.log("create", "country");
        String sql = "INSERT INTO country (name, player_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, country.getName());
            if (country.getPlayerId() != null) {
                stmt.setInt(2, country.getPlayerId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Country read(int id) {
        AuditService.log("read", "country");
        String sql = "SELECT * FROM country WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                Integer playerId = rs.getObject("player_id") != null ? rs.getInt("player_id") : null;
                return new Country(id, name, playerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Country> readAll() {
        AuditService.log("readAll", "country");
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM country";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Integer playerId = rs.getObject("player_id") != null ? rs.getInt("player_id") : null;
                countries.add(new Country(id, name, playerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public void update(Country country) {
        AuditService.log("update", "country");
        String sql = "UPDATE country SET name = ?, player_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, country.getName());
            if (country.getPlayerId() != null) {
                stmt.setInt(2, country.getPlayerId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, country.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerCountry(int playerId, String name) {
        AuditService.log("updatePlayer", "country");
        String sql = "UPDATE country SET player_id = ? WHERE name = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        AuditService.log("delete", "country");
        String sql = "DELETE FROM country WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}