package matchmaking;

import java.util.*;

// Queue class to manage matchmaking queue for different games
public class MatchmakingQueue {
    private Map<String, LinkedList<Profile>> gameQueues;

    public MatchmakingQueue() {
        this.gameQueues = new HashMap<>();
    }

    public LinkedList<Profile> getQueue(String gameType) {
        return gameQueues.getOrDefault(gameType, new LinkedList<>());
    }

    /***
     * Adds player into specific gametype queue
     * @param player Player to be added into queue
     * @param gameType Game type for which players want to be added into queue for
     */
    public void enqueue(Profile player, String gameType) {
        gameQueues.putIfAbsent(gameType, new LinkedList<Profile>());
        gameQueues.get(gameType).add(player);
        quickSort(gameQueues.get(gameType));
        //Testing purposes
        System.out.println(player.getName() + " has joined the " + gameType + " queue.");
    }

    /***
     * Removes player from queue upon matching players or when a player wants to be removed from queue
     * @param gameType The queue from which the player is already in queue
     * @return Player that has been dequeued or null if queue is empty
     */
    public Profile dequeue(Player player, String gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            Profile player = gameQueues.get(gameType).poll();
            //Testing purposes
            System.out.println(player.getName() + " has been matched for " + gameType + ".");
            return player;
        }
        //Test
        System.out.println("No players in " + gameType + " queue.");
        return null;
    }

    /***
     * Sorts queue
     * @param gameType sorts specific game queue
     */
    public void quickSort(String gameType) {

        if (gameQueues.containsKey(gameType)) {
            gameQueues.put(gameType, quickSortHelper(gameQueues.get(gameType)));
        }

    }

    /**
     * Sorts a list of Players in descending order by rank using QuickSort.
     * Recursively partitions the list around a pivot.
     * @param list list of Player objects to sort
     * @return sorted LinkedList of Players
     */
    private LinkedList<Profile> quickSortHelper(LinkedList<Player> list) {
        if (list.size() <= 1) {
            return list;
        }

        Profile pivot = list.get(list.size() / 2);
        LinkedList<Profile> lesser = new LinkedList<Profile>();
        LinkedList<Profile> greater = new LinkedList<Profile>();
        LinkedList<Profile> equal = new LinkedList<Profile>();


        for (Profile player : list) {
            if (player.obtainPlayerRanking().getRanking(gametype) > pivot.obtainPlayerRanking().getRanking(gametype)) {
                greater.add(player);
            } else if (player.obtainPlayerRanking().getRanking(gametype) < pivot.obtainPlayerRanking().getRanking(gametype) {
                lesser.add(player);
            } else {
                equal.add(player);
            }
        }

        LinkedList<Profile> sortedList = new LinkedList<Profile>();
        sortedList.addAll(quickSortHelper(greater));
        sortedList.addAll(equal);
        sortedList.addAll(quickSortHelper(lesser));

        return sortedList;
    }

}
