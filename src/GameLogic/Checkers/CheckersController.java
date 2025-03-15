package GameLogic.Checkers;

import java.util.ArrayList;


public class CheckersController {
    private BoardGame BoardGame;
    private int[] CheckersMove = new int[2];




    /**
     * A new game of checkers has started
     */

    void start(){
        System.out.println(" A new game of Checkers has started");
        BoardGame = new BoardGame();
        printBoard();

}

    /**
     * will handle input during the game.
     *
     */

    void handleInput(String input){

    }


    /**
     * prints out how the board looks at any point (continously????)
     */
    void printBoard(){
        System.out.println(CheckersBoard);
    }

    public static void main(String[] args) {}
}