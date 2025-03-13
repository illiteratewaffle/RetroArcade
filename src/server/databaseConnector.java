package server;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class databaseConnector {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final Connection connection;

    // Loads database credentials from properties file
    static {
        try (FileInputStream fis = new FileInputStream("db-config.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
        }
    }

    /**
     * Establishes a connection to the postgres db. IF a connection already exists, it returns the existing connection.
     * @return
     */
    public static Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database.");
            } catch (SQLException e) {
                System.err.println("Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Closes the active database connection. If no connection exists, this method does nothing.
     */
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
