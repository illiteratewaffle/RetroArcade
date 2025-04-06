package AuthenticationAndProfile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ProfileCSVReader {
    /* New: With Game Rankings *********************************
    ID: index 0
    username: index 1
    nickname: index 2
    email: index 3
    hashedPassword: index 4
    bio: index 5
    profilePicFilePath: index 6
    currentGameStatus: index 7
    isOnlineStatus: index 8
    (TTT)
    winLossRatio: index 9
    rating: index 10
    rank: index 11
    wins: index 12
    (Connect-4)
     winLossRatio: index 13
    rating: index 14
    rank: index 15
    wins: index 16
    (Checkers)
     winLossRatio: index 17
    rating: index 18
    rank: index 19
    wins: index 20
    gameHistory: index 21
    achievementProgress: index 22
    friends: index 23
    friendRequests: index 24
     */
    protected static final int ID_INDEX = 0;  //id
    protected static final int USER_INDEX = 1; //email
    protected static final int NICK_INDEX = 2; //null
    protected static final int EMAIL_INDEX = 3; //password
    protected static final int PWD_INDEX = 4;//username
    protected static final int BIO_INDEX = 5;//null
    protected static final int PIC_INDEX = 6;//null
    protected static final int CGAME_INDEX = 7;//null
    protected static final int ONLINE_INDEX = 8; //f is online
    protected static final int WLR_TTT_INDEX = 9;//0
    protected static final int WLR_CONNECT4_INDEX = 10;//0
    protected static final int WLR_CHECKERS_INDEX = 11;
    protected static final int RATING_TTT_INDEX = 12;
    protected static final int RATING_CONNECT4_INDEX = 13;
    protected static final int RATING_CHECKERS_INDEX = 14;
    protected static final int RANK_TTT_INDEX = 15;
    protected static final int RANK_CONNECT4_INDEX = 16;
    protected static final int RANK_CHECKERS_INDEX = 17;
    protected static final int WINS_TTT_INDEX = 18;
    protected static final int WINS_CONNECT4_INDEX = 19;
    protected static final int WINS_CHECKERS_INDEX = 20;
    protected static final int GHIST_INDEX = 21;
    protected static final int ACHIVPROG_INDEX = 22;
    protected static final int FRIENDS_INDEX = 23;
    protected static final int FREQUEST_INDEX = 24;

    /**
     * Opens CSV file (from networking): A list of one profile and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * @return
     */
    public static ArrayList<String> openSingleProfileFile(String filePath){
        ArrayList<String> fields = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            // Read and process the line
            String line;
            br.readLine();
            line = br.readLine();
            // Split the line by csv section and store each field in an ArrayList<Object>
            String section = "";
//            boolean inSection = false;
//            boolean inSection2 = false;
//            for (int j = 0; j < line.length(); j ++){
//                Character c = Character.valueOf(line.charAt(j));
//                if (c == '[') {
//                    inSection = true;
//                }else if ((c == '{') && !inSection){
//                    inSection2 = true;
//                } else if ((c == ',' && (!inSection || !inSection2))) {
//                    fields.add(section);
//                    section = "";
//                }else if (c == ']') {
//                    inSection = false;
//                }else if ((c == '}' )&& inSection2){
//                    inSection2 = false;
//                } else if (j == line.length() - 1) {
//                    section = section + c;
//                    fields.add(section);
//                }else if (c != '"') {
//                    section = section + c;
//                }
            boolean inSection = false;
            boolean firstOpenBracket = true;
            for (int j = 0; j < line.length(); j ++) {
                Character c = Character.valueOf(line.charAt(j));
                if (c == '{') {
                    inSection = true;
                    firstOpenBracket = true;
                } else if (c == ',' && !inSection) {
                    fields.add(section);
                    section = "";
                } else if (c == '}') {
                    inSection = false;
                } else if (j == line.length() - 1) {
                    section = section + c;
                } else if ( c != '"') {
                    section = section + c;
                }
            }
            br.close();

            System.out.println("ID: " + fields.get(ID_INDEX)
                    + " Username: " + fields.get(USER_INDEX)
                    + " Nickname: " + fields.get(NICK_INDEX)
                    + " Email: " + fields.get(EMAIL_INDEX)
                    + " HashedPassword: " + fields.get(PWD_INDEX)
                    + " Bio: " + fields.get(BIO_INDEX)
                    + " Profile pic: " + fields.get(PIC_INDEX)
                    + " Current game: " + fields.get(CGAME_INDEX)
                    + " IsOnline: " + fields.get(ONLINE_INDEX)
                    + " WLR TTT: " + fields.get(WLR_TTT_INDEX)
                    + "WLR C4:" + fields.get(WLR_CONNECT4_INDEX)
                    + "WLR C4:" + fields.get(WLR_CHECKERS_INDEX)
                    + " Rating: " + fields.get(RATING_TTT_INDEX)
                    + " Rating: " + fields.get(RATING_CONNECT4_INDEX)
                    + " Rating: " + fields.get(RATING_CHECKERS_INDEX)
                    + " Rank: " + fields.get(RANK_TTT_INDEX)
                    + " Rank: " + fields.get(RANK_CONNECT4_INDEX)
                    + " Rank: " + fields.get(RANK_CHECKERS_INDEX)
                    + " Wins: " + fields.get(WINS_TTT_INDEX)
                    + " Wins: " + fields.get(WINS_CONNECT4_INDEX)
                    + " Wins: " + fields.get(WINS_CHECKERS_INDEX)
                    + " GameHistory: " + fields.get(GHIST_INDEX)
                    + " AchievementProgress: " + fields.get(ACHIVPROG_INDEX)
                    + " Friends: " + fields.get(FRIENDS_INDEX)
                    + " FriendRequests: " + fields.get(FREQUEST_INDEX)
                    + " Creation Time: " + fields.get(FREQUEST_INDEX + 1)
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
    public static ArrayList<ArrayList<String>> openProfilesFile(String filePath) {
        ArrayList<ArrayList<String>> fields = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            int i = 0;
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<String> fieldsList = new ArrayList<>();
                String section = "";
                boolean inSection = false;
                for (int j = 0; j < line.length(); j ++){
                    Character c = Character.valueOf(line.charAt(j));
                    if (c == '['){
                        inSection = true;
                    } else if (c == ',' && !inSection){
                        fieldsList.add(section);
                        section = "";
                    }else if (c == ']') {
                        inSection = false;
                    } else if (j == line.length()-1) {
                        section = section + c;
                        fieldsList.add(section);
                    }else if (c != '"'){
                        section = section + c;
                    }
                }
                fields.add(fieldsList);

//                    System.out.println("ID: " + fields.get(i).get(ID_INDEX)
//                            + " Username: " + fields.get(i).get(USER_INDEX)
//                            + " Nickname: " + fields.get(i).get(NICK_INDEX)
//                            + " Email: " + fields.get(i).get(EMAIL_INDEX)
//                            + " HashedPassword: " + fields.get(i).get(PWD_INDEX)
//                            + " Bio: " + fields.get(i).get(BIO_INDEX)
//                            + " Profile pic: " + fields.get(i).get(PIC_INDEX)
//                            + " Current game: " + fields.get(i).get(CGAME_INDEX)
//                            + " IsOnline: " + fields.get(i).get(ONLINE_INDEX)
//                            + " WLR: " + fields.get(i).get(WLR_INDEX)
//                            + " Rating: " + fields.get(i).get(RATING_INDEX)
//                            + " Rank: " + fields.get(i).get(RANK_INDEX)
//                            + " Wins: " + fields.get(i).get(WINS_INDEX)
//                            + " GameHistory: " + fields.get(i).get(GHIST_INDEX)
//                            + " AchievementProgress: " + fields.get(i).get(ACHIVPROG_INDEX)
//                            + " Friends: " + fields.get(i).get(FRIENDS_INDEX)
//                            + " FriendRequests: " + fields.get(i).get(FREQUEST_INDEX)
//                            + " Creation Time: " + fields.get(i).get(17)
//                    );
                    i += 1;
                }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("System can't find file");
        }
        return fields;
    }

    /**
     * Takes an ArrayList<ArrayList<String>> of all profiles in the database and rewrites CSV for new changes to be added.
     * @param filePath
     * @param profiles
     */
    public static void writeProfilesFile(String filePath, ArrayList<ArrayList<String>> profiles){
        for (int i = 0; i < profiles.size(); i ++) {
            String csvLine = "";
            for (int j = 0; j < profiles.get(i).size(); j ++){

            switch (j){
                case BIO_INDEX, GHIST_INDEX, ACHIVPROG_INDEX, FRIENDS_INDEX, FREQUEST_INDEX:
                        csvLine += "[" + profiles.get(i).get(j) + "]";
                        break;
                }
                csvLine += profiles.get(i).get(j) + ",";
            }
        }
    }

    public static void main(String[] args) {
        openSingleProfileFile("player_profile_19.csv");
    }
}