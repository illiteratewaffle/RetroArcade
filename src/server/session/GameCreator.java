//package server.session;
//
//import server.player.Player;
//import server.management.ThreadMessage;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class GameCreator {
//    private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
//    private final CopyOnWriteArrayList<Player> playerList;
//
//    /**
//     * Constructor for the GameCreator class
//     * @param queue the main message cue object that will be used to communicate between threads.
//     * @param playerList the thread safe ArrayList that the Player objects are in
//     */
//    public GameCreator(ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue, CopyOnWriteArrayList<Player> playerList) {
//        this.queue = queue;
//        this.playerList = playerList;
//    }
//
//    /**
//     * The function that the thread runs
//     */
//    public void run() {
//        // Just run at all times (prolly not a good idea but still at an early state of coding)
//        while (true) {
//            // Waits until .notify has been called (when playerList is updated)
//            synchronized (playerList) {
//                try {
//                    playerList.wait();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            Player player1 = null;
//            Player player2 = null;
//            for (Player player : playerList) {
//                if (player.getStatus() == Player.PlayerStatus.WAITING) {
//                    if (player1 == null) {
//                        player1 = player;
//                    } else {
//                        player2 = player;
//                        break;
//                    }
//                }
//            }
//            if (player2 != null) {
//                // Create the GameSessionManager object
//                // Thread.ofVirtual().start(new GameSessionManager(player1, player2, queue));
//            }
//        }
//    }
//}
