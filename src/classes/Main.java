package classes;

import config.DatabaseConfiguration;
import services.AuditService;

import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        runInitialSqlFile(connection, "src/config/diagram.sql");

        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.run();
        DatabaseConfiguration.closeDatabaseConnection();
    }

    private static void runInitialSqlFile(Connection connection, String filePath) {
        AuditService.log("initialize", "game");
        try {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] statements = sql.split(";");
            Statement stmt = connection.createStatement();
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}