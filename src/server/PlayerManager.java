package server;

import java.sql.*;

public class PlayerManager {
    private static final Connection conn = DatabaseConnector.connect();

    /**
     * Registers a player by creating a new player tuple within the player relation in the db
     * @param username
     * @param passwordHash
     * @return
     */
    public static boolean registerPlayer(String username, String passwordHash) {
        // register player code
    }

    /**
     * Authenticates player by querying the player relation in the db
     * @param username
     * @param passwordHash
     * @return
     */
    public static boolean authenticatePlayer(String username, String passwordHash) {
        // Authenticate player code
    }

    /**
     * Fetches playerID from the db using player username
     * @param username
     * @return
     */
    public static int getPlayerID(String username) {
        // retrieve playerID by username code
    }

}