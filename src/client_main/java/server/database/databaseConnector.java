package server.database;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.io.IOException;
import java.util.Properties;

public class databaseConnector {
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";
    private static Connection connection = null;

    /**
     * Loads configuration file containing database admin details for connection to the database
     * @param filename
     */
    public static void loadConfiguration(String filename) {
        try (InputStream input = databaseConnector.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new FileNotFoundException("Database configuration file not found in classpath: " + filename);
            }
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            // TODO: could be logged?
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
                loadConfiguration("db-config-test.properties");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                // TODO: log?
                System.out.println("Connected to the database.");
            } catch (SQLException e) {
                // TODO: log?
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
                // TODO: log?
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                // TODO: log?
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
