package server.player;

import server.database.databaseConnector;

import java.sql.*;

public class PlayerManager {
    private static final Connection conn = databaseConnector.connect();

    /**
     * Registers a player by creating a new player tuple within the player relation in the db
     * @param email
     * @param hashedPassword
     * @param nickname
     * @param bio
     * @param isOnline
     * @param currentGame
     * @param winLossRatio
     * @param username
     * @return
     */
    public static boolean registerPlayer(String email, String hashedPassword, String nickname,
                                         String bio, boolean isOnline, String currentGame,
                                         double winLossRatio, String username) {
        String query = "INSERT INTO profiles (email, hashed_password, nickname, bio, is_online, current_game, win_loss_ratio, username) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            statement.setString(3, nickname);
            statement.setString(4, bio);
            statement.setBoolean(5, isOnline);
            statement.setString(6, currentGame);
            statement.setDouble(7, winLossRatio);
            statement.setString(8, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting new player into database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Authenticates player by querying the player relation in the db
     * @param username
     * @param passwordHash
     * @return
     */
    public static boolean authenticatePlayer(String username, String passwordHash) {
        String query = "SELECT username, hashed_password FROM profiles WHERE username = ? AND hashed_password = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fetches playerID from the db using player username
     * @param username
     * @return
     */
    public static int getPlayerID(String username) {
        // retrieve playerID by username code
        return 0;
    }

    public static void main(String[] args) {
        registerPlayer("example4@gmail.com", "24389472873947", "cris", "dskdn", false, null,
                1.32, "evan2");
        boolean playerExists = authenticatePlayer("evan", "24389472873947");
        System.out.println(playerExists);
    }
}