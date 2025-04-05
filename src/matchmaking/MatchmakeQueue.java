package matchmaking;

import java.util.*;

// Queue class to manage matchmaking queue for different games
public class MatchmakingQueue {
    private Map<String, LinkedList<Player>> gameQueues;

    public MatchmakingQueue() {
        this.gameQueues = new HashMap<>();
    }

    /***
     * Adds player into specific gametype queue
     * @param player Player to be added into queue
     * @param gameType Game type for which players want to be added into queue for
     */
    public void enqueue(Player player, String gameType) {
        gameQueues.putIfAbsent(gameType, new LinkedList<Player>());
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
    public Player dequeue(Player player, String gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            Player player = gameQueues.get(gameType).poll();
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

    private LinkedList<Player> quickSortHelper(LinkedList<Player> list) {
        if (list.size() <= 1) {
            return list;
        }

        Player pivot = list.get(list.size() / 2);
        LinkedList<Player> lesser = new LinkedList<Player>();
        LinkedList<Player> greater = new LinkedList<Player>();
        LinkedList<Player> equal = new LinkedList<Player>();

        for (Player player : list) {
            if (player.getRank() > pivot.getRank()) {
                greater.add(player);
            } else if (player.getRank() < pivot.getRank()) {
                lesser.add(player);
            } else {
                equal.add(player);
            }
        }

        LinkedList<Player> sortedList = new LinkedList<Player>();
        sortedList.addAll(quickSortHelper(greater));
        sortedList.addAll(equal);
        sortedList.addAll(quickSortHelper(lesser));

        return sortedList;
    }

}
