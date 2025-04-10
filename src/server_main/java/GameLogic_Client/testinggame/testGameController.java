package GameLogic_Client.testinggame;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;

import java.util.ArrayList;

public class testGameController implements IBoardGameController {

    int[][] game = new int[][] {{2,2,2},{2,2,2},{2,2,2} };
    boolean playing = false;
    int[] winner;

    public int currPlayer = 1;

    /**
     *
     * @param input A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */
    @Override
    public void receiveInput(Ivec2 input) {
        if (this.game[input.x][input.y] == 2 && playing) {
            if(currPlayer == 1) {
                //add input
                this.game[input.x][input.y] = 1;
                //change the current player
                currPlayer = 0;
                checkwin();
                return;
            }
            else {
                game[input.x][input.y] = 0;
                currPlayer = 1;
                checkwin();
                return;
            }
        }
        System.out.println("Invalid input: " + input.x + " " + input.y);
    }

    @Override
    public void removePlayer(int player) throws IndexOutOfBoundsException {
        return;
    }

    /**
     *
     * @return returns the winner or returns 0
     */
    @Override
    public int[] getWinner() {
        if(!playing) {
            return winner;
        }
        return new int[0];
    }

    /**
     *
     * @return true if game is ongoing, false otherwise
     */
    @Override
    public boolean getGameOngoing() {
        return playing;
    }

    @Override
    public ArrayList<int[][]> getBoardCells(int layerMask) {
        ArrayList<int[][]> layers = new ArrayList<>();
        layers.add(game);
        return layers;
    }

    @Override
    public Ivec2 getBoardSize() {
        return new Ivec2(game.length, game.length);
    }

    @Override
    public int getCurrentPlayer() {
        return currPlayer;
    }

    @Override
    public boolean gameOngoingChangedSinceLastCommand() {
        return playing;
    }

    @Override
    public boolean winnersChangedSinceLastCommand() {
        return false;
    }

    @Override
    public boolean currentPlayerChangedSinceLastCommand() {
        return false;
    }

    @Override
    public int boardChangedSinceLastCommand() {
        return 0;
    }

    private boolean checkwin() {
        if (this.game[0][0] != 2)
        {
            if(this.game[0][0] == 0)
            {
                winner[0] = 0;
            }
            else if(this.game[0][0] == 1)
            {
                winner[0] = 1;
            }
            playing = false;
            return true;
        }
        return false;
    }
}
