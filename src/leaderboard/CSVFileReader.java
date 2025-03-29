package leaderboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFileReader {
    private static final String FILEPATH = "src/leaderboard/profiles.csv"; //change this depending on where .csv is exported to

    /*
    ID: index 0
    WLR: index 9
    Rating: index 10
    Wins: index 12
     */
    private static final int ID_INDEX = 0;
    private static final int WLR_INDEX = 9;
    private static final int RATING_INDEX = 10;
    private static final int WINS_INDEX = 12;

    public static void main(String[] args) {
        ArrayList<Object> fields = openFile();
    }

    /**
     * Opens CSV file (from networking): A list of all profiles and their information
     * Puts it into ArrayList
     * Returns said ArrayList
     *
     * @return
     */
    public static ArrayList<Object> openFile(){
        ArrayList<Object> fields = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
            // Read the header line
            String header = br.readLine();
            System.out.println("Header: " + header);

            // Read and process each subsequent line
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by ", " and store each field in an ArrayList<Object>
                String[] fieldsList = line.split(",");

                for (String fieldsString : fieldsList) {
                    fields.add(fieldsString.trim());
                }

                System.out.println("ID: " + fields.get(ID_INDEX)
                        + ", WLR: " + fields.get(WLR_INDEX)
                        + ", Rating: " + fields.get(RATING_INDEX)
                        + ", Wins: " + fields.get(WINS_INDEX));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("System can't find file");
        }

        return fields;
    }
}