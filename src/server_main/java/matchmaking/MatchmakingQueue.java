package matchmaking;

import java.util.*;
import player.PlayerHandler;

public class MatchmakingQueue {
    private static final Map<Integer, LinkedList<PlayerHandler>> gameQueues = new HashMap<>();

    static {
        gameQueues.put(0, new LinkedList<>()); // TTT
        gameQueues.put(1, new LinkedList<>()); // Connect 4
        gameQueues.put(2, new LinkedList<>()); // Checkers
    }

    /***
     *
     * @param gameType
     * @return
     */
    public static LinkedList<PlayerHandler> getQueue(int gameType) {
        return gameQueues.getOrDefault(gameType, null);
    }

    /***
     * Returns the size of the queue for TTT
     * @return int
     */
    public static int getQueueWaitTTT() {
        return gameQueues.get(0).size();
    }

    /***
     *
     * @return
     */
    public static int getQueueWaitCon4() {
        return gameQueues.get(1).size();
    }


    /***
     *
     * @return
     */
    public static int getWaitCheckers() {
        return gameQueues.get(2).size();
    }

    /***
     *
     * @param handler
     * @param gameType
     */
    public static void enqueue(PlayerHandler handler, int gameType) {
        gameQueues.get(gameType).add(handler);
        quickSort(gameType);
    }

    /***
     *
     * @param gameType
     */
    public static void dequeue(PlayerHandler player, int gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).remove(player)) {
            gameQueues.get(gameType).removeFirst();
        }
    }

    public static void dequeue(PlayerHandler handler) {
        for (Map.Entry<Integer, LinkedList<PlayerHandler>> entry : gameQueues.entrySet()) {
            if (entry.getValue().remove(handler)) {
                return;
            }
        }
    }

    /***
     *
     * @param gameType
     * @return
     */
    public static PlayerHandler dequeue(int gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            return gameQueues.get(gameType).removeFirst();
        }
        return null;
    }

    /***
     *
     * @param gameType
     * @return
     */
    public static PlayerHandler peek(int gameType) {
        if (gameQueues.containsKey(gameType) && !gameQueues.get(gameType).isEmpty()) {
            return gameQueues.get(gameType).peek();
        }
        return null;
    }

    /***
     *
     * @param gameType
     * @return
     */
    public static int size(int gameType) {
        return gameQueues.get(gameType).size();
    }

    /***
     *
     * @param gameType
     */
    private static void quickSort(int gameType) {
        if (gameQueues.containsKey(gameType)) {
            gameQueues.put(gameType, quickSortHelper(gameQueues.get(gameType), gameType));
        }
    }

    /***
     *
     * @param list
     * @param gameType
     * @return
     */
    private static LinkedList<PlayerHandler> quickSortHelper(LinkedList<PlayerHandler> list, int gameType) {
        if (list.size() <= 1) return list;

        PlayerHandler pivot = list.get(list.size() / 2);
        LinkedList<PlayerHandler> lesser = new LinkedList<>();
        LinkedList<PlayerHandler> greater = new LinkedList<>();
        LinkedList<PlayerHandler> equal = new LinkedList<>();

        int pivotRank = pivot.getProfile().getPlayerRanking().getRating(gameType);

        for (PlayerHandler handler : list) {
            int rank = handler.getProfile().getPlayerRanking().getRating(gameType);

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
