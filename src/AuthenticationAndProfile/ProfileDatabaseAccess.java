package AuthenticationAndProfile;
//import leaderboard.Leaderboard;
import leaderboard.PlayerRanking;
import server.player.PlayerManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.util.List;

import static AuthenticationAndProfile.ProfileCSVReader.openSingleProfileFile;
import static AuthenticationAndProfile.FriendsListCSVReader.openFriendsListFile;
import static AuthenticationAndProfile.GameHistoryCSVReader.openGameHistoryFile;
/**
 * ProfileDatabase is the Class containing all the methods that construct the AuthenticationAndProfile Class objects based on
 * information recieved from the database based on PlayerManger query calls.
 * @author Alessia Flaig
 */
public class ProfileDatabaseAccess {
    /**
     * obtainProfile(long id) is called to recreate a Profile object for a profile from its information saved to the Database.
     * The method calls a PlayerManager method to send a csv file for the specified id. If an error results from the id not existing
     * in the database, an error is handled. The csv files fromthe FriendsList, GameHistory,
     * and PlayerRanking database csv's are also read and the information put into constructors. These objects, as well as the other
     * parameters read from the profile csv are input as parameters and a Player object is constructed and returned.
     * @pre There are profiles registered to the Profile Database.
     * @param id
     * @return Profile object from data corresponding to the id from the database
     */
    public static Profile obtainProfile(int id){
        //call method to get csv for id
        try {
            PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = ""; //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);

            //from Profile ArrayList obtain the values for Profile Class variables
            String username = profileFields.get(1);
            String nickname = profileFields.get(2);
            String email = profileFields.get(3);
            String hashedPassword = profileFields.get(4);
            //String bio = profileFields.get(7);
            String bio = profileFields.get(5);
            //String profilePicFilePath = profileFields.get(8);
            String profilePicFilePath = profileFields.get(6);
            BufferedImage profilePic = ImageIO.read(new File(profilePicFilePath));
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
        } catch (IOException e) {
            System.out.println("ID does not match a profile in the database.");
            return null;
        }
    }

    /**
     * Constructs a FriendsList Object based off of the FriendsList variables saved to the database for the specified id.
     * @param id
     * @return FriendsList object reflecting an up to date FriendsList that composes the profile saved in the database.
     */
    public static FriendsList obtainFriendsList(int id){
        //call method to get csv for id
        //call method to get csv for FriendsList
        //String csvFriendsListFilePath = PlayerManager.getPlayer(id); //method to get FriendsList csv for id
        //ArrayList<String> friendsListFields = openFriendsListFile(csvFriendsListFilePath);

        //from FriendsList ArrayList obtain the values for FriendsList Class variables
        try {
            PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = ""; //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<String> friends = new ArrayList<>();
            String friendsString = profileFields.get(15);
            String[] fieldsList = friendsString.split(", ");
            for (int i = 0 ; i < fieldsList.length; i ++){
                friends.add(fieldsList[i]);
            }

            List<String> friendRequests = new ArrayList<>();
            String friendRequestString = profileFields.get(16);
            fieldsList = friendRequestString.split(", ");
            for (int i = 0 ; i < fieldsList.length; i ++){
                friendRequests.add(fieldsList[i]);
            }
            FriendsList friendsList = new FriendsList(friends, friendRequests);
            return friendsList;
        } catch (IOException e) {
            System.out.println("ID does not match a profile in the database.");
            return null;
        }

    }

    public static PlayerRanking obtainPlayerRanking(int id) {
        //call method to get csv for id
        //call method to gte csv for PlayerRanking
        //String csvPlayerRankingFilePath = PlayerManager.getPlayerRanking(id); //method to get PlayerRanking csv for id
        //ArrayList<String> playerRankingFields = openPlayerRankingFile(csvPlayerRankingFilePath);
        //from PlayerRanking ArrayList obtain the values for PlayerRanking Class variables

        PlayerRanking playerRanking = new PlayerRanking();


    }

    public static GameHistory obtainGameHistory(int id) {
        //call method to get csv for id
        //call method to get csv for GameHistory
        //String csvGameHistoryFilePath = PlayerManager.getGameHistory(id); //method to get FriendsList csv for id
        //ArrayList<String> gameHistoryFields = openGameHistoryFile(csvGameHistoryFilePath);
        try {
            PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = ""; //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<String> gameHistory = new ArrayList<>();
            String gameHistoryString = profileFields.get(13);
            String[] fieldsList = gameHistoryString.split(", ");
            for (int i = 0 ; i < fieldsList.length; i ++){
                gameHistory.add(fieldsList[i]);
            }

            GameHistory gameHistoryObject = new GameHistory(gameHistory);
            return gameHistoryObject;
        } catch (IOException e) {
            System.out.println("ID does not match a profile in the database.");
            return null;
        }
    }

    /**
     * removeProfile(long id) removes all profile information associated from the Database and logs the account out.
     * @param id
     */
    public static void removeProfile(int id){
        PlayerManager.deleteProfile(id);
        Authentication.logOut();
    }

    /**
     * getAllProfiles() is used to obtain the HashMap of all username keys and all Profile values.
     * @return HashMap<String, Profile>
     */
    public static ArrayList<String> getAllProfiles() {
        String csvProfileFilePath = ""; //csv is generated in the main directory of the project
        PlayerManager.getPlayer();//method to get all profiles in database into a csv file
        ArrayList<String> profileFields = ProfileCSVReader.openProfilesFile(csvProfileFilePath);
        return profileFields;
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param search String
     */
    public static void searchForProfile(String search){



    }

    /**
     * Method to obtain the profile information required to view a personal profile.
     * @param profile
     */
    public static void viewPersonalProfile(Profile profile){

    }

    /**
     * Method to obtain the profile information required to view other profiles.
     * @param profile
     */
    public static void viewOtherProfile(Profile profile){

    }



    public static void main(String[] args) {
        PlayerManager.getPlayer();
    }
}
