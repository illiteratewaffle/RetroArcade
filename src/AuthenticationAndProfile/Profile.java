package AuthenticationAndProfile;

import java.awt.image.BufferedImage;
import leaderboard.PlayerRanking;
import server.player.PlayerManager;

import static server.player.PlayerManager.getUsername;

public class Profile {
    private String email;
    private String hashedPassword;
    private String nickname;
    private String bio;
    private boolean isOnline;
    private String currentGame;
    private PlayerRanking playerRanking;
    private FriendsList friendsList;
    private GameHistory gamesPlayed;
    private BufferedImage profilePic;
    private String username;
    private int id;

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame, FriendsList friendsList, PlayerRanking playerRanking, GameHistory gamesPlayed, BufferedImage profilePic, String username, int id) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.bio = bio;
        this.isOnline = isOnline;
        this.currentGame = currentGame;
        this.playerRanking = playerRanking;
        this.friendsList = friendsList;
        this.gamesPlayed = gamesPlayed;
        this.profilePic = profilePic;
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
    public GameHistory getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Sets the player's played games.
     * @param gamesPlayed the new played games of the player.
     */
    public void setGamesPlayed(GameHistory gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Gets the player's profile picture.
     * @return the player's profile picture.
     */
    public BufferedImage getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the player's profile picture.
     * @param profilePic the new profile picture of the player.
     */
    public void setProfilePic(BufferedImage profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Gets the player's username.
     * @return the player's username.
     */
    public static String exportUsername(int id) {
        return getUsername(7);
    }

    /**
     * Sets a player's username.
     * @param newUsername the new username of the player.
     */
    public void updateUsername(String newUsername) {

    }

    public static void main(String[] args) {
        getUsername(7);
    }
}