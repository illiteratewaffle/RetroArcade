package GameLogic.Checkers;


public class CheckersController {
    private BoardGame BoardGame;
    private int[] CheckersMove = new int[2];

    //checks if the player can interact with the board
    private boolean TurnP1 = true;




    /**
     * A new game of checkers has started
     */

    void start(){
        System.out.println(" A new game of Checkers has started");
        BoardGame = new BoardGame();
        //  printBoard();

    }

    /**
     * will handle input during the game.
     *
     */

    void handleInput(String input){

    }

    void gameState(){

    }


    /**
     * prints out how the board looks at any point (continously????)
     * commented this out bc there was an error -ava
     */
   /* void printBoard(){
        System.out.println(CheckersBoard);
    }*/

    public static void main(String[] args) {}
}