package gamelogic.Connect4;

public class C4Controller {


    private C4GameLogic c4GameLogic;

    /**
     * Starts a new game of Connect Four.
     */
    void start() {
        System.out.println("A new game of connect four has started");
        c4GameLogic = new C4GameLogic();
        printBoard();
    }

    /**
     * Handles user input during the game.
     */
    void handleInput() {
    }

    /**
     * Prints current state of board (at any point).
     */
    void printBoard() {
        System.out.println(c4GameLogic);
    }
}