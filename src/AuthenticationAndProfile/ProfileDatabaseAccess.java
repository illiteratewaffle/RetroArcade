package AuthenticationAndProfile;
import leaderboard.PlayerRanking;
import server.player.PlayerManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.List;

import static AuthenticationAndProfile.ProfileCSVReader.openSingleProfileFile;

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
    public static Profile obtainProfile(int id) {
        //call method to get csv for id
//        try {
            //PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("profile_%d_export.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);

            //from Profile ArrayList obtain the values for Profile Class variables
            String username = profileFields.get(1);
            String nickname = profileFields.get(2);
            String email = profileFields.get(3);
            String hashedPassword = profileFields.get(4);
            String bio = profileFields.get(5);
            String profilePicFilePath = profileFields.get(6);
            String currentGame = profileFields.get(7);
            boolean isOnline;
            if (profileFields.get(8).equals("true")) {
                isOnline = true;
            } else {
                isOnline = false;
            }

            //Call methods to create the FriendsList, GameHistory, and PlayerRanking objects from database attached to profile id
            FriendsList friendsList = obtainFriendsList(id);
            GameHistory gameHistory = obtainGameHistory(id);
            PlayerRanking playerRanking = obtainPlayerRanking(id);

            Profile profile = new Profile(email, hashedPassword, nickname, bio, isOnline, currentGame, friendsList, playerRanking, gameHistory, profilePicFilePath, username, id);
            return profile;
//        } catch (IOException e) {
//            System.out.println("ID does not match a profile in the database.");
//            return null;
//        }
//        } catch (IOException f) {
//            System.out.println("Image issue.");
//            return null;
//        } catch (NullPointerException g) {
//            System.out.println("Profile does not exist.");
//            return null;
//        }
    }

    /**
     * Constructs a FriendsList Object based off of the FriendsList variables saved to the database for the specified id.
     * @param id
     * @return FriendsList object reflecting an up to date FriendsList that composes the profile saved in the database.
     */
    public static FriendsList obtainFriendsList(int id){
        //call method to get csv for id
        //try {
            //PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("profile_%d_export.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<String> friends = new ArrayList<>();
            String friendsString = profileFields.get(15);
            String[] fieldsList = friendsString.split(",");
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
        //} catch (IOException e) {
        //    System.out.println("ID does not match a profile in the database.");
        //    return null;
        //}
    }

    public static PlayerRanking obtainPlayerRanking(int id) {
        //call method to get csv for id
        //try {
            //PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("profile_%d_export.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);

            //from Profile ArrayList obtain the values for Profile Class variables
            double winLossRatio = Double.parseDouble(profileFields.get(9));
            int rating = Integer.parseInt(profileFields.get(10));
            String rank = profileFields.get(11);
            int wins = Integer.parseInt(profileFields.get(12));

            PlayerRanking playerRanking = new PlayerRanking(winLossRatio, rating, rank, wins);
            return playerRanking;
//        } catch (IOException e){
//            System.out.println("ID does not match a profile in the database.");
//            return null;
//        }
    }

    public static GameHistory obtainGameHistory(int id) {
        //call method to get csv for id
        //try {
            //PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("profile_%d_export.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<String> gameHistory = new ArrayList<>();
            String gameHistoryString = profileFields.get(13);
            String[] fieldsList = gameHistoryString.split(", ");
            for (int i = 0 ; i < fieldsList.length; i ++){
                gameHistory.add(fieldsList[i]);
            }
            GameHistory gameHistoryObject = new GameHistory(gameHistory);
            return gameHistoryObject;
//        } catch (IOException e) {
//            System.out.println("ID does not match a profile in the database.");
//            return null;
//        }
    }

    /**
     * removeProfile(long id) removes all profile information associated from the Database and logs the account out.
     * @param id
     */
    public static void removeProfile(int id){
        //PlayerManager.deleteProfile(id);
        Authentication.logOut();
    }

    /**
     * getAllProfiles() is used to obtain the HashMap of all username keys and all Profile values.
     * @return HashMap<String, Profile>
     */
    public static ArrayList<ArrayList<String>> getAllProfiles() {
        String csvProfileFilePath = ""; //csv is generated in the main directory of the project
        PlayerManager.getProfileTable();//method to get all profiles in database into a csv file
        ArrayList<ArrayList<String>> profileFields = ProfileCSVReader.openProfilesFile(csvProfileFilePath);
        return profileFields;
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param search String
     */
    public static List<Profile> searchForProfile(String search) {
        //List<Integer> usernameSearchMatchIdList = PlayerManager.searchProfileTable(search);
        List<Profile> profilesFound = new ArrayList<Profile>();
        //for (int i = 0; i < usernameSearchMatchIdList.size(); i ++) {
        //profilesFound.add(usernameSearchMatchIdList[i]);
        //}
        return profilesFound;
    }

    /**
     * Method to obtain the profile information required to view a personal profile.
     * @param profile
     */
    public static void viewPersonalProfile(Profile profile){
        //GameHistory.getFullGameHistory();
        //username
        //nickname
        //email
        //bio
        //profilePic
        //friendsList
    }

    /**
     * Method to obtain the profile information required to view other profiles.
     * @param profile
     */
    public static void viewOtherProfile(Profile profile){
        //username
        //nickname
        //bio
        //profilePic
        //currentStatus
        //GameHistory.getRecentGames();
        //PlayerRanking
        //make friend request
    }

    public static void main(String[] args) {
        Profile saraProfile2 = obtainProfile(2);
        System.out.println(saraProfile2.getBio());
        System.out.println(saraProfile2.getFriendsList().getFriendRequests());
        System.out.println(saraProfile2.getFriendsList().getFriendsList());
    }
}

