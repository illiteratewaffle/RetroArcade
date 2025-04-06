package AuthenticationAndProfile;

import java.util.UUID;

//import leaderboard.TTTRanking;
//import leaderboard.CheckersRanking;
//import leaderboard.Connect4Ranking;
import leaderboard.PlayerRanking;
import player.PlayerManager;

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
    private static String username;
    private int id;

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame,
                   FriendsList friendsList, PlayerRanking playerRanking, GameHistory gameHistory, String profilePicFilePath,
                   String username, int id) {
        //public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String
        // currentGame, FriendsList friendsList, TTTRanking TTTRanking, Connect4Ranking connect4Ranking, CheckersRanking
        // checkersRanking, GameHistory gameHistory, String profilePicFilePath, String username, int id) {
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
     * @return the current email of the player.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a player's email.
     * @param newEmail the new email of the player.
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
        PlayerManager.updateAttribute(id, "email", newEmail);

    }

    /**
     * Gets the hashed password of a player's account.
     * @return the player's hashed password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets a player's hashed password. Takes the inputted password, hashed it, then saves to profile.
     * @param newPassword the new password of the player.
     */
    public void setHashedPassword(String newPassword) {
        String newHashedPassword = ProfileCreation.hashedPassword(newPassword);
        this.hashedPassword = newHashedPassword;
        PlayerManager.updateAttribute(id, "hashed_password", newHashedPassword);

    }

    /**
     * Gets the player's nickname.
     * @return the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets a player's nickname.
     * @param newNickname the new nickname of the player.
     */
    public void setNickname(String newNickname) {
        this.nickname = newNickname;
        PlayerManager.updateAttribute(id, "nickname", newNickname);
    }

    /**
     * Gets the player's bio.
     * @return the player's bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets a player's bio.
     * @param newBio the new bio of the player.
     */
    public void setBio(String newBio) {
        this.bio = newBio;
        PlayerManager.updateAttribute(id, "bio", newBio);
    }

    /**
     * Sets a player's online status.
     * @param online the new online status of the player.
     */
    public void setOnlineStatus(boolean online) {
        isOnline = online;
        if (online) {
            PlayerManager.updateAttribute(this.id, Integer.toString(ProfileCSVReader.ONLINE_INDEX), "true");
        }else {
            PlayerManager.updateAttribute(this.id, Integer.toString(ProfileCSVReader.ONLINE_INDEX), "false");
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
                System.out.println("Online - Currently playing: " + currentGame);
            } else {
                System.out.println("Online");
            }
        } else {
            System.out.println("Offline");
        }
    }

    /**
     * Gets the player's current game.
     * @return the player's current game.
     */
    public String getCurrentGame() {
        return currentGame;
    }

    /**
     * Sets a player's current game.
     * @param currentGame the current game of the player.
     */
    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
        PlayerManager.updateAttribute(id, "current_game", currentGame);
    }

    /**
     * Gets the player's played games.
     * @return the player's played games.
     */
    public GameHistory getGameHistory() {
        return gameHistory;
    }

    /**
     * Sets the player's played games.
     * @param gameHistory the new played games of the player.
     */
    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }

    /**
     * Gets the player's profile picture.
     * @return the player's profile picture.
     */
    public String getProfilePicFilePath() {
        return profilePicFilePath;
    }

    /**
     * Sets the player's profile picture.
     * @param profilePicFilePath the new profile picture of the player.
     */
    public void setProfilePicFilePath(String profilePicFilePath) {
        this.profilePicFilePath = profilePicFilePath;
    }

    /**
     * Gets the player's username.
     * @return the player's username.
     */
    public static String exportUsername(int id) {
        return PlayerManager.getUsername(id);
    }

    public String getUsername() {
        return username;
    }

    /**
     * Sets a player's username.
     * @param newUsername the new username of the player.
     */
    public void updateUsername(int id, String newUsername) {
        this.username = newUsername;
        PlayerManager.updateAttribute(id, "username", newUsername);

    }

    // ChatGPT was used to generate the original code for the following 2 methods in ProfileCreationTest.java.
    // The prompt used was: How can I make it so every time this test is run, a new username email and password are generated and it's id is used to identify it in the assertEquals statement
    // ChatGPT suggested using UUID.randomUUID() to generate a unique identifier for the username, email, and password.
    // UUID.randomUUID() generates a random universally unique identifier using a strong pseudo random number generator.
    // The identifier is then converted to a string using .toString. This ensures every time this test is ran, a unique profile is created.
    // The rest of the code in this test case (ProfileCreation.createNewProfile and following) was written without any AI assistance.
    // To ensure this code is efficiently reusable, I've moved the generation of usernames and passwords to their own methods in the Profile class.

    /**
     * Generates a unique username.
     * @return a unique username.
     */
    public static String generateUsername() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        return "User_" + uniqueSuffix;
    }

    /**
     * Generates a unique password.
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

    //    public TTTRanking getTTTRanking() {
//        return TTTRanking;
//    }
//
//    public Connect4Ranking getConnect4Ranking() {
//        return connect4Ranking;
//    }
//
//    public CheckersRanking getCheckersRanking() {
//        return checkersRanking;
//    }
//
//    public void setTTTRanking(TTTRanking TTTRanking) {
//        this.TTTRanking = TTTRanking;
//    }
//
//    public void setConnect4Ranking(Connect4Ranking connect4Ranking) {
//        this.connect4Ranking = connect4Ranking;
//    }
//
//    public void setCheckersRanking(CheckersRanking checkersRanking) {
//        this.checkersRanking = checkersRanking;
//    }

    public static void main(String[] args) {
        //int id = PlayerManager.registerPlayer("jake2", "jake2@email.com", "1263876");
//        System.out.println(PlayerManager.updateAttribute(158,"wins_checkers","10"));
//        System.out.println(ProfileDatabaseAccess.obtainProfile(158).getPlayerRanking().getWins(PlayerRanking.CHECKERS_INDEX));
    }

    public int getID() {
        return id;
    }
}