package AuthenticationAndProfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ProfileCSVReader Class handles reading csv files sent from the database and converts information into ArrayLists to be accessible by
 * various methods.
 * @author Alessia Flaig
 */
public class ProfileCSVReader {
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
    protected static final int LOSSES_TTT_INDEX = 25;
    protected static final int LOSSES_CONNECT4_INDEX = 26;
    protected static final int LOSSES_CHECKERS_INDEX = 27;
    protected static final int TOTAL_TTT_INDEX = 28;
    protected static final int TOTAL_CONNECT4_INDEX = 29;
    protected static final int TOTAL_CHECKERS_INDEX = 30;


    /**
     * Opens CSV file (from networking): A list of one profile and their information. This method puts it into ArrayList.
     *
     * @return ArrayList<String></String>
     */
    public static ArrayList<String> openSingleProfileFile(String filePath) throws IOException, NullPointerException {
        ArrayList<String> fields = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            // Read and process the line
            String line;
            br.readLine();
            line = br.readLine();
            if (line != null) {
                // Split the line by csv section and store each field in an ArrayList<Object>
                String section = "";
                boolean inSection = false;
                for (int j = 0; j < line.length(); j++) {
                    Character c = Character.valueOf(line.charAt(j));
                    if (c == '{') {
                        inSection = true;
                    } else if (c == ',' && !inSection) {
                        fields.add(section);
                        section = "";
                    } else if (c == '}') {
                        inSection = false;
                    } else if (j == line.length() - 1) {
                        section = section + c;
                        fields.add(section);
                    } else if (c != '"') {
                        section = section + c;
                    }
                }
                br.close();
            } else {
                throw new NullPointerException("CSV is empty");
            }
        } catch (IOException e) {
            throw new IOException("System can't find file");
        }
        return fields;
    }

    /**
     * openProfilesFiles(String filePath) method used to read profile csv's that contain all ids in the database.
     * @param filePath
     * @return ArrayList<ArrayList<String> of all profiles in the database
     */
    public static ArrayList<ArrayList<String>> openProfilesFile(String filePath) throws IOException{
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
                    if (c == '{'){
                        inSection = true;
                    } else if (c == ',' && !inSection){
                        fieldsList.add(section);
                        section = "";
                    }else if (c == '}') {
                        inSection = false;
                    } else if (j == line.length()-1) {
                        section = section + c;
                        fieldsList.add(section);
                    }else if (c != '"'){
                        section = section + c;
                    }
                }
                fields.add(fieldsList);
                i += 1;
            }
            br.close();
        } catch (IOException e){
            throw new IOException("System can't find file");
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
                        csvLine += "{" + profiles.get(i).get(j) + "}";
                        break;
                }
                csvLine += profiles.get(i).get(j) + ",";
            }
        }
    }
}