package AuthenticationAndProfile;

public class Profile {
    private String email;
    private String hashedPassword;
    private String nickname;
    private String bio;
    private boolean isOnline;
    private String currentGame;
    private double winLossRatio;
    private GameHistory gamesPlayed;
    private BufferedImage profilePic;
    private String username;
    private long id;

    public Profile(String email, String hashedPassword, String nickname, String bio, boolean isOnline, String currentGame, double winLossRatio, GameHistory gamesPlayed, BufferedImage profilePic, String username, long id) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.bio = bio;
        this.isOnline = isOnline;
        this.currentGame = currentGame;
        this.winLossRatio = winLossRatio;
        this.gamesPlayed = gamesPlayed;
        this.profilePic = profilePic;
        this.username = username;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio = winLossRatio;
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
