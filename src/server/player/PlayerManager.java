package server.player;

import server.database.databaseConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class PlayerManager {
    private static final Connection conn = databaseConnector.connect();

    /**
     * Registers a player by creating a new player tuple within the player relation in the db
     * @param email Profile's associated email. Unique value enforced for email.
     * @param hashedPassword SHA-256 encrypted password.
     * @param nickname Profile's username.
     * @param bio Profile's bio.
     * @param isOnline Determines whether a profile is currently online. Stored as a boolean value.
     * @param currentGame
     * @param winLossRatio Profile's win/loss ratio.
     * @param username Profile's username. Unique value enforced for username.
     * @return Returns player registration success or error message.
     */
    public static String registerPlayer(String email, String hashedPassword, String nickname,
                                        String bio, boolean isOnline, String currentGame,
                                        double winLossRatio, String username) {

        // Prepare SQL query for storing profile into db. PreparedStatement is used to prevent SQL injection
        String query = "INSERT INTO profiles (email, hashed_password, nickname, bio, is_online, current_game, " +
                "win_loss_ratio, username) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Open connection to database, set profile attributes and store into SQL table.
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            statement.setString(3, nickname);
            statement.setString(4, bio);
            statement.setBoolean(5, isOnline);
            statement.setString(6, currentGame);
            statement.setDouble(7, winLossRatio);
            statement.setString(8, username);

            // For logging purposes
            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            // Error message notifying user of issue.
            System.err.println("Error inserting new player into database: " + e.getMessage());
        }
        return "Player Registered Successfully!";
    }

    /**
     * Authenticates player by querying the player relation in the db.
     * @param username References database using username for authentication.
     * @param passwordHash Checks username contains matching passwordHash in database.
     * @return true if player credentials match, false otherwise.
     */
    public static boolean authenticatePlayer(String username, String passwordHash) {
        // Prepare SQL query for storing profile into db. PreparedStatement is used to prevent SQL injection
        String query = "SELECT username, hashed_password FROM profiles WHERE username = ? AND hashed_password = ?";

        // Open connection to database, set reference username and passwordHash, and execute SQL query
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet rs = statement.executeQuery();
            // If a row is returned matching player credentials, returns true
            return rs.next();
        } catch (SQLException e) {
            // If there was an error authenticating a player, user is notified
            System.err.println("Authentication failed: " + e.getMessage());
            // Returns false
            return false;
        }
    }

    /**
     * Fetches all profile data from the database and writes it into a CSV file.
     */
    public static void getProfile() {
        // SQL query to fetch all rows in the profiles table
        String query = "SELECT * FROM profiles";
        // Keeps track of the number of rows exported
        int rowCount = 0;
        // File name for exported CSV file
        String csvFile = "profiles_export.csv";

        try (
                // Prepare statement to prevent SQL injection
                PreparedStatement statement = conn.prepareStatement(query);
                // Execute query and store the result set in rs
                ResultSet rs = statement.executeQuery();
                // Create a FileWriter object to write CSV file
                FileWriter writer = new FileWriter(csvFile)
        ) {
            // Get column information for headers
            ResultSetMetaData metaData = rs.getMetaData();
            // Count the number of columns in the table
            int columnCount = metaData.getColumnCount();

            // Write table headers in the CSV
            for (int i = 1; i <= columnCount; i++) {
                writer.append(metaData.getColumnName(i));
                if (i < columnCount) writer.append(",");
            }
            writer.append("\n");

            // Write table rows in the CSV
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(rs.getString(i));
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");
                rowCount++;
            }

            System.out.println("Exported " + rowCount + " profile(s) to " + csvFile);
        } catch (SQLException | IOException e) {
            // Exception error messages in case of SQL or file I/O issues
            System.err.println("Error exporting profile data: " + e.getMessage());
        }
    }
}