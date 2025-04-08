package AuthenticationAndProfile;
//import leaderboard.PlayerRanking;
import player.PlayerManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static AuthenticationAndProfile.ProfileCSVReader.openSingleProfileFile;
import static java.nio.file.Files.delete;


/**
 * ProfileDatabase is the Class containing all the methods that construct the AuthenticationAndProfile Class objects based on
 * information recieved from the database based on PlayerManger query calls.
 * @author Alessia Flaig
 */
public class ProfileDatabaseAccess {
    /**
     * obtainProfile(long id) is called to recreate a Profile object for a profile from its information saved to the Database.
     * The method calls a PlayerManager method to send a csv file for the specified id. If an error results from the id not existing
     * in the database, an error is handled. The csv files from the FriendsList, GameHistory,
     * and PlayerRanking database csv's are also read and the information put into constructors. These objects, as well as the other
     * parameters read from the profile csv are input as parameters and a Player object is constructed and returned.
     * @pre There are profiles registered to the Profile Database.
     * @param id
     * @return Profile object from data corresponding to the id from the database
     */
    public static Profile obtainProfile(int id) throws SQLException, IOException, NullPointerException {
        //call method to get csv for id
        try {
            PlayerManager.getProfile(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("player_profile_%d.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);

            //from Profile ArrayList obtain the values for Profile Class variables
            String username = profileFields.get(ProfileCSVReader.USER_INDEX);
            String nickname = profileFields.get(ProfileCSVReader.NICK_INDEX);
            String email = profileFields.get(ProfileCSVReader.EMAIL_INDEX);
            String hashedPassword = profileFields.get(ProfileCSVReader.PWD_INDEX);
            String bio = profileFields.get(ProfileCSVReader.BIO_INDEX);
            String profilePicFilePath = profileFields.get(ProfileCSVReader.PIC_INDEX);
            String currentGame = profileFields.get(ProfileCSVReader.CGAME_INDEX);
            boolean isOnline;
            if (profileFields.get(ProfileCSVReader.ONLINE_INDEX).equals("true")) {
                isOnline = true;
            } else {
                isOnline = false;
            }

            //Call methods to create the FriendsList, GameHistory, and PlayerRanking objects from database attached to profile id
            FriendsList friendsList = obtainFriendsList(id);
            GameHistory gameHistory = obtainGameHistory(id);
            PlayerRanking playerRanking = obtainPlayerRanking(id);

            Profile profile = new Profile(email, hashedPassword, nickname, bio, isOnline, currentGame, friendsList, playerRanking, gameHistory, profilePicFilePath, username, id);
            delete(Paths.get(String.format("player_profile_%d.csv", id)));
            return profile;
        } catch (NullPointerException n) {
            throw new NullPointerException(n.getMessage());
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static Profile obtainProfileDirect(int id) throws SQLException {
        try {
            //from Profile ArrayList obtain the values for Profile Class variables
            String username = PlayerManager.getAttribute(id, "username");
            String nickname = PlayerManager.getAttribute(id, "nickname");
            String email = PlayerManager.getAttribute(id, "email");
            String hashedPassword = PlayerManager.getAttribute(id, "hashed_password");
            String bio = PlayerManager.getAttribute(id, "bio");
            String profilePicFilePath = PlayerManager.getAttribute(id, "profile_pic_path");
            String currentGame = PlayerManager.getAttribute(id, "current_game");
            boolean isOnline;
            if (PlayerManager.getAttribute(id, "is_online") != null) {
                if (PlayerManager.getAttribute(id, "is_online").equals("true")) {
                    isOnline = true;
                } else {
                    isOnline = false;
                }
            } else {
                isOnline = false;
            }

            //Call methods to create the FriendsList, GameHistory, and PlayerRanking objects from database attached to profile id
            FriendsList friendsList = obtainFriendsListDirect(id);
            GameHistory gameHistory = obtainGameHistoryDirect(id);
            PlayerRanking playerRanking = obtainPlayerRankingDirect(id);

            Profile profile = new Profile(email, hashedPassword, nickname, bio, isOnline, currentGame, friendsList, playerRanking, gameHistory, profilePicFilePath, username, id);
            return profile;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Constructs a FriendsList Object based off of the FriendsList variables saved to the database for the specified id.
     * @param id
     * @return FriendsList object reflecting an up to date FriendsList that composes the profile saved in the database.
     */
    public static FriendsList obtainFriendsList(int id) throws SQLException, IOException {
        //call method to get csv for id
        try {
            PlayerManager.getProfile(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("player_profile_%d.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<Integer> friends = new ArrayList<>();
            String friendsString = profileFields.get(ProfileCSVReader.FRIENDS_INDEX);
            if (!friendsString.equals("")) {
                String[] fieldsList = friendsString.split(",");
                for (int i = 0; i < fieldsList.length; i++) {
                    friends.add(Integer.parseInt(fieldsList[i]));
                }
            }
            List<Integer> friendRequests = new ArrayList<>();
            String friendRequestString = profileFields.get(ProfileCSVReader.FREQUEST_INDEX);
            if (!friendRequestString.equals("")) {
                String[] fieldsList = friendRequestString.split(",");
                for (int i = 0; i < fieldsList.length; i++) {
                    friendRequests.add(Integer.parseInt(fieldsList[i]));
                }
            }
            FriendsList friendsList = new FriendsList(friends, friendRequests, id);
            return friendsList;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static FriendsList obtainFriendsListDirect(int id) throws SQLException{
        List<Integer> friends = new ArrayList<>();
        String friendsString = PlayerManager.getAttribute(id, "friends");
        if (friendsString != null){
            String[] fieldsList = friendsString.split(",");
            for (int i = 0 ; i < fieldsList.length; i ++){
                friends.add(Integer.parseInt(fieldsList[i]));
            }
        }
        List<Integer> friendRequests = new ArrayList<>();
        String friendRequestString = PlayerManager.getAttribute(id, "friend_requests");
        if(friendRequestString != null) {
            String[] fieldsList = friendRequestString.split(",");
            for (int i = 0; i < fieldsList.length; i++) {
                friendRequests.add(Integer.parseInt(fieldsList[i]));
            }
        }
        FriendsList friendsList = new FriendsList(friends, friendRequests, id);
        return friendsList;
    }

    public static PlayerRanking obtainPlayerRanking(int id) {
        //call method to get csv for id
        try {
            String csvProfileFilePath = String.format("player_profile_%d.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);

            //from Profile ArrayList obtain the values for Profile Class variables
            double[] winLossRatio = new double[3];
            int[] rating = new int[3];
            String[] rank = new String[3];
            int[] wins = new int[3];
            int[] losses = new int[3];
            int[] total = new int[3];

            winLossRatio[0] = Double.parseDouble(profileFields.get(ProfileCSVReader.WLR_TTT_INDEX));
            winLossRatio[1] = Double.parseDouble(profileFields.get(ProfileCSVReader.WLR_CONNECT4_INDEX));
            winLossRatio[2] = Double.parseDouble(profileFields.get(ProfileCSVReader.WLR_CHECKERS_INDEX));

            rating[0] = Integer.parseInt(profileFields.get(ProfileCSVReader.RATING_TTT_INDEX));
            rating[1] = Integer.parseInt(profileFields.get(ProfileCSVReader.RATING_CONNECT4_INDEX));
            rating[2] = Integer.parseInt(profileFields.get(ProfileCSVReader.RATING_CHECKERS_INDEX));

            rank[0] = profileFields.get(ProfileCSVReader.RANK_TTT_INDEX);
            rank[1] = profileFields.get(ProfileCSVReader.RANK_CONNECT4_INDEX);
            rank[2] = profileFields.get(ProfileCSVReader.RANK_CHECKERS_INDEX);

            wins[0] = Integer.parseInt(profileFields.get(ProfileCSVReader.WINS_TTT_INDEX));
            wins[1] = Integer.parseInt(profileFields.get(ProfileCSVReader.WINS_CONNECT4_INDEX));
            wins[2] = Integer.parseInt(profileFields.get(ProfileCSVReader.WINS_CHECKERS_INDEX));

            losses[0] = Integer.parseInt(profileFields.get(ProfileCSVReader.LOSSES_TTT_INDEX));
            losses[1] = Integer.parseInt(profileFields.get(ProfileCSVReader.LOSSES_CONNECT4_INDEX));
            losses[2] = Integer.parseInt(profileFields.get(ProfileCSVReader.LOSSES_CHECKERS_INDEX));

            total[0] = Integer.parseInt(profileFields.get(ProfileCSVReader.TOTAL_TTT_INDEX));
            total[1] = Integer.parseInt(profileFields.get(ProfileCSVReader.TOTAL_CONNECT4_INDEX));
            total[2] = Integer.parseInt(profileFields.get(ProfileCSVReader.TOTAL_CHECKERS_INDEX));

            PlayerRanking playerRanking = new PlayerRanking(id, winLossRatio, rating, rank, wins, losses, total);
            return playerRanking;
        } catch (IOException e){
            System.out.println("ID does not match a profile in the database.");
            return null;
        }
    }

    public static PlayerRanking obtainPlayerRankingDirect(int id) throws SQLException{
        //from Profile ArrayList obtain the values for Profile Class variables
        double[] winLossRatio = new double[3];
        int[] rating = new int[3];
        String[] rank = new String[3];
        int[] wins = new int[3];
        int[] losses = new int[3];
        int[] total = new int[3];
        try {
            winLossRatio[0] = Double.parseDouble(PlayerManager.getAttribute(id, "win_loss_ratio_ttt"));
            winLossRatio[1] = Double.parseDouble(PlayerManager.getAttribute(id, "win_loss_ratio_connect4"));
            winLossRatio[2] = Double.parseDouble(PlayerManager.getAttribute(id, "win_loss_ratio_checkers"));

            rating[0] = Integer.parseInt(PlayerManager.getAttribute(id, "rating_ttt"));
            rating[1] = Integer.parseInt(PlayerManager.getAttribute(id, "rating_connect4"));
            rating[2] = Integer.parseInt(PlayerManager.getAttribute(id, "rating_checkers"));

            rank[0] = PlayerManager.getAttribute(id, "rank_ttt");
            rank[1] = PlayerManager.getAttribute(id, "rank_connect4");
            rank[2] = PlayerManager.getAttribute(id, "rank_checkers");

            wins[0] = Integer.parseInt(PlayerManager.getAttribute(id, "wins_ttt"));
            wins[1] = Integer.parseInt(PlayerManager.getAttribute(id, "wins_connect4"));
            wins[2] = Integer.parseInt(PlayerManager.getAttribute(id, "wins_checkers"));

            losses[0] = Integer.parseInt(PlayerManager.getAttribute(id, "losses_ttt"));
            losses[1] = Integer.parseInt(PlayerManager.getAttribute(id, "losses_connect4"));
            losses[2] = Integer.parseInt(PlayerManager.getAttribute(id, "losses_checkers"));

            total[0] = Integer.parseInt(PlayerManager.getAttribute(id, "total_ttt"));
            total[1] = Integer.parseInt(PlayerManager.getAttribute(id, "total_connect4"));
            total[2] = Integer.parseInt(PlayerManager.getAttribute(id, "total_checkers"));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }

        PlayerRanking playerRanking = new PlayerRanking(id, winLossRatio, rating, rank, wins, losses, total);
        return playerRanking;
    }

    public static GameHistory obtainGameHistory(int id) throws IOException {
        //call method to get csv for id
        try {
            //PlayerManager.getProfileTable(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("player_profile_%d.csv", id); //csv file saved to the main project directory

            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            List<String> gameHistory = new ArrayList<>();
            String gameHistoryString = profileFields.get(ProfileCSVReader.GHIST_INDEX);
            if (!gameHistoryString.equals("")) {
                String[] fieldsList = gameHistoryString.split(", ");
                for (int i = 0; i < fieldsList.length; i++) {
                    gameHistory.add(fieldsList[i]);
                }
            }
            HashMap<String, Double> achievementProgress = new HashMap<>();
            String achievementProgressString = profileFields.get(ProfileCSVReader.ACHIVPROG_INDEX);
            if (!achievementProgressString.equals("")) {
                String[] achievements = achievementProgressString.split(",");
                String section = "";
                String key = "";
                Double value = 0.00;
                boolean keyComplete = false;
                for (int j = 0; j < achievements.length; j++) {
                    String entry = achievements[j];
                    for (int k = 0; k < entry.length(); k++) {
                        char c = entry.charAt(k);
                        if (c == '=') {
                            continue;
                        } else if (c == '>') {
                            keyComplete = true;
                            key = section;
                        } else if (c != '"') {
                            section += c;
                        }
                    }
                    value = Double.parseDouble(section);
                    achievementProgress.put(key, value);
                }
            }
            GameHistory gameHistoryObject = new GameHistory(gameHistory, achievementProgress, id);
            return gameHistoryObject;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static GameHistory obtainGameHistoryDirect(int id) throws SQLException {
        try {
            List<String> gameHistory = new ArrayList<>();
            String gameHistoryString = PlayerManager.getAttribute(id, "games_played");
            if (!gameHistoryString.equals("")) {
                String[] fieldsList = gameHistoryString.split(", ");
                for (int i = 0; i < fieldsList.length; i++) {
                    gameHistory.add(fieldsList[i]);
                }
            }
            HashMap<String, Double> achievementProgress = new HashMap<>();
            String achievementProgressString = PlayerManager.getAttribute(id, "achievement_progress");
            if (achievementProgressString != null) {
                String section = "";
                boolean inSection = false;
                for (int j = 0; j < achievementProgressString.length(); j++) {
                    Character c = Character.valueOf(achievementProgressString.charAt(j));
                    if (c == '{') {
                        inSection = true;
                    } else if (c == ',' && !inSection) {
                        String[] entry = section.split(",");
                        String key = entry[0];
                        Double value = Double.parseDouble(entry[1]);
                        achievementProgress.put(key, value);
                        section = "";
                    } else if (c == '}') {
                        inSection = false;
                    } else if (j == achievementProgressString.length() - 1) {
                        section = section + c;
                        String[] entry = section.split(",");
                        String key = entry[0];
                        Double value = Double.parseDouble(entry[1]);
                        achievementProgress.put(key, value);
                    } else if (c != '"') {
                        section = section + c;
                    }
                }
            }
            GameHistory gameHistoryObject = new GameHistory(gameHistory, achievementProgress, id);
            return gameHistoryObject;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * removeProfile(long id) removes all profile information associated from the Database and logs the account out.
     * @param id
     */
    public static void removeProfile(int id) throws SQLException, IOException {
        try {
            Authentication.logOut(id);
            PlayerManager.deleteProfile(id);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * getAllProfiles() is used to obtain the HashMap of all username keys and all Profile values.
     * @return HashMap<String, Profile>
     */
    public static ArrayList<ArrayList<String>> getAllProfiles() throws SQLException, IOException {
        String csvProfileFilePath = "profiles_export.csv"; //csv is generated in the main directory of the project
        try {
            PlayerManager.getProfileTable();//method to get all profiles in database into a csv file
            ArrayList<ArrayList<String>> profileFields = ProfileCSVReader.openProfilesFile(csvProfileFilePath);
            return profileFields;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param search String
     */
    public static List<Integer> searchForProfile(String search) throws SQLException {
        try {
            List<Integer> searchResults = PlayerManager.searchProfiles(search);
            return searchResults;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }



    public static void main(String[] args) {
//        try {
//
//        } catch (SQLException | NoSuchAlgorithmException s){
//            System.out.println(s.getMessage());
//        }
    }
}

