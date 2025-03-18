package leaderboard;

import AuthenticationAndProfile.Profile;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
    private ArrayList<Profile> rankings;

    /**
     * HERE FOR CODING/TEST PURPOSES ONLY. TO BE DELETED WHEN MERGING
     * @param args
     */
    public static void main(String[] args) {

    }

    public Leaderboard() {

    }

    public ArrayList<Profile> getTopPlayers(){

        return rankings;
    }

    public void updateLeaderboard(){

    }

    public ArrayList<Profile> sortByRating() {
        // get the list of players from database, strip the players' rating

        //sort()
        return rankings;
    }

    public ArrayList<Profile> sortByWLR() {
        // get the list of players from database, strip the players' rating

        //sort()
        return rankings;
    }

    public ArrayList<Profile> sortByWins() {
        // get the list of players from database, strip the players' rating

        //sort()
        return rankings;
    }

    public ArrayList<Profile> toggleSortOrder(){
        ArrayList<Profile> reversedRankingsArray = new ArrayList<Profile>();
        int listSize = rankings.size();
        for(int i = listSize - 1; i >= 0; i--) {
            reversedRankingsArray.add(rankings.get(i));
        }

        return reversedRankingsArray;
    }

    public ArrayList<Profile> searchPlayer(String username){

        return rankings;
    }

    public ArrayList<Profile> filterFriends(ArrayList<Profile> friendList){

        return rankings;
    }

    /**
     * Sorts an array list of integers.
     * Returns the sorted array list.
     *
     * References:
     * Learned how to sort a list of numbers using bubble sort from:
     * https://www.geeksforgeeks.org/bubble-sort-algorithm/
     *
     * @param original
     * @return
     */
    private ArrayList<Integer> sort(ArrayList<Integer> original) {
        ArrayList<Integer> sorted = new ArrayList<Integer>();
        int listSize = original.size();
        for (int i = 0; i < listSize-1; i++){
            for(int j = 0; j < listSize-i-1; j++){
                if(original.get(j) < original.get(j+1)) {
                    int temp = original.get(j);
                    sorted.set(j, original.get(j+1));
                    sorted.set(j+1, temp);
                }
            }
        }

        return sorted;
    }
}
