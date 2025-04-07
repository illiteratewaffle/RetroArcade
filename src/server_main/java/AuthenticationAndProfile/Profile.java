package AuthenticationAndProfile;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;
import player.PlayerManager;
import static management.ServerLogger.log;

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
            PlayerManager.updateAttribute(id, "hashed_password", newHashedPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException n) {
            throw new NoSuchAlgorithmException(n.getMessage());
        }
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
        try {
            PlayerManager.updateAttribute(id, "nickname", newNickname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
     */
    public void getCurrentStatus() {
        if (isOnline) {
            if (currentGame != null) {
                log("Online - Currently playing: " + currentGame);
            } else {
                log("Online");
            }
        } else {
            log("Offline");
        }
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
        this.currentGame = currentGame;
        try {
            PlayerManager.updateAttribute(id, "current_game", currentGame);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
        try {
            PlayerManager.updateAttribute(id, "current_game", currentGame);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public void setProfilePicFilePath(String profilePicFilePath) {
        this.profilePicFilePath = profilePicFilePath;
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
        try {
            PlayerManager.updateAttribute(id, "username", newUsername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public PlayerRanking getPlayerRanking() {
        return playerRanking;
    }


    public static void main(String[] args) {
        //int id = PlayerManager.registerPlayer("jake2", "jake2@email.com", "1263876");
//        System.out.println(PlayerManager.updateAttribute(158,"wins_checkers","10"));
//        System.out.println(ProfileDatabaseAccess.obtainProfile(158).getPlayerRanking().getWins(PlayerRanking.CHECKERS_INDEX));
    }

    public int getID() {
        return id;
    }
}


// ChatGPT was used to generate the original code for the following 2 methods in ProfileCreationTest.java.
// The prompt used was: How can I make it so every time this test is run, a new username email and password are generated and it's id is used to identify it in the assertEquals statement
// ChatGPT suggested using UUID.randomUUID() to generate a unique identifier for the username, email, and password.
// UUID.randomUUID() generates a random universally unique identifier using a strong pseudo random number generator.
// The identifier is then converted to a string using .toString. This ensures every time this test is ran, a unique profile is created.
// The rest of the code in this test case (ProfileCreation.createNewProfile and following) was written without any AI assistance.
// To ensure this code is efficiently reusable, I've moved the generation of usernames and passwords to their own methods in the Profile class.
