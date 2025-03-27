package AuthenticationAndProfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//unneeded now?
public class FriendsListCSVReader {
    //private static final String FILEPATH = "src/AuthenticationAndProfile/friendsList_ID.csv"; //saves with the id of the profile it belongs to

    /*
    ID: index 0
    FriendsList: list of profile IDs?: index 1
    Friend Requests: list of profile IDs of profiles who have sent un handled friend requests: index 2
     */
    private static final int ID_INDEX = 0;
    public static final int FRIENDS_INDEX = 1;
    public static final int REQUESTS_INDEX = 2;

    /**
     * Opens CSV file (from networking): A list of all profiles and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * @return
     */
    public static ArrayList<String> openFriendsListFile(String filePath){
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
                    + ", Friends: " + fields.get(FRIENDS_INDEX)
                    + ", Requests: " + fields.get(REQUESTS_INDEX));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("System can't find file");
        }

        return fields;
    }

}
