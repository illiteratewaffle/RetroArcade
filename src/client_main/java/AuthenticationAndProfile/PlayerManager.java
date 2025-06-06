package AuthenticationAndProfile;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static AuthenticationAndProfile.ServerLogger.log;

public class PlayerManager {
    private static final Connection conn = databaseConnector.connect();

    /**
     * Registers a new player with the provided username, email, and hashed password.
     * Returns the newly created player's ID on success. Optional profile attributes
     * like nickname or bio can be set later.
     *
     * @param email Profile's associated email. Must be unique.
     * @param hashedPassword SHA-256 hashed password.
     * @param username Unique username of the player.
     * @return The newly created player ID if registration is successful, -1 otherwise.
     * @throws SQLException
     */
    public static int registerPlayer(String username, String email, String hashedPassword) throws SQLException {

        // Prepare SQL query for storing profile into db. PreparedStatement is used to prevent SQL injection
        String query = "INSERT INTO profiles (username, email, hashed_Password) VALUES (?, ?, ?) RETURNING id";

        // Open connection to database, set profile attributes and store into SQL table.
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, hashedPassword);

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
            // Possibly do some logging
            throw new SQLException("Failed to register player: " + e.getMessage(), e);
        }
    }

    /**
     * Authenticates player by querying the player relation in the db.
     *
     * @param username References database using username for authentication.
     * @param passwordHash Checks username contains matching passwordHash in database.
     * @return True if player credentials match, false otherwise.
     * @throws SQLException if a database access error occurs during the authentication process. To be handled by caller.
     */
    public static int authenticatePlayer(String username, String passwordHash) throws SQLException {
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
                log("Authentication successful. Player ID: " + playerId);
                return playerId;
            } else {
                log("Authentication failed: Invalid username or password.");
                return -1;
            }
        } catch (SQLException e) {
            // If there was an error authenticating a player, user is notified
            System.err.println("Authentication failed: " + e.getMessage());
            // Returns -1
            throw new SQLException("Failed to authenticate player: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a player's profile by their ID and writes the data to a CSV file.
     * The CSV includes column headers and the full row of player information.
     *
     * @param id ID of the player whose profile is to be exported.
     * @throws SQLException SQLException if there is an error accessing the database or writing to the file. To be
     * handled by caller.
     */
    public static void getProfile(int id) throws SQLException {
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
                    String value = rs.getString(i);
                    String columnType = metaData.getColumnTypeName(i);

                    // Wrap hstore values in curly braces
                    if ("hstore".equalsIgnoreCase(columnType) && value != null) {
                        value = "{" + value + "}";
                    }

                    writer.append(value != null ? value : "");
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");

                log("Player data written to: " + fileName);
            } else {
                log("No player found with ID: " + id);
            }
        } catch (SQLException | IOException e) {
            throw new SQLException("Error retrieving or writing player data: " + e.getMessage(), e);
        }
    }

    /**
     * Fetches all profile data from the database and writes it into a CSV file.
     */
    public static void getProfileTable() throws SQLException {
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
                    String value = rs.getString(i);
                    String columnType = metaData.getColumnTypeName(i);

                    // Wrap hstore values in curly braces
                    if ("hstore".equalsIgnoreCase(columnType) && value != null) {
                        value = "{" + value + "}";
                    }

                    writer.append(value != null ? value : "");
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");
                rowCount++;
            }

            log("Exported " + rowCount + " profile(s) to " + csvFile);
        } catch (SQLException | IOException e) {
            // Exception error messages in case of SQL or file I/O issues
            throw new SQLException("Error exporting profile data: " + e.getMessage());
        }
    }

    /**
     * Searches the friends list of a player for usernames containing the provided substring.
     *
     * @param id ID of the player whose friends list is being searched.
     * @param nameFragment Substring to match against friend usernames.
     * @return List of friend IDs whose usernames contain the substring.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static List<Integer> searchFriendsList(int id, String nameFragment) throws SQLException {
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
            throw new SQLException("Error searching friends by substring: " + e.getMessage());
        }
        // Return the friend's list that match the substring passed through
        return matchingIds;
    }

    /**
     * Searches the profile table by substring. Returns profiles with usernames that contain substring arg.
     * @param nameFragment Substring to match against friend usernames.
     * @return List of profile IDs whose usernames contain the substring.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static List<Integer> searchProfiles(String nameFragment) throws SQLException {
        // SQL query that searches profiles table based on profile usernames that match the submitted string parameter.
        String query = """
                SELECT id
                FROM profiles
                WHERE username ILIKE ?""";

        // List containing profile Ids
        List<Integer> matchingIds = new ArrayList<>();

        // Prepare SQL query by setting search parameters and then execute
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            // % signify wildcards so it searches any string containing the substring nameFragment
            statement.setString(1, "%" + nameFragment + "%");
            // Store the resulting table in rs
            ResultSet rs = statement.executeQuery();
            // rs.next moves cursor to next row in table and stores the id into matchingIds list
            while (rs.next()) {
                matchingIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new SQLException("Error profiles by substring: " + e.getMessage());
        }
        // Return the profile's list that match the substring passed through
        return matchingIds;
    }

    /**
     * Retrieves the username associated with the given player ID.
     *
     * @param id ID of the player.
     * @return The username if found, or null if no matching player is found.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String getUsername(int id) throws SQLException {
        String query = "SELECT username FROM profiles WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            } else {
                log("No user found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error searching username: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a profile row from the profiles table in the database.
     *
     * @param id ID of the profile to be deleted.
     * @return Status message.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String deleteProfile(int id) throws SQLException {
        String query = "DELETE FROM profiles WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Player with ID " + id + " deleted successfully.";
            } else {
                return "No player found with ID " + id + ".";
            }
        } catch (SQLException e) {
            throw new SQLException("Error deleting profile: " + e.getMessage(), e);
        }
    }

    /**
     * Update the attribute of a specified profile attribute - constrained to INTS, DOUBLES, TEXT, and BOOLEAN data types.
     *
     * @param id ID of the player.
     * @param attrColumn Column to fetch data from.
     * @param newValue New value to update attribute with.
     * @return Status message.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String updateAttribute(int id, String attrColumn, Object newValue) throws SQLException {
        String query = "UPDATE profiles SET " + attrColumn + " = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setObject(1, newValue);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Attribute '" + attrColumn + "' updated for Player " + id;
            } else {
                return "No player found with ID: " + id;
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating player attribute: " + e.getMessage(), e);
        }
    }

    /**
     * Fetch data from a specified profile ID and attribute column.
     *
     * @param id ID of the player.
     * @param attrColumn Column to fetch data from.
     * @return Value stored in the cell fetching data from.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String getAttribute(int id, String attrColumn) throws SQLException {
        String query = "SELECT " + attrColumn + " FROM profiles WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(attrColumn);
            } else {
                System.err.println("No player found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving attribute '" + attrColumn + "' for player ID " + id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Add new friend profile ID to a player's friend list.
     *
     * @param id ID of the player.
     * @param newFriendId ID of the newly added friend/
     * @return Status message.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String addToFriendsList(int id, int newFriendId) throws SQLException {
        String query = """
                            UPDATE profiles
                            SET friends = CASE
                            WHEN array_position(friends, ?) IS NOT NULL
                            THEN friends
                            ELSE array_append(friends, ?)
                            END
                            WHERE id = ?
                """;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, newFriendId);
            statement.setInt(2, newFriendId);
            statement.setInt(3, id);


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "added player " + newFriendId + " to player " + id + " Friend's list";
            } else {
                return "No player found with ID: " + id;
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating 'friends' array: " + e.getMessage(), e);
        }
    }

    /**
     * Add a friend request profile id to a player's friend request list.
     *
     * @param id ID of the player.
     * @param newFriendId ID of the requesting friend.
     * @return Status message.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String addToFriendRequests(int id, int newFriendId) throws SQLException {
        String query = """
                            UPDATE profiles
                            SET friend_requests = CASE
                            WHEN array_position(friend_requests, ?) IS NOT NULL
                            THEN friend_requests
                            ELSE array_append(friend_requests, ?)
                            END
                            WHERE id = ?
                """;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, newFriendId);
            statement.setInt(2, newFriendId);
            statement.setInt(3, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "added player " + newFriendId + " to player " + id + " Friend Request list";
            } else {
                return "No player found with ID: " + id;
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating friend_requests array: " + e.getMessage(), e);
        }
    }

    /**
     * Fetch a profile's ID using their username as a search constraint.
     *
     * @param username Username of the profile to search for.
     * @return ID of the profile searched for.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static int getProfileID(String username) throws SQLException {
        String query = "SELECT id FROM profiles WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            // If we get a row, return its "id"
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Error retrieving profile ID");
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving profile ID: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a friend from player's friend list.
     *
     * @param playerId ID of the player.
     * @param friendId ID of the friend to be removed.
     * @return Status message.
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String deleteFriend(int playerId, int friendId) throws SQLException {
        // array_remove will remove 'friendId' from the 'friend_requests' INT[] column for the row matching userId
        String query = "UPDATE profiles SET friends = array_remove(friends, ?) WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Bind parameters
            statement.setInt(1, friendId);
            statement.setInt(2, playerId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Successfully removed friendId from friend_requests
                return "Friend with ID " + friendId + " removed for user " + playerId;
            } else {
                throw new SQLException("No player found with ID: " + playerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Error deleting Friend: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a friend request from a player's friend request list.
     *
     * @param playerId ID of the player.
     * @param friendId ID of the friend request.
     * @return Status message
     * @throws SQLException SQLException if a database access error occurs or the query fails. To be handled by caller.
     */
    public static String deleteFriendRequest(int playerId, int friendId) throws SQLException {
        // array_remove will remove 'friendId' from the 'friend_requests' INT[] column for the row matching userId
        String query = "UPDATE profiles SET friend_requests = array_remove(friend_requests, ?) WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Bind parameters
            statement.setInt(1, friendId);
            statement.setInt(2, playerId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Successfully removed friendId from friend_requests
                return "Friend request from ID " + friendId + " removed for user " + playerId;
            } else {
                throw new SQLException("No player found with ID: " + playerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Error deleting friend request: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a game to the list of games played by a player.
     *
     * @param id ID of the player.
     * @param gamePlayed Name of the game to add.
     * @return Status message.
     * @throws SQLException if a database access error occurs during the update. To be handled by caller.
     */
    public static String addToGamesPlayed(int id, String gamePlayed) throws SQLException {
        String query = """
                            UPDATE profiles
                            SET games_played = CASE
                            WHEN array_position(games_played, ?) IS NOT NULL
                            THEN games_played
                            ELSE array_append(games_played, ?)
                            END
                            WHERE id = ?
                """;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, gamePlayed);
            statement.setString(2, gamePlayed);
            statement.setInt(3, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "added game " + gamePlayed + " to player " + id + " games played list";
            } else {
                return "No player found with ID: " + id;
            }
        } catch (SQLException e) {
            throw new SQLException("Error adding to games played: " + e.getMessage(), e);
        }
    }

    /**
     * Adds or updates a player's progress toward a named achievement. Stores unique keys only.
     *
     * @param id ID of the player.
     * @param achievementName Name of the achievement. Must be a string.
     * @param progress Progress value to set. Must be a string.
     * @return Status message.
     * @throws SQLException if a database access error occurs during the update. To be handled by caller.
     */
    public static String setAchievementProgress(int id, String achievementName, String progress) throws SQLException {
        String query = """
                     UPDATE profiles
                     SET achievement_progress = achievement_progress || hstore(?,?)
                     WHERE id = ?
                """;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, achievementName);
            statement.setString(2, progress);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Set achievement '" + achievementName + " for player " + id;
            } else {
                return "No player found with ID: " + id;
            }
        } catch (SQLException e) {
            throw new SQLException("Error setting achievement progress: " + e.getMessage(), e);
        }
    }
}