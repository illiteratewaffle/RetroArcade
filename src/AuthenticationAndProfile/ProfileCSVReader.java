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
    GameHistory object: (index 5) May actually need to call on a separate GameHistory database to obtain info to create then put into
    the profile during construction (based on matching id)
    FriendsList object: (index 6) same as above
    bio: index 7
    profilePic: index 8
    currentGame: index 9
    isOnline: index 10
     */
    private static final int ID_INDEX = 0;
    private static final int USER_INDEX = 1;
    private static final int NICK_INDEX = 2;
    private static final int EMAIL_INDEX = 3;
    private static final int PWD_INDEX = 4;
    //private static final int HIST_INDEX = 5;
    //public static final int FRIENDLIST_INDEX = 6;
    //public static final int BIO_INDEX = 7;
    public static final int BIO_INDEX = 5;
    //public static final int PIC_INDEX = 8;
    public static final int PIC_INDEX = 6;
    //public static final int CGAME_INDEX = 9;
    public static final int CGAME_INDEX = 7;
    //public static final int ONLINE_INDEX = 10;
    public static final int ONLINE_INDEX = 8;


//    public static void main(String[] args) {
//        ArrayList<String> fields = openProfileFile(FILEPATH);
//    }

    /**
     * Opens CSV file (from networking): A list of all profiles and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * @return
     */
    public static ArrayList<String> openProfileFile(String filePath){
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
                    + ", email: " + fields.get(EMAIL_INDEX) + ", hashedPassword: " + fields.get(PWD_INDEX)
                    //+ ", GameHistory: " + fields.get(HIST_INDEX)
                    //+ ", FriendsList: " + fields.get(FRIENDLIST_INDEX)
                    + ", bio: " + fields.get(BIO_INDEX)
                    + ", profile pic: " + fields.get(PIC_INDEX)
                    + ", current game: " + fields.get(CGAME_INDEX)
                    + ", isOnline: " + fields.get(ONLINE_INDEX));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("System can't find file");
        }

        return fields;
    }
}