package AuthenticationAndProfile;

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
    private long id;

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame, FriendsList friendsList, PlayerRanking playerRanking, GameHistory gamesPlayed, BufferedImage profilePic, String username, long id) {
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
     * Profile constructor.
     * @param email the email of the player.
     * @param username the username of the player.
     * @param hashedPassword the hashed password of the player.
     */
    public Profile(String email, String username, String hashedPassword){
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets a player's email.
     * @param id the id of the player.
     * @return the current email of the player.
     */
    public String getEmail(long id) {
        if (id == this.id) {
            return email;
        }
        return null;
    }

    /**
     * Sets a player's email.
     * @param id the id of the player.
     * @param email the new email of the player.
     */
    public void setEmail(long id, String email) {
        if (id == this.id) {
            this.email = email;
        }
    }

    /**
     * Gets the hashed password of a player's account.
     * @return the player's hashed password.
     */
    public String getHashedPassword(long id) {
        if (id == this.id) {
            return hashedPassword;
        }
        return null;
    }

    /**
     * Sets a player's hashedPassword.
     * @param id the id of the player.
     * @param hashedPassword the new hashed password of the player.
     */
    public void setHashedPassword(long id, String hashedPassword) {
        if (id == this.id) {
            this.hashedPassword = hashedPassword;
        }
    }

    /**
     * Gets the player's nickname.
     * @param id the id of the player.
     * @return the player's nickname.
     */
    public String getNickname(long id) {
        if (id == this.id) {
            return nickname;
        }
        return null;
    }

    /**
     * Sets a player's nickname.
     * @param id the id of the player.
     * @param nickname the new nickname of the player.
     */
    public void setNickname(long id, String nickname) {
        if (id == this.id) {
            this.nickname = nickname;
        }
    }

    /**
     * Gets the player's bio.
     * @param id the id of the player.
     * @return the player's bio.
     */
    public String getBio(long id) {
        if (id == this.id) {
            return bio;
        }
        return null;
    }

    /**
     * Sets a player's bio.
     * @param id the id of the player.
     * @param bio the new bio of the player.
     */
    public void setBio(long id, String bio) {
        if (id == this.id) {
            this.bio = bio;
        }
    }

    /**
     * Gets the player's online status.
     * @param id the id of the player.
     * @return the player's online status.
     */
    public boolean isOnline(long id) {
        if (id == this.id) {
            return isOnline;
        }
    }

    /**
     * Sets a player's online status.
     * @param id the id of the player.
     * @param online the new online status of the player.
     */
    public void setOnline(long id, boolean online) {
        if (id == this.id) {
            isOnline = online;
        }
    }

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

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }

    public GameHistory getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(GameHistory gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public BufferedImage getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(BufferedImage profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
