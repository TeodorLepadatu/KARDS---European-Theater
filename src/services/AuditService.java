package services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String AUDIT_FILE = "audit.csv";
    private static boolean appending = false;
    private static void logAction(String action) {
        try (FileWriter writer = new FileWriter(AUDIT_FILE, appending)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            writer.append(action).append(",").append(timestamp).append("\n");
        } catch (IOException e) {
            System.err.println("Failed to write to audit file: " + e.getMessage());
        }
    }

    public static void log(String action, String entity) {
        logAction(action + "_" + entity);
        if(!appending) {
            appending = true;
        }
    }
}
