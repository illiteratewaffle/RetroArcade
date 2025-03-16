package AuthenticationAndProfile;

public class ProfileDatabase {
    private ProfileEmailSet<String> profileEmailSet = new ProfileEmailSet<String>;
    private HashMap<Username, Profile> profiles = new HashMap<Username, Profile>;
    private Leaderboard leaderboard = new Leaderboard;

    public ProfileDatabase(ProfileEmailSet<String> profileEmailSet, HashMap<Username, Profile> profiles, Leaderboard leaderboard) {
        this.profileEmailSet = profileEmailSet;
        this.profiles = profiles;
        this.leaderboard = leaderboard;
    }

    public void addProfile(Username, Profile){

    }

    public void removeProfile(Username, Profile){

    }

    public void searchForProfile(String){

    }

    public void viewProfile(Profile){

    }

    public ProfileEmailSet<String> getProfileEmailSet() {
        return profileEmailSet;
    }

    public Leaderboard viewLeaderboard() {

    }

    public Leaderboard viewLeaderboard(Profile, FriendsList){

    }


}