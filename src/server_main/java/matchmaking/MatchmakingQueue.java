package matchmaking;

import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.*;

import AuthenticationAndProfile.PlayerRanking;
import player.PlayerHandler;

public class MatchmakingQueue {
    private static final Map<Integer, LinkedList<PlayerHandler>> gameQueues = new HashMap<>();

    //Static block to run once the class is initialized
    static {
        gameQueues.put(0, new LinkedList<>()); // TTT
        gameQueues.put(1, new LinkedList<>()); // Connect 4
        gameQueues.put(2, new LinkedList<>()); // Checkers
    }

    /***
     * Get specific gameType LinkedList
     * @param gameType Specific game linkedList
     * @return linkedList
     */
    public static LinkedList<PlayerHandler> getQueue(int gameType) {
        return gameQueues.get(gameType);
    }

    /***
     * Returns the size of the LinkedList for TTT
     * @return size of TTT LinkedList
     */
    public static int getQueueWaitTTT() {
        return gameQueues.get(0).size();
    }

    /***
     * Returns the size of the LinkedList for Connect4
     * @return size of the Con4 LinkedList
     */
    public static int getQueueWaitCon4() {
        return gameQueues.get(1).size();
    }


    /***
     * Returns the size of the LinkedList for Checkers
     * @return size of the Checkers LinkedList
     */
    public static int getWaitCheckers() {
        return gameQueues.get(2).size();
    }

    /***
     * Adds PlayerHandler into LinkedList depending on the game they wanted to put in
     * @param handler PlayerHandler object
     * @param gameType gameType specifying which LinkedList to add the PlayerHandler into
     */
    public static void enqueue(PlayerHandler handler, int gameType) throws SQLException {
        gameQueues.get(gameType).add(handler);
        if (gameQueues.get(gameType).size() > 1) {
            System.out.println(gameQueues.get(gameType).size());
            quickSort(gameType);
        }
    }

    /**
     * Checks if a specific player is currently present in any of the matchmaking queues.
     * @param player The PlayerHandler object to check for.
     * @return true if the player is found in any of the queues, false otherwise.
     */
    public static Boolean isInQueue(PlayerHandler player) {
        for (Map.Entry<Integer, LinkedList<PlayerHandler>> entry : gameQueues.entrySet()) {     //Loop through each queue
            if (entry.getValue().contains(player)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Dequeues a specific player from the matchmaking queue for a given game type.
     * @param player   The PlayerHandler of the player to dequeue.
     * @param gameType An integer representing the game type from which to dequeue the player.
     */
    public static void dequeue(PlayerHandler player, int gameType) {
        if (gameQueues.containsKey(gameType) & !gameQueues.get(gameType).isEmpty()) {
            gameQueues.get(gameType).remove(player);
        }
    }

    /**
     * Dequeues a specific player from any of the game queues.
     * It iterates through all game queues and removes the player if found. Then break
     * @param handler The PlayerHandler of the player to dequeue.
     */
    public static void dequeue(PlayerHandler handler) {
        for (Map.Entry<Integer, LinkedList<PlayerHandler>> entry : gameQueues.entrySet()) {
            if (entry.getValue().remove(handler)) {
                return; // Exit method if handler was found and removed
            }
        }
    }

    /**
     * Dequeues and returns the first player waiting in the matchmaking queue for a specific game type.
     * @param gameType An integer representing the game type.
     * @return The PlayerHandler of the first player in the queue for the specified game type,
     * or null if the queue for that game type is empty or does not exist.
     */
    public static PlayerHandler dequeue(int gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            return gameQueues.get(gameType).removeFirst();
        }
        return null;
    }

    /**
     * Retrieves, but does not remove, the first player waiting in the matchmaking queue for a specific game type.
     * @param gameType An integer representing the game type.
     * @return The PlayerHandler of the first player in the queue for the specified game type,
     * null if the queue for that game type is empty or does not exist.
     */
    public static PlayerHandler peek(int gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            return gameQueues.get(gameType).peek();
        }
        return null;
    }

    /***
     * Get size of the queue for a specific gameType
     * @param gameType Integer representing the gameType
     * @return The size of the
     */
    public static int size(int gameType) {
        return gameQueues.get(gameType).size();
    }

    /**
     * Sorts the matchmaking queue for a specific game type using the QuickSort algorithm.
     * This method replaces the existing queue for the given game type with the sorted version.
     * @param gameType An integer representing the game type of the queue to be sorted.
     */
    private static void quickSort(int gameType) throws SQLException {

        if (gameQueues.containsKey(gameType)) {
            System.out.println(gameQueues);
            gameQueues.put(gameType, quickSortHelper(gameQueues.get(gameType), gameType));
        }
        System.out.println(gameQueues);
    }

    /**
     * Helper function for QuickSort that recursively sorts a linked list of PlayerHandler objects
     * based on their player rating for a specific game type.
     * @param list     The LinkedList of PlayerHandler objects to be sorted.
     * @param gameType The integer representing the game type used to retrieve the player's rating.
     * @return A new LinkedList containing the PlayerHandler objects sorted in ascending order of their rating for the given game type.
     */
    private static LinkedList<PlayerHandler> quickSortHelper(LinkedList<PlayerHandler> list, int gameType) throws SQLException {
        if (list == null || list.size() <= 1) return list; // <--- add null check

        int size = list.size();
        PlayerHandler pivot = list.get(size / 2); // safe now

        LinkedList<PlayerHandler> lesser = new LinkedList<>();
        LinkedList<PlayerHandler> greater = new LinkedList<>();
        LinkedList<PlayerHandler> equal = new LinkedList<>();

        int pivotRank = PlayerRanking.getRating(pivot.getProfile().getID(), gameType);

        for (PlayerHandler handler : list) {
            int rank = PlayerRanking.getRating(handler.getProfile().getID(), gameType);
            if (rank > pivotRank) {
                greater.add(handler);
            } else if (rank < pivotRank) {
                lesser.add(handler);
            } else {
                equal.add(handler);
            }
        }

        LinkedList<PlayerHandler> sorted = new LinkedList<>();
        sorted.addAll(quickSortHelper(greater, gameType));
        sorted.addAll(equal);
        sorted.addAll(quickSortHelper(lesser, gameType));
        return sorted;
    }

}
