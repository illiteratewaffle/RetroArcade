package server.player;

import server.database.databaseConnector;

import javax.xml.transform.Result;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private static final Connection conn = databaseConnector.connect();

    /**
     * Registers a new player with only the required information.
     * Optional fields like nickname, bio, current game, and stats can be updated later.
     *
     * @param email Profile's associated email. Must be unique.
     * @param hashedPassword SHA-256 hashed password.
     * @param username Unique username of the player.
     * @return Confirmation message on success or error message on failure.
     */
    public static int registerPlayer(String email, String hashedPassword, String username) {

        // Prepare SQL query for storing profile into db. PreparedStatement is used to prevent SQL injection
        String query = "INSERT INTO profiles (email, hashed_password, username) VALUES (?, ?, ?) RETURNING id";

        // Open connection to database, set profile attributes and store into SQL table.
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, email);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                int newPlayerID = rs.getInt("id");
                System.out.println("Player " + newPlayerID + " Registered Successfully!");
                return newPlayerID;
            } else {
                System.err.println("Registration failed: No ID returned.");
                return -1;
            }

        } catch (SQLException e) {
            // Error message notifying user of issue.
            System.err.println("Error inserting new player into database: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Authenticates player by querying the player relation in the db.
     * @param username References database using username for authentication.
     * @param passwordHash Checks username contains matching passwordHash in database.
     * @return true if player credentials match, false otherwise.
     */
    public static int authenticatePlayer(String username, String passwordHash) {
        // Prepare SQL query for storing profile into db. PreparedStatement is used to prevent SQL injection
        String query = "SELECT id FROM profiles WHERE username = ? AND hashed_password = ?";

        // Open connection to database, set reference username and passwordHash, and execute SQL query
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet rs = statement.executeQuery();
            // If a row is returned matching player credentials, returns player ID
            if (rs.next()) {
                int playerId = rs.getInt("id");
                System.out.println("Authentication successful. Player ID: " + playerId);
                return playerId;
            } else {
                System.out.println("Authentication failed: Invalid username or password.");
                return -1;
            }
        } catch (SQLException e) {
            // If there was an error authenticating a player, user is notified
            System.err.println("Authentication failed: " + e.getMessage());
            // Returns -1
            return -1;
        }
    }

    public static void getProfileTable(int id) {
        String query = "SELECT * FROM profiles WHERE id = ?";
        String fileName = "player_profile_" + id + ".csv";

        try (PreparedStatement statement = conn.prepareStatement(query);
             FileWriter writer = new FileWriter(fileName)
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Write header
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(metaData.getColumnName(i));
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");

                // Write player row
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(rs.getString(i));
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");

                System.out.println("Player data written to: " + fileName);
            } else {
                System.out.println("No player found with ID: " + id);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error retrieving or writing player data: " + e.getMessage());
        }
    }

    /**
     * Fetches all profile data from the database and writes it into a CSV file.
     */
    public static void getProfileTable() {
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

    public static List<Integer> searchFriendsList(int id, String nameFragment) {
        // SQL query that searches friends based on friend usernames that match the submitted string parameter
        String query = """
                SELECT id
                FROM profiles
                WHERE id = ANY (
                SELECT jsonb_array_elements_text(friends)::INT
                FROM profiles WHERE id = ?)
                AND username ILIKE ?""";

        // List containing friend Ids
        List<Integer> matchingIds = new ArrayList<>();

        // Prepare SQL query by setting search parameters and then execute
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            // % signify wildcards so it searches any string containing the substring nameFragment
            statement.setString(2, "%" + nameFragment + "%");
            // Store the resulting table in rs
            ResultSet rs = statement.executeQuery();
            // rs.next moves cursor to next row in table and stores the id into matchingIds list
            while (rs.next()) {
                matchingIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Error searching friends by substring: " + e.getMessage());
        }
        // Return the friend's list that match the substring passed through
        return matchingIds;
    }

    public static String getUsername(int id) {
        String query = "SELECT username FROM profiles WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            } else {
                System.out.println("No user found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error searching username: " + e.getMessage());
            return null;
        }
    }

    // TODO: create methods to retrieve columns of profile

    public static void main(String[] args) {
        registerPlayer("ertgdsg", "tqwe3tdfgs", "k23452345tq34");
    }
}