package client_main.java.leaderboardTests;


import leaderboard.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardTest {


    @Test
    void testSortByRating_Checkers() {
        // For game CHECKERS, sortByRating uses index RATING_CHECKERS_INDEX (index 7).
        // Profile 1 (Alice): 1400, Profile 2 (Bob): 1500.
        // Expected descending order: Bob then Alice.
        //ArrayList<ArrayList<String>> result = leaderboard.sortByRating(testData, "CHECKERS");
        // The username is at index 2 in each profile.
        System.out.println("hi");
    }

}
