package server;

import java.sql.*;

public class PlayerManager {
    private static final Connection conn = DatabaseConnector.connect();

    public static boolean registerPlayer(String username, String passwordHash) {
        // register player code
    }

    public static boolean authenticatePlayer(String username, String passwordHash) {
        // Authenticate player code
    }

    public static int getPlayerID(String username) {
        // retrieve playerID by username code
    }

}