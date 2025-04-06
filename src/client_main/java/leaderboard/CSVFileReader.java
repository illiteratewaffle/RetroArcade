package leaderboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFileReader {

    // INDEXES FROM CSV FILE
    private static final int ID_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private static final int WLR_CHECKERS_INDEX = 11;
    private static final int WLR_TTT_INDEX = 9;
    private static final int WLR_C4_INDEX = 10;
    private static final int RATING_CHECKERS_INDEX = 14;
    private static final int RATING_TTT_INDEX = 12;
    private static final int RATING_C4_INDEX = 13;
    private static final int WINS_CHECKERS_INDEX = 20;
    private static final int WINS_TTT_INDEX = 18;
    private static final int WINS_C4_INDEX = 19;
    private static final int PROFILE_ROW_SIZE = 26;
    private static int numberOfProfiles = 0;

    /**
     * Method to open csv file and returns a 2d arraylist of only profile statistics
     * format: [[id1, wlr1, rating1, wins1], [id2, wlr2, rating2, wins2], ...]
     *
     * @return
     */
    public static ArrayList<ArrayList<String>> retrieveProfiles(String filePath) {
        ArrayList<ArrayList<String>> fields = openFile(filePath);
        ArrayList<ArrayList<String>> sortedFields = sortFields(fields);

        return sortedFields;
    }

    /**
     * Opens CSV file (from networking): A list of all profiles and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * update: METHOD COPIED FROM AUTHENTICATION AND PROFILE'S CODE - SOME FIELDS IN THE CSV HAVE COMMAS WITHIN AND
     * I DIDNT ACCOUNT FOR IT. BUT THEY DID
     *
     * @return
     */
    public static ArrayList<ArrayList<String>> openFile(String filePath){
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
                    }else if ( c != '"'){
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
                i +=1;
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("System can't find file");
        }

        //System.out.println(fields);
        return fields;
    }

    /**
     * from 2d arraylist of all profiles, makes another 2d arraylist of ONLY PROFILE STATISTICS
     * format: [[id, username, rating ttt, rating c4, rating checkers, wins tt, wins c4, wins checkers, wlr ttt, wlr c4, wlr checkers], ...]
     *
     * @param fields
     * @return
     */
    public static ArrayList<ArrayList<String>> sortFields(ArrayList<ArrayList<String>> fields) {
        ArrayList<ArrayList<String>> sortedFields = new ArrayList<>();

        // i_profile is number of profiles
        // statistic represents a profile's field
        for (int i_profile = 0; i_profile < fields.size(); i_profile++) {
            ArrayList<String> individual = new ArrayList<String>();
            for (int statistic = 0; statistic < PROFILE_ROW_SIZE; statistic++) {
                toNewList(fields, i_profile, individual, statistic, ID_INDEX, USERNAME_INDEX, RATING_TTT_INDEX, RATING_C4_INDEX, RATING_CHECKERS_INDEX);
                toNewList(fields, i_profile, individual, statistic, WINS_TTT_INDEX, WINS_C4_INDEX, WINS_CHECKERS_INDEX, WLR_TTT_INDEX, WLR_C4_INDEX);
                if (statistic == WLR_CHECKERS_INDEX) {
                    // add to index 10
                    individual.add(fields.get(i_profile).get(WLR_CHECKERS_INDEX));
                }
            }

            sortedFields.add(individual);
        }

        //System.out.println(sortedFields);

        return sortedFields;
    }

    private static void toNewList(ArrayList<ArrayList<String>> fields, int i_profile, ArrayList<String> individual, int statistic, int idIndex, int usernameIndex, int ratingTttIndex, int ratingC4Index, int ratingCheckersIndex) {
        if (statistic == idIndex) {

            individual.add(fields.get(i_profile).get(idIndex));
        }
        if (statistic == usernameIndex) {

            individual.add(fields.get(i_profile).get(usernameIndex));
        }
        if (statistic == ratingTttIndex) {

            individual.add(fields.get(i_profile).get(ratingTttIndex));
        }
        if (statistic == ratingC4Index) {

            individual.add(fields.get(i_profile).get(ratingC4Index));
        }
        if (statistic == ratingCheckersIndex) {

            individual.add(fields.get(i_profile).get(ratingCheckersIndex));
        }
    }
}