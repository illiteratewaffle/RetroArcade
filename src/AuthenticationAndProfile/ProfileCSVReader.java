package AuthenticationAndProfile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileCSVReader {
    //private static final String FILEPATH = "src/AuthenticationAndProfile/profile_ID.csv"; //exports to this location with the id of the associated profile

    /*
    ID: index 0
    username: index 1
    nickname: index 2
    email: index 3
    hashedPassword: index 4
    bio: index 5
    profilePicFilePath: index 6
    currentGameStatus: index 7
    isOnlineStatus: index 8
    winLossRatio: index 9
    rating: index 10
    rank: index 11
    wins: index 12
    gameHistory: index 13
    achievementProgress: index 14
    friends: index 15
    friendRequests: index 16
     */
    private static final int ID_INDEX = 0;
    private static final int USER_INDEX = 1;
    private static final int NICK_INDEX = 2;
    private static final int EMAIL_INDEX = 3;
    private static final int PWD_INDEX = 4;
    public static final int BIO_INDEX = 5;
    public static final int PIC_INDEX = 6;
    public static final int CGAME_INDEX = 7;
    public static final int ONLINE_INDEX = 8;
    private static final int WLR_INDEX = 9;
    private static final int RATING_INDEX = 10;
    private static final int RANK_INDEX = 11;
    private static final int WINS_INDEX = 12;
    public static final int GHIST_INDEX = 13;
    public static final int ACHIVPROG_INDEX = 14;
    public static final int FRIENDS_INDEX = 15;
    public static final int FREQUEST_INDEX = 16;

    /**
     * Opens CSV file (from networking): A list of one profile and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * @return
     */
    public static ArrayList<String> openSingleProfileFile(String filePath){
        ArrayList<String> fields = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read and process the line
            String line;
            line = br.readLine();
            // Split the line by ", " and store each field in an ArrayList<Object>
            String[] fieldsList = line.split(", ");
            for (String fieldsString : fieldsList) {
                fields.add(fieldsString.trim());
            }
            System.out.println("ID: " + fields.get(ID_INDEX)
                    + ", username: " + fields.get(USER_INDEX)
                    + ", nickname: " + fields.get(NICK_INDEX)
                    + ", email: " + fields.get(EMAIL_INDEX)
                    + ", hashedPassword: " + fields.get(PWD_INDEX)
                    + ", bio: " + fields.get(BIO_INDEX)
                    + ", profile pic: " + fields.get(PIC_INDEX)
                    + ", current game: " + fields.get(CGAME_INDEX)
                    + ", isOnline: " + fields.get(ONLINE_INDEX)
                    + ", wlr: " + fields.get(WLR_INDEX)
                    + ", Rating: " + fields.get(RATING_INDEX)
                    + ", Rank: " + fields.get(RANK_INDEX)
                    + ", Wins: " + fields.get(WINS_INDEX)
                    + ", gameHistory: " + fields.get(GHIST_INDEX)
                    + ", achievemntProgress: " + fields.get(ACHIVPROG_INDEX)
                    + ", friends: " + fields.get(FRIENDS_INDEX)
                    + ", friendRequests: " + fields.get(FREQUEST_INDEX)
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("System can't find file");
        }
        return fields;
    }


    /**
     * openProfilesFiles(String filePath) method used to read profile csv's that contain all ids in the database.
     * @param filePath
     * @return ArrayList<String> of all profiles in the database
     */
    public static ArrayList<String> openProfilesFile(String filePath) {
        ArrayList<String> fields = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();
            while (line != null) {
                line = br.readLine();
                String[] fieldsList = line.split(",");
                for (String fieldsString : fieldsList) {
                    fields.add(fieldsString.trim());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("System can't find file");
        }
        return fields;
    }

//    public static void main(String[] args) {
//        openSingleProfileFile("C:src/AuthenticationAndProfile/profile_ID.csv");
//
//    }
}