package matchmaking;

import java.util.*;

// Queue class to manage matchmaking queue for different games
public class MatchmakingQueue {
    private Map<String, LinkedList<Profile>> gameQueues;

    public MatchmakingQueue() {
        this.gameQueues = new HashMap<>();
    }

    public LinkedList<Profile> getQueue(String gameType) {
        if (gameQueues.get(gameType) != null){
            return gameQueues.get(gameType);
        } else {
            return null;
        }
    }

    public int getQueueWaitTTT(){
        return gameQueues.get("TTT").size();
    }

    public int getQueueWaitCon4(){
        return gameQueues.get("Connect4").size();
    }

    public int getWaitCheckers(){
        return gameQueues.get("Checkers").size();
    }

    /***
     * Adds player into specific gameType queue
     * @param player Player to be added into queue
     * @param gameType Game type for which players want to be added into queue for
     */
    public void enqueue(Profile player, String gameType) {
        gameQueues.putIfAbsent(gameType, new LinkedList<Profile>());
        gameQueues.get(gameType).add(player);
        quickSort(gameQueues.get(gameType));
    }

    /***
     * Removes player from queue upon matching players or when a player wants to be removed from queue
     * @param gameType The queue from which the player is already in queue
     * @return Player that has been dequeued or null if queue is empty
     */
    public Profile dequeue(Profile player, String gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            ArrayList<Profile> list = gameQueues.get(gameType);
            list = list.remove(player);
            return player;
        }
        return null;
    }

    /***
     * Sorts queue
     * @param gameType sorts specific game queue
     */
    private void quickSort(String gameType) {

        if (gameQueues.containsKey(gameType)) {
            gameQueues.put(gameType, quickSortHelper(gameQueues.get(gameType), gameType));
        }

    }

    /**
     * Sorts a list of Players in descending order by rank using QuickSort.
     * Recursively partitions the list around a pivot.
     * @param list list of Player objects to sort
     * @return sorted LinkedList of Players
     */
    private LinkedList<Profile> quickSortHelper(LinkedList<Profile> list, String gameType) {
        if (list.size() <= 1) {
            return list;
        }

        Profile pivot = list.get(list.size() / 2);
        LinkedList<Profile> lesser = new LinkedList<Profile>();
        LinkedList<Profile> greater = new LinkedList<Profile>();
        LinkedList<Profile> equal = new LinkedList<Profile>();


        for (Profile player : list) {
            if (player.obtainPlayerRanking().getRanking().getRank(gameType) > pivot.obtainPlayerRanking().getRanking().getRanking(gameType)) {
                greater.add(player);
            } else if (player.obtainPlayerRanking().getRanking().getRank(gameType) < pivot.obtainPlayerRanking().getRanking().getRanking(gameType)) {
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
