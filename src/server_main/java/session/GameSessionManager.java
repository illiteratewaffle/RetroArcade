package session;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;
import GameLogic_Client.testinggame.testGameController;
import management.*;
import player.PlayerHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static management.ServerLogger.log;

public class GameSessionManager implements Runnable {
    private final PlayerHandler player1;
    private final PlayerHandler player2;
    private final Integer gameType;
    private final IBoardGameController gameController;

    /**
     * The constructor for the GameSessionManager
     *
     * @param player1  The first player's PlayerHandler
     * @param player2  The second player's PlayerHandler
     * @param gameType The int representing the game type
     */
    public GameSessionManager(PlayerHandler player1, PlayerHandler player2, int gameType) {
        this.player1 = player1;
        this.player2 = player2;
        // yeah, bad practice what about it
        this.gameType = gameType;
        this.gameController = getController(gameType);
    }

    /**
     * Get the corresponding controller for the game being played
     * @param gameType the String of the game type that is going to be created
     * @return the subclass of the IGameBoardController
     */
    private IBoardGameController getController(int gameType) {
        switch (gameType) {
            case 0:
                return new GameLogic_Client.TicTacToe.TTTGameController();
            case 1:
                GameLogic_Client.Connect4.C4Controller c4 = new GameLogic_Client.Connect4.C4Controller();
                c4.start(); // Ensure Connect4 game is initialized
                return c4;
            case 2:
                return new GameLogic_Client.Checkers.CheckersController();
            case 3:
                return new testGameController();
            default:
                throw new IllegalArgumentException("GameSessionManager: Unknown game type: " + gameType);
        }
    }

