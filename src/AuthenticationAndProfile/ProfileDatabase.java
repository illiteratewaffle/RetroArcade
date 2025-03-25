package AuthenticationAndProfile;
import leaderboard.Leaderboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import static AuthenticationAndProfile.ProfileCSVReader.openProfileFile;
import static AuthenticationAndProfile.FriendsListCSVReader.openFriendsListFile;
import static AuthenticationAndProfile.GameHistoryCSVReader.openGameHistoryFile;

/**
 * ProfileDatabase is the Class containing all the information stored on the Network for all registered accounts.
 * @author Alessia Flaig
 */
public class ProfileDatabase {
    private static ProfileEmailSet<String> profileEmailSet = new ProfileEmailSet<>();
    private static HashMap<String, Profile> profiles = new HashMap<String, Profile>();
    private Leaderboard leaderboard = new Leaderboard();

    /**
     * ProfileDatabase constructor
     */
    public ProfileDatabase() {

    }

    public Profile obtainProfile(long id){
        //call method to get csv for id
        String csvProfileFilePath = PlayerManager.getPlayer(id);//method to get Profile csv for id
        ArrayList<String> profileFields = openProfileFile(csvProfileFilePath);

        //from Profile ArrayList obtain the values for Profile Class variables
        String username = profileFields.get(1);
        String nickname = profileFields.get(2);
        String email = profileFields.get(3);
        String hashedPassword = profileFields.get(4);
        //String bio = profileFields.get(7);
        String bio = profileFields.get(5);
        //String profilePicFilePath = profileFields.get(8);
        String profilePicFilePath = profileFields.get(6);
        BufferedImage profilePic = ImageIO.read(profilePicFilePath);
        //String currentGame = profileFields.get(9);
        String currentGame = profileFields.get(7);
        Boolean isOnline;
        //if (profileFields.get(10).equals("true")) {
        if(profileFields.get(8).equals("true")) {
            isOnline = true;
        } else {
            isOnline = false;
        }

        //Call methods to create the FriendsList, GameHistory, and PlayerRanking objects from database attached to profile id
        FriendsList friendsList = obtainFriendsList(id);
        GameHistory gameHistory = obtainGameHistory(id);
        PlayerRanking playerRanking = obtainPlayerRanking(id);

        Profile profile = new Profile(email, hashedPassword, nickname, bio, isOnline, currentGame, friendsList, playerRanking, gameHistory, profilePic, username, id);
        return profile;

    }


    public FriendsList obtainFriendsList(long id){
        //call method to get csv for id
        //call method to get csv for FriendsList
        String csvFriendsListFilePath = PlayerManager.getFriendsList(id); //method to get FriendsList csv for id
        ArrayList<String> friendsListFields = openFriendsListFile(csvFriendsListFilePath);

        //from FriendsList ArrayList obtain the values for FriendsList Class variables

        FriendsList friendsList = new FriendsList();
    }

    public PlayerRanking obtainPlayerRanking(long id) {
        //call method to get csv for id
        //call method to gte csv for PlayerRanking
        String csvPlayerRankingFilePath = PlayerManager.getPlayerRanking(id); //method to get PlayerRanking csv for id
        ArrayList<String> playerRankingFields = openPlayerRankingFile(csvPlayerRankingFilePath);
        //from PlayerRanking ArrayList obtain the values for PlayerRanking Class variables

        PlayerRanking playerRanking = new PlayerRanking();


    }

    public GameHistory obtainGameHistory(long id) {
        //call method to get csv for id
        //call method to get csv for GameHistory
        String csvGameHistoryFilePath = PlayerManager.getGameHistory(id); //method to get FriendsList csv for id
        ArrayList<String> gameHistoryFields = openGameHistoryFile(csvGameHistoryFilePath);

        GameHistory gameHistory = new GameHistory();
    }





//    /**
//     * addProfile(String username, Profile profile) adds the username key and its associated profile to the HashMap of all
//     * registered accounts.
//     * @param username
//     * @param profile
//     */
//    public static void addProfile(String username, Profile profile){
//        profiles.put(username, profile);
//    }


    /**
     * removeProfile(String username) takes the provided username key to remove the email from the ProfileEmailSet and the profile from the Database.
     * @param username
     */
    public static void removeProfile(String username){
        profileEmailSet.removeEmail(profiles.get(username).getEmail());
        profiles.remove(username);
        //May need to handle logging off of the profile back to the main menu?
    }

    /**
     * getProfile(String username) is used to obtain the Profile object for the provided username.
     * @param username
     * @return Profile
     */
    public static Profile getProfile(String username){
        return profiles.get(username);
    }

    /**
     * getAllProfiles() is used to obtain the HashMap of all username keys and all Profile values.
     * @return HashMap<String, Profile>
     */
    public static HashMap<String, Profile> getAllProfiles() {
        return profiles;
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param search String
     */
    public static void searchForProfile(String search){

    }

    /**
     * Method to obtain the profile information required to view a profile.
     * @param profile
     */
    public static void viewProfile(Profile profile){

    }

    /**
     * Email is verified to not be already associated to a profile and then is added to the profileEmailSet.
     * @param email
     * @return
     * @throws Exception "Email is already associated with an account"
     */
    public static boolean addEmailToDatabase(String email) throws Exception{
        if (profileEmailSet.isValidEmail(email)) {
            profileEmailSet.addEmail(email);
            return true;
        } else{
            throw new Exception("Email is already associated with an account.");
        }
    }

    /**
     * Removes email from profileEmailSet.
     * @param email
     */
    public static void removeEmailFromDatabase(String email){
        profileEmailSet.removeEmail(email);
    }

    /**
     * ProfileEmailSet Class for the HashSet ProfileEmailSet to access all emails currently associated with an account.
     * @author Alessia Flaig
     */
    private static class ProfileEmailSet<String> {
        private HashSet<String> profileEmailHashSet = new HashSet<String>();

        public ProfileEmailSet() {
        }

        /**
         * isValidEmail(String email) checks if the provided email address can be used for an account.
         * @param email String
         * @return true if email is not already not in the HashSet.
         * Returns false if email is already associated with an account in the database.
         */
        public boolean isValidEmail(String email){
            return !profileEmailHashSet.contains(email);
        }

        /**
         * removeEmail(String email) removes the provided email from the ProfileEmailSet HashSet.
         * @param email String
         */
        public void removeEmail(String email){
            profileEmailHashSet.remove(email);
        }

        /**
         * Adds email to the ProfileEmailSet HashSet.
         * @param email
         */
        public void addEmail(String email){
            profileEmailHashSet.add(email);
        }
    }
}
