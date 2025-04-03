package server_main.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import server.player.PlayerManager;

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

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame, FriendsList friendsList, PlayerRanking playerRanking, GameHistory gameHistory, String profilePicFilePath, String username, int id) {
        //public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame, FriendsList friendsList, TTTRanking TTTRanking, Connect4Ranking connect4Ranking, CheckersRanking checkersRanking, GameHistory gameHistory, String profilePicFilePath, String username, int id) {
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
     * @param email the new email of the player.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the hashed password of a player's account.
     * @return the player's hashed password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets a player's hashed password.
     * @param hashedPassword the new hashed password of the player.
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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
     * @param nickname the new nickname of the player.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * @param bio the new bio of the player.
     */
    public void setBio(String bio) {
        this.bio = bio;
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
        return PlayerManager.getUsername(7);
    }

    public static String getUsername() {
        return username;
    }

    /**
     * Sets a player's username.
     * @param newUsername the new username of the player.
     */
    public void updateUsername(String newUsername) {

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
        PlayerManager.getUsername(7);
    }
}