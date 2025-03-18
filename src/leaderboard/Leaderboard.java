package leaderboard;

import AuthenticationAndProfile.Profile;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
    private ArrayList<Profile> rankings;

    public Leaderboard() {

    }

    public ArrayList<Profile> getTopPlayers(){

        return rankings;
    }

    public void updateLeaderboard(){

    }

    public ArrayList<Profile> sortPlayers() {

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
}
