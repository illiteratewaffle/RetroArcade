package AuthenticationAndProfile;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;
import static AuthenticationAndProfile.ServerLogger.log;

public class Profile {
    private String email;
    private String hashedPassword;
    private String nickname;
    private String bio;
    private boolean isOnline;
    private String currentGame;
    private PlayerRanking playerRanking;
    private FriendsList friendsList;
    private GameHistory gameHistory;
    private String profilePicFilePath;
    private String username;
    private int id;

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame,
                   FriendsList friendsList, PlayerRanking playerRanking, GameHistory gameHistory, String profilePicFilePath,
                   String username, int id) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.bio = bio;
        this.isOnline = isOnline;
        this.currentGame = currentGame;
        this.playerRanking = playerRanking;
        this.friendsList = friendsList;
        this.gameHistory = gameHistory;
        this.profilePicFilePath = profilePicFilePath;
        this.username = username;
        this.id = id;
    }

    /**
     * Gets a player's email.
     *
     * @return the current email of the player.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a player's email.
     *
     * @param newEmail the new email of the player.
     */
    public void setEmail(String newEmail) throws SQLException {
        this.email = newEmail;
        try {
            PlayerManager.updateAttribute(id, "email", newEmail);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Gets the hashed password of a player's account.
     *
     * @return the player's hashed password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets a player's hashed password. Takes the inputted password, hashed it, then saves to profile.
     *
     * @param newPassword the new password of the player.
     */
    public void setHashedPassword(String newPassword) throws SQLException, NoSuchAlgorithmException {
        try {
            String newHashedPassword = ProfileCreation.hashedPassword(newPassword);
            this.hashedPassword = newHashedPassword;
            PlayerManager.updateAttribute(id, "hashed_password", newHashedPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException n) {
            throw new NoSuchAlgorithmException(n.getMessage());
        }
    }

    /**
     * Gets the player's ID.
     *
     * @return the player's ID.
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the player's nickname.
     *
     * @return the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets a player's nickname.
     *
     * @param newNickname the new nickname of the player.
     */
    public void setNickname(String newNickname) throws SQLException {
        this.nickname = newNickname;
        try {
            PlayerManager.updateAttribute(id, "nickname", newNickname);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Gets the player's bio.
     *
     * @return the player's bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets a player's bio.
     *
     * @param newBio the new bio of the player.
     */
    public void setBio(String newBio) throws SQLException {
        this.bio = newBio;
        try {
            PlayerManager.updateAttribute(id, "bio", newBio);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
        try {
            PlayerManager.updateAttribute(id, "bio", newBio);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets a player's online status.
     *
     * @param online the new online status of the player.
     */
    public void setOnlineStatus(boolean online) throws SQLException {
        isOnline = online;
        try {
            if (online) {
                PlayerManager.updateAttribute(this.id, "is_online", true);
            } else {
                PlayerManager.updateAttribute(this.id, "is_online", false);
            }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    public boolean getOnlineStatus() {
        return isOnline;
    }

    /**
     * Check if a player is in a game. If so, prints out the game they're playing (likely usable by GUI team).
     * If not, prints out "Online" if the player is online. Otherwise, prints out "Offline".
     *
     * @return the current status of the player.
     */
    public String getCurrentStatus() {
        String currentStatus;
        if (isOnline) {
            if (currentGame == null) {
                currentStatus = "Online";
            } else {
                currentStatus = String.format("%s", currentGame);
            }
        } else {
            currentStatus = "Offline";
        }
        return currentStatus;
    }

    /**
     * Gets the player's current game.
     *
     * @return the player's current game.
     */
    public String getCurrentGame() {
        return currentGame;
    }

    /**
     * Sets a player's current game.
     *
     * @param currentGame the current game of the player.
     */
    public void setCurrentGame(String currentGame) throws SQLException {
        try {
            PlayerManager.updateAttribute(id, "current_game", currentGame);
            this.currentGame = currentGame;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Gets the player's played games.
     *
     * @return the player's played games.
     */
    public GameHistory getGameHistory() {
        return gameHistory;
    }

    /**
     * Sets the player's played games.
     *
     * @param gameHistory the new played games of the player.
     */
    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }

    /**
     * Gets the player's profile picture.
     *
     * @return the player's profile picture.
     */
    public String getProfilePicFilePath() {
        return profilePicFilePath;
    }

    /**
     * Sets the player's profile picture.
     *
     * @param profilePicFilePath the new profile picture of the player.
     */
    public void setProfilePicFilePath(String profilePicFilePath) throws SQLException{
        try {
            PlayerManager.updateAttribute(id, "profile_pic_path", profilePicFilePath);
            this.profilePicFilePath = profilePicFilePath;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Gets the player's username.
     *
     * @return the player's username.
     */
    public static String exportUsername(int id) throws SQLException {
        try {
            return PlayerManager.getUsername(id);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Gets the player's username.
     *
     * @return the player's username.
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets a player's username.
     *
     * @param newUsername the new username of the player.
     */
    public void updateUsername(int id, String newUsername) throws SQLException {
        this.username = newUsername;
        try {
            PlayerManager.updateAttribute(id, "username", newUsername);
        } catch (SQLException s) {
            throw new RuntimeException(s.getMessage());
        }
    }

    // ChatGPT was used to generate the original code for the following 2 methods in ProfileCreationTest.java.
    // The prompt used was: How can I make it so every time this test is run, a new username email and password are generated and it's id is used to identify it in the assertEquals statement
    // ChatGPT suggested using UUID.randomUUID() to generate a unique identifier for the username, email, and password.
    // UUID.randomUUID() generates a random universally unique identifier using a strong pseudo random number generator.
    // The identifier is then converted to a string using .toString. This ensures every time this test is ran, a unique profile is created.
    // To ensure this code is efficiently reusable, I've moved the generation of usernames and passwords to their own methods in the Profile class.

    /**
     * Generates a unique username.
     *
     * @return a unique username.
     */
    public static String generateUsername() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        return "User_" + uniqueSuffix;
    }

    /**
     * Generates a unique password.
     *
     * @return a unique password.
     */
    public static String generatePassword() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String password = "Pass_" + uniqueSuffix;
        return password;
    }

    /**
     * Gets the player's friends and friend requests as a FriendsList object.
     *
     * @return a FriendsList object.
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * Sets the player's friends and friend requests through a FriendsList object.
     *
     * @param friendsList the new friends/friend requests of the player.
     */
    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    /**
     * Gets the player's ranking.
     *
     * @return the player's ranking.
     */
    public PlayerRanking getPlayerRanking() {
        return playerRanking;
    }
}
