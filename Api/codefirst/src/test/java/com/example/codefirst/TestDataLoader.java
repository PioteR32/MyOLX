package com.example.codefirst;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TestDataLoader {

    public static void main(String[] args) {
        // Using H2 database for testing.
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL";
        String user = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            
            System.out.println("Connected to database: " + url);
            
            // Use current working directory to find files
            File sqlDir = new File("Api/codefirst/src/test/sql");
            if (!sqlDir.exists()) {
                System.err.println("SQL directory not found: " + sqlDir.getAbsolutePath());
                return;
            }

            File[] sqlFiles = sqlDir.listFiles((dir, name) -> name.endsWith(".sql"));
            if (sqlFiles == null || sqlFiles.length == 0) {
                System.out.println("No SQL files found in " + sqlDir.getAbsolutePath());
                return;
            }

            // Sort files to ensure consistent order (e.g. towns before users)
            java.util.Arrays.sort(sqlFiles, java.util.Comparator.comparing(File::getName));

            for (File file : sqlFiles) {
                System.out.println("Executing: " + file.getName());
                executeSqlFile(statement, file);
            }

            System.out.println("Successfully executed all SQL files.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeSqlFile(Statement statement, File file) throws SQLException, IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("--") || trimmedLine.startsWith("//")) {
                continue;
            }
            
            String[] statements = trimmedLine.split(";");
            for (String statementText : statements) {
                String finalStatement = statementText.trim();
                if (!finalStatement.isEmpty()) {
                    try {
                        statement.execute(finalStatement);
                    } catch (SQLException e) {
                        System.err.println("Error in " + file.getName() + " at statement: " + finalStatement);
                        throw e;
                    }
                }
            }
        }
    }
}