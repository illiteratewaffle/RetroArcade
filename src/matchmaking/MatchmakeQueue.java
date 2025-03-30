package matchmaking;

import java.util.*;

// Queue class to manage matchmaking queue
public class MatchmakingQueue {
    private LinkedList<Player> queue;

    public MatchmakingQueue() {
        this.queue = new LinkedList<>();
    }

    /***
     * Quick Sort linked list according to rank
     */

    public void quickSort() {
        queue = quickSortHelper(queue);
    }

    private LinkedList<Player> quickSortHelper(LinkedList<Player> list) {
        if (list.size() <= 1) {
            return list;
        }

        Player pivot = list.get(list.size() / 2);
        LinkedList<Player> lesser = new LinkedList<>();
        LinkedList<Player> greater = new LinkedList<>();
        LinkedList<Player> equal = new LinkedList<>();

        for (Player player : list) {
            if (player.getRank() > pivot.getRank()) {
                greater.add(player);
            } else if (player.getRank() < pivot.getRank()) {
                lesser.add(player);
            } else {
                equal.add(player);
            }
        }

        LinkedList<Player> sortedList = new LinkedList<>();
        sortedList.addAll(quickSortHelper(greater));
        sortedList.addAll(equal);
        sortedList.addAll(quickSortHelper(lesser));

        return sortedList;
    }
    /***
     * Adds a player to the queue
     */
    public void enqueue(Player player) {
        queue.add(player);
        queue = sortQueue(queue);
        System.out.println(player.getName() + " has joined the queue.");
    }

    /***
     * Removes and returns the next player in queue
     */
    public Player dequeue() {
        if (!queue.isEmpty()) {
            Player player = queue.poll();   //.poll() returns head of list and bring
            return player;
        }
        System.out.println("Queue is empty.");
        return null;
    }

}
