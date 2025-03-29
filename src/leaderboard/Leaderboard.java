package leaderboard;

//import server_main.AuthenticationAndProfile.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Leaderboard {

    private static final String FILEPATH = "src/leaderboard/profiles.csv";
    private ArrayList<ArrayList<String>> rankings;
    private String sortChoice = "RATING";

    private static final int WLR_INDEX = 1;
    private static final int RATING_INDEX = 2;
    private static final int WINS_INDEX = 3;
    private static final String SORT_RATING = "RATING";
    private static final String SORT_WLR = "WLR";
    private static final String SORT_WINS = "WINS";


    /**
     * HERE FOR CODING/TEST PURPOSES ONLY. TO BE DELETED WHEN MERGING
     * @param args
     */
    public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard();
    }

    public Leaderboard() {
        updateLeaderboard();
    }

    public void updateLeaderboard(){
        // String csvFilePath = get csv from networking()
        // CSVFileReader.retrieveProfiles(csvFilePath);

        this.rankings = CSVFileReader.retrieveProfiles(FILEPATH);

        this.rankings = getTopPlayers(sortChoice);

        rankings = toggleSortOrder(rankings);
        System.out.println(rankings);
    }

    public ArrayList<ArrayList<String>> getTopPlayers(String sortChoice){
        if (Objects.equals(sortChoice, SORT_RATING)) {

            rankings = sortByRating(rankings);
            System.out.println(rankings);
        } else if (Objects.equals(sortChoice, SORT_WLR)) {

            rankings = sortByWLR(rankings);
            System.out.println(rankings);
        } else if (Objects.equals(sortChoice, SORT_WINS)) {

            rankings = sortByWins(rankings);
            System.out.println(rankings);
        } else {

            System.out.println("select a valid sorting option: rating, win/loss ratio, wins");
        }

        return rankings;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the rating in descending order.
     * Each player profile is represented by an ArrayList of strings, and the rating
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by rating in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByRating(ArrayList<ArrayList<String>> profiles) {
        Collections.sort(profiles, new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                // Convert the rating (which is a String) to an integer for comparison.
                int rating1 = Integer.parseInt(p1.get(RATING_INDEX));
                int rating2 = Integer.parseInt(p2.get(RATING_INDEX));

                // Compare the ratings in descending order.
                return Integer.compare(rating2, rating1);
            }
        });

        return profiles;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the wlr in descending order.
     * Each player profile is represented by an ArrayList of strings, and the wlr
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by wlr in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByWLR(ArrayList<ArrayList<String>> profiles) {
        Collections.sort(profiles, new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                // Convert the rating (which is a String) to an integer for comparison.
                float rating1 = Float.parseFloat(p1.get(WLR_INDEX));
                float rating2 = Float.parseFloat(p2.get(WLR_INDEX));

                // Compare the ratings in descending order.
                return Float.compare(rating2, rating1);
            }
        });
        return profiles;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the wins in descending order.
     * Each player profile is represented by an ArrayList of strings, and the wins
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by wins in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByWins(ArrayList<ArrayList<String>> profiles) {
        Collections.sort(profiles, new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                // Convert "wins" (which is a String) to an integer for comparison.
                int rating1 = Integer.parseInt(p1.get(WINS_INDEX));
                int rating2 = Integer.parseInt(p2.get(WINS_INDEX));

                // Compare the ratings in descending order.
                return Integer.compare(rating2, rating1);
            }
        });
        return profiles;
    }

    /**
     * Reverses the order of player profiles in the list. This method creates a new list
     * by iterating through the given list of profiles in reverse order and adding each
     * profile to the new list. The original list remains unchanged, and the method
     * returns a new list with the profiles in reverse order.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return A new ArrayList containing the profiles in reverse order of the original list.
     */
    public ArrayList<ArrayList<String>> toggleSortOrder(ArrayList<ArrayList<String>> profiles){
        ArrayList<ArrayList<String>> reversedRankingsArray = new ArrayList<ArrayList<String>>();
        int listSize = profiles.size();
        for(int i = listSize - 1; i >= 0; i--) {
            reversedRankingsArray.add(profiles.get(i));
        }

        return reversedRankingsArray;
    }

    public ArrayList<ArrayList<String>> searchPlayer(String username){

        return rankings;
    }

//    public ArrayList<ArrayList<String>> filterFriends(ArrayList<Profile> friendList){
//
//        return rankings;
//    }

}
