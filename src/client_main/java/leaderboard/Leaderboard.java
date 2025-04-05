package leaderboard;

//import server_main.AuthenticationAndProfile.Profile;
import server.player.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


public class Leaderboard {

    private static final String FILEPATH = "profiles_export.csv";
    private ArrayList<ArrayList<String>> rankings; // to be deleted later

    private String sortChoice = "RATING";
    private String sortGame = "CHECKERS";

    private static final int ID_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private static final int WLR_TTT_INDEX = 2;
    private static final int WLR_C4_INDEX = 3;
    private static final int WLR_CHECKERS_INDEX = 4;
    private static final int RATING_TTT_INDEX = 5;
    private static final int RATING_C4_INDEX = 6;
    private static final int RATING_CHECKERS_INDEX = 7;
    private static final int WINS_TTT_INDEX = 8;
    private static final int WINS_C4_INDEX = 9;
    private static final int WINS_CHECKERS_INDEX = 10;



    private static final String GAME_CHECKERS = "CHECKERS";
    private static final String GAME_TTT = "TTT";
    private static final String GAME_C4 = "C4";
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

    public void updateLeaderboard() {


        //PlayerManager.getProfileTable(); BRING BACK WHEN COMPLETELY DONE LEADERBOARD

        this.rankings = CSVFileReader.retrieveProfiles(FILEPATH);

        this.rankings = getTopPlayers(sortChoice, sortGame);

        rankings = toggleSortOrder(rankings);
        System.out.println("toggled " + rankings);
    }

    public ArrayList<ArrayList<String>> getTopPlayers(String sortChoice, String sortGame) {

        if (Objects.equals(sortChoice, SORT_RATING)) {

            rankings = sortByRating(rankings, sortGame);
            System.out.println(rankings);

        } else if (Objects.equals(sortChoice, SORT_WLR)) {

            rankings = sortByWLR(rankings, sortGame);
            System.out.println(rankings);

        } else if (Objects.equals(sortChoice, SORT_WINS)) {

            rankings = sortByWins(rankings, sortGame);
            System.out.println(rankings);

        } else {
            System.out.println("select a valid sorting option: rating, win/loss ratio, wins");
        }

        return rankings;
    }

    private void sort(String sortChoice, String sortGame) {

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
    public ArrayList<ArrayList<String>> sortByRating(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_CHECKERS_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_TTT_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_C4_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        }

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
    public ArrayList<ArrayList<String>> sortByWLR(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_CHECKERS_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_TTT_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_C4_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        }

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
    public ArrayList<ArrayList<String>> sortByWins(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_CHECKERS_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_TTT_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_C4_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        }

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
