package AuthenticationAndProfile;

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
     * Sets a player's online status.
     * @param id the id of the player.
     * @param online the new online status of the player.
     */
    public void setOnlineStatus(long id, boolean online) {
            isOnline = online;
            // push update to database
        }
    }

    /**
     * Check if a player is in a game. If so, prints out the game they're playing (likely usable by GUI team).
     * If not, prints out "Online" if the player is online. Otherwise, prints out "Offline".
     * @param id the id of the player.
     * @return the player's current game.
     */
    public void getCurrentStatus(long id) {
        if (id == this.id) {
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
    }

    `/**
     * Gets the player's current game.
     * @param id the id of the player.
     * @return the player's current game.
     */
    public String getCurrentGame(long id) {
        if (id == this.id) {
            return currentGame;
        }
    }

    /**
     * Sets a player's current game.
     * @param id the id of the player.
     * @param currentGame the current game of the player.
     */
    public void setCurrentGame(String currentGame) {
        if (id == this.id) {
            this.currentGame = currentGame;
        }
    }

    /**
     * Gets the player's played games.
     * @param id the id of the player.
     * @return the player's played games.
     */
    public GameHistory getGamesPlayed(long id) {
        if (id == this.id) {
            return gamesPlayed;
        }
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

    /**
     * Gets the player's username.
     * @param id the id of the player.
     * @return the player's username.
     */
    public String getUsername(long id) {
        if (id == this.id) {
            return username;
        }
    }

    /**
     * Sets a player's username.
     * @param id the id of the player.
     * @param username the new username of the player.
     */
    public void updateUsername(long id, String newUsername) {

            this.username = username; // locally change the username
            // call method to update username in database
        }
    }


