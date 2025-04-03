package leaderboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFileReader {

    // INDEXES FROM CSV FILE
    private static final int ID_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private static final int WLR_INDEX = 9;
    private static final int RATING_INDEX = 10;
    private static final int WINS_INDEX = 12;
    private static final int PROFILE_ROW_SIZE = 16;
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
     * @return
     */
    public static ArrayList<ArrayList<String>> openFile(String filePath){
        ArrayList<ArrayList<String>> fields = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the header line
            String header = br.readLine();
            //System.out.println("Header: " + header);

            // Read and process each subsequent line
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<String> field = new ArrayList<>();

                // Split the line by ", " and store each field in an ArrayList<>
                String[] fieldsList = line.split(",");

                for (String fieldsString : fieldsList) {
                    field.add(fieldsString.trim());
                }

                fields.add(field);

                numberOfProfiles++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("System can't find file");
        }

        //System.out.println(fields);
        return fields;
    }

    /**
     * from 2d arraylist of all profiles, makes another 2d arraylist of only profile statistics
     * format: [[id1, wlr1, rating1, wins1], [id2, wlr2, rating2, wins2], ...]
     *
     * @param fields
     * @return
     */
    public static ArrayList<ArrayList<String>> sortFields (ArrayList<ArrayList<String>> fields) {
        ArrayList<ArrayList<String>> sortedFields = new ArrayList<>();

        // i_profile is number of profiles
        // statistic represents a profile's field
        for (int i_profile = 0; i_profile < fields.size(); i_profile++) {
            ArrayList<String> individual = new ArrayList<String>();
            for (int statistic = 0; statistic < PROFILE_ROW_SIZE; statistic++) {
                if (statistic == ID_INDEX) {
                    // add to index 0
                    individual.add(fields.get(i_profile).get(statistic));
                }
                if (statistic == USERNAME_INDEX) {
                    // add to index 1
                    individual.add(fields.get(i_profile).get(statistic));
                }
                if (statistic == WLR_INDEX) {
                    // add to index 2
                    individual.add(fields.get(i_profile).get(statistic));
                }
                if (statistic == RATING_INDEX) {
                    // add to index 3
                    individual.add(fields.get(i_profile).get(statistic));
                }
                if (statistic == WINS_INDEX) {
                    // add to index 4
                    individual.add(fields.get(i_profile).get(statistic));
                }
            }

            sortedFields.add(individual);
        }

        //System.out.println(sortedFields);

        return sortedFields;
    }
}