    /**
     * The method that runs on the separate Thread
     */
    public void run() {
        // Register the GameSessionManager on the ThreadRegistry
        Thread currentThread = Thread.currentThread();
        BlockingQueue<ThreadMessage> myQueue = new LinkedBlockingQueue<>();
        ThreadRegistry.register(currentThread, myQueue);

        // Tell the players the thread is ready
        player1.setGameSessionManagerThread(Thread.currentThread());
        player2.setGameSessionManagerThread(Thread.currentThread());

        // Send "startGame" to the players
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "startGame");
        forward.put("data", 1);
        System.out.println(forward);
        ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
        Map<String, Object> forward2 = new HashMap<>();
        forward2.put("type", "game");
        forward2.put("command", "startGame");
        forward2.put("data", 2);
        System.out.println(forward);
        ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), forward2));

        //Set the game status pf the two players
        try {
            if (gameType == 0) {
                player1.getProfile().setCurrentGame("Tic Tac Toe");
                player2.getProfile().setCurrentGame("Tic Tac Toe");
            } else if (gameType == 1) {
                player1.getProfile().setCurrentGame("Connect Four");
                player2.getProfile().setCurrentGame("Connect Four");
            } else {
                player1.getProfile().setCurrentGame("Checkers");
                player2.getProfile().setCurrentGame("Checkers");
            }
        } catch (SQLException e) {
            ServerLogger.log("GameSessionManager: Unable to update current game status of players.",e);
        }

        log("GameSessionManager: Created " + gameType + " with players " + player1.getProfile().getUsername() +
                ":" + player1.getProfile().getID() + " and " + player2.getProfile().getUsername() + ":" +
                player2.getProfile().getID() + ".");

        // While the game is ongoing
        while (gameController.getGameOngoing()) {
            try {
                ThreadMessage threadMessage = myQueue.take();
                routeMessage(threadMessage);
            } catch (InterruptedException e) {
                log("GameSessionManager: Failed to take from own BlockingQueue.");
                // TODO: shutdown game?
            }
        }
    }

    /**
     * Handles chat messages, routing them to the other player
     * @param threadMessage the ThreadMessage received
     */
    private void handleChatMessage(ThreadMessage threadMessage) {
        Map<String, Object> content = threadMessage.getContent();
        Thread sender = threadMessage.getSender();
        // Create a response
        HashMap<String, Object> forward = new HashMap<>();
        // Make a new message of the type "chat"
        forward.put("type", "chat");
        // Put the other users message in the hashmap
        forward.put("message", content.get("message"));
        // Send to the opposite player
        if (sender == player1.getThread()) {
            forward.put("sender", player1.getProfile().getUsername());
            ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
        } else if (sender == player2.getThread()) {
            forward.put("sender", player2.getProfile().getUsername());
            ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
        } else {
            log("GameSessionManager: Chat message sender not recognized.");
        }
    }

    /**
     * Method that handles the end of a game session.
     * @param winner The player that won the game.
     */
    private void handleGameEnd(PlayerHandler winner, PlayerHandler loser) {
        try {
            //Update the players profiles based on the result of the game.
            winner.getProfile().getPlayerRanking().endOfMatchMethod(winner.getProfile().getID(), gameType, 1);
            loser.getProfile().getPlayerRanking().endOfMatchMethod(loser.getProfile().getID(), gameType, 0);

            //Set the game status of the two players to null
            winner.getProfile().setCurrentGame(null);
            loser.getProfile().setCurrentGame(null);

            //Log it
            ServerLogger.log("GameSessionManager: Game ended.");
        } catch (SQLException e) {
            //If there is an error, log it.
            log("GameSessionManager: Error while logging the winners and loser of a game session.", e);
        }

        //Once the game session has concluded, call the methods to end everything and log it.
        ServerController.endGameSession(Thread.currentThread());
        log("GameSessionManager: Game session ended.");
    }

    /**
     * The method to handle win conditions and stuff when a player disconnects.
     * @param message The Thread Message saying a disconnection has occurred.
     */
    private void handleDisconnection(ThreadMessage message) {
        //Find the thread of the player that was disconnected.
        Thread sender = message.getSender();

        //Check which player was the one that got disconnected.
        if (sender == player1.getThread()) {
            player2.setGameSessionManagerThread(null);
            handleGameEnd(player2, player1);
        } else if (sender == player2.getThread()) {
            player1.setGameSessionManagerThread(null);
            handleGameEnd(player1, player2);
        }
    }

    /**
     * Route the ThreadMessage to the corresponding function
     * @param threadMessage the ThreadMessage that is to be routed
     */
    public void routeMessage(ThreadMessage threadMessage) {
        // Get the sender and the content from the ThreadMessage
        Map<String, Object> content = threadMessage.getContent();
        Thread sender = threadMessage.getSender();
        Map<String, Object> forward = new HashMap<>();

        // This is nasty bro
        switch ((String) content.get("type")) {
            case "game":
                // Add "type":"game" to the return json
                forward.put("type", "game");
                forward.put("command", content.get("command"));
                switch ((String) content.get("command")) {
                    // If wanting to call receiveInput()
                    case "receiveInput":
                        if (content.containsKey("parameter") && content.get("parameter") instanceof List<?> parameter) {
                            Ivec2 ivec2 = new Ivec2((int) parameter.get(0), (int) parameter.get(1));
                            gameController.receiveInput(ivec2);
                        } else {
                            log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
                        }
                        break;
                    // If wanting to call removePlayer()
                    case "removePlayer":
                        if (content.containsKey("parameter") && content.get("parameter") instanceof Integer parameter) {
                            gameController.removePlayer(parameter);
                        } else {
                            log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
                        }
                        break;
                    // If wanting to call getWinner()
                    case "getWinner":
                        forward.put("data", gameController.getWinner());
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call getGameOngoing()
                    case "getGameOngoing":
                        forward.put("data", gameController.getGameOngoing());
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call getBoardCells()
                    case "getBoardCells":
                        if (content.containsKey("parameter") && content.get("parameter") instanceof Integer parameter) {
                            forward.put("data", ConverterTools.listOf2dArrayto3dlist(gameController.getBoardCells(parameter)));
                            sendMessageBack(sender, forward);
                        } else {
                            log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
                        }
                        break;
                    // If wanting to call getBoardSize()
                    case "getBoardSize":
                        forward.put("data", ConverterTools.ivecToList(gameController.getBoardSize()));
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call getCurrentPlayer()
                    case "getCurrentPlayer":
                        // TODO: THIS MUST BE DEPENDENT ON THE PLAYER CURRENTLY PLAYING
                        forward.put("data", checkTurn(sender));
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call gameOngoingChangedSinceLastCommand()
                    case "gameOngoingChangedSinceLastCommand":
                        forward.put("data", gameController.gameOngoingChangedSinceLastCommand());
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call winnersChangedSinceLastCommand()
                    case "winnersChangedSinceLastCommand":
                        forward.put("data", gameController.winnersChangedSinceLastCommand());
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call currentPlayerChangedSinceLastCommand()
                    case "currentPlayerChangedSinceLastCommand":
                        forward.put("data", gameController.currentPlayerChangedSinceLastCommand());
                        sendMessageBack(sender, forward);
                        break;
                    // If wanting to call boardChangedSinceLastCommand()
                    case "boardChangedSinceLastCommand":
                        forward.put("data", gameController.boardChangedSinceLastCommand());
                        sendMessageBack(sender, forward);
                        break;
                    case "getC4Board":
                        forward.put("data", ConverterTools.c4Piece2dArrayTo2dList(gameController.getC4Board()));
                        sendMessageBack(sender, forward);
                        break;
                    case "getC4IsGameOver":
                        forward.put("data", gameController.getC4IsGameOver());
                        sendMessageBack(sender, forward);
                        break;
                    case "getC4WinnerAsEnum":
                        forward.put("data", ConverterTools.C4PieceToInt(gameController.getC4WinnerAsEnum()));
                        sendMessageBack(sender, forward);
                        break;
                    case "getC4CurrentPlayer":
                        forward.put("data", ConverterTools.C4PieceToInt(gameController.getC4CurrentPlayer()));
                        sendMessageBack(sender, forward);
                        break;
                    case "getC4ColHint":
                        forward.put("data", ConverterTools.hintResultToList(gameController.getC4ColHint()));
                        sendMessageBack(sender, forward);
                        break;
                    case "isTileEmpty":
                        if (content.containsKey("parameter") && content.get("parameter") instanceof List<?> parameter) {
                            forward.put("data", gameController.isTileEmpty(ConverterTools.listToIvec((List<Integer>) parameter)));
                            sendMessageBack(sender, forward);
                        } else {
                            log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
                        }
                        break;
                    case "makeMove":
                        if (content.containsKey("parameter") && content.get("parameter") instanceof List<?> parameter) {
                            forward.put("data", gameController.makeMove((int) parameter.get(0), (int) parameter.get(1)));
                            sendMessageBack(sender, forward);
                        }
                        break;
                    case "checkWin":
                        forward.put("data", gameController.checkWin());
                        sendMessageBack(sender, forward);
                        break;
                    case "checkDraw":
                        forward.put("data", gameController.checkDraw());
                        sendMessageBack(sender, forward);
                        break;
                    case "updateGameState":
                        gameController.updateGameState();
                        break;
                    default:
                        log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
                }
                break;
            // If chatting with a player
            case "chat":
                handleChatMessage(threadMessage);
                break;
            // If handling a disconnection
            case "disconnection":
                handleDisconnection(threadMessage);
                break;
            default:
                log("GameSessionManager: Message not recognized: " + threadMessage.getContent());
        }
    }

    private void sendMessageBack(Thread sender, Map<String, Object> newMessage) {
        ThreadMessage forward = new ThreadMessage(Thread.currentThread(), newMessage);
        // Send to the same player
        if (sender == player1.getThread()) {
            ThreadRegistry.getQueue(player1.getThread()).add(forward);
        } else if (sender == player2.getThread()) {
            ThreadRegistry.getQueue(player2.getThread()).add(forward);
        } else {
            log("GameSessionManager: Chat message sender not recognized: " + sender);
        }
    }

    private int checkTurn(Thread playerThread) {
        if (gameController.getCurrentPlayer() == 0) {
            if (player1.getThread() == playerThread) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (player1.getThread() == playerThread) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
