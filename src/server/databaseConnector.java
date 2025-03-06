package server;
import java.sql.*;

public class databaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/omg_game";
    private static final String USER = "postgres";
    private static final String PASSWORD = "omg_game_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
