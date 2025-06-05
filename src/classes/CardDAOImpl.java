package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.*;
import java.util.ArrayList;

public class CardDAOImpl implements CardDAO {

    private static CardDAOImpl instance = null;

    private CardDAOImpl() {}

    public static CardDAOImpl getInstance() {
        if (instance == null) {
            instance = new CardDAOImpl();
        }
        return instance;
    }

    @Override
    public void create(Card card, int countryId) {
        AuditService.log("create", "card");
        String sql = "INSERT INTO card (name, hp, type, power, country_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.name);
            stmt.setInt(2, card.hp);

            if (card instanceof AttackingCard ac) {
                stmt.setString(3, "ATTACK");
                stmt.setInt(4, ac.getAttackPower());
            } else if (card instanceof HealingCard hc) {
                stmt.setString(3, "HEAL");
                stmt.setInt(4, hc.getHealPower());
            } else {
                stmt.setString(3, "ATTACK");
                stmt.setInt(4, 0);
            }

            stmt.setInt(5, countryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card read(int id) {
        AuditService.log("read", "card");
        String sql = "SELECT * FROM card WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int hp = rs.getInt("hp");
                String type = rs.getString("type");
                int power = rs.getInt("power");

                if ("ATTACK".equals(type)) {
                    return new AttackingCard(name, hp, power);
                } else {
                    return new HealingCard(name, hp, power);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Card> readAllByCountryId(int countryId) {
        AuditService.log("readAll", "card");
        ArrayList<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM card WHERE country_id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int hp = rs.getInt("hp");
                String type = rs.getString("type");
                int power = rs.getInt("power");
                Card card;
                if ("ATTACK".equals(type)) {
                    card = new AttackingCard(name, hp, power);
                } else {
                    card = new HealingCard(name, hp, power);
                }
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void update(Card card, int countryId) {
        AuditService.log("update", "card");
        String sql = "UPDATE card SET name = ?, hp = ?, type = ?, power = ?, country_id = ? WHERE name = ? AND country_id = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.name);
            stmt.setInt(2, card.hp);
            if (card instanceof AttackingCard ac) {
                stmt.setString(3, "ATTACK");
                stmt.setInt(4, ac.getAttackPower());
            } else if (card instanceof HealingCard hc) {
                stmt.setString(3, "HEAL");
                stmt.setInt(4, hc.getHealPower());
            } else {
                stmt.setString(3, "ATTACK");
                stmt.setInt(4, 0);
            }
            stmt.setInt(5, countryId);
            stmt.setString(6, card.name);
            stmt.setInt(7, countryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHealth(String name, int newHp) {
        AuditService.log("updateHealth", "card");
        String sql = "UPDATE card SET hp = ? WHERE name = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newHp);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        AuditService.log("delete", "card");
        String sql = "DELETE FROM card WHERE name = ?";
        try (Connection conn = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
