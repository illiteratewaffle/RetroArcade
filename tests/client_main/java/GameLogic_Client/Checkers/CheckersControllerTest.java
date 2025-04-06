package client_main.java.GameLogic_Client.Checkers;

import GameLogic_Client.Checkers.CheckersController;

import GameLogic_Client.Checkers.CheckersPiece;
import GameLogic_Client.Checkers.GameState;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckersControllerTest
{
    @Test
    public void testDefaultGameStateSetUp()
    {
        CheckersController checkersController = new CheckersController();

        // The game should start from player 1's turn.
        assertEquals(checkersController.getCurrentPlayer(), 0);
        // The game should still be ongoing.
        assertEquals(checkersController.getGameOngoing(), true);
        // There should be no winners yet.
        assertEquals(checkersController.getWinner().length, 0);
    }

    @Test
    public void testDefaultBoardSetUp()
    {
        CheckersController checkersController = new CheckersController();

        int emptyValue = CheckersPiece.NONE.getValue();
        int p1PawnValue = CheckersPiece.P1PAWN.getValue();
        int p2PawnValue = CheckersPiece.P2PAWN.getValue();

        int[][] expectedBoardPieceSetUp = new int[][] {
                {p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue},
                {emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue},
                {p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue, p2PawnValue, emptyValue},
                {emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue},
                {emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue, emptyValue},
                {emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue},
                {p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue},
                {emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue, emptyValue, p1PawnValue}
        };

        int[][] actualBoardPieceSetup = checkersController.getBoardCells(0b01).getFirst();

        // Check if all the pieces are in the correct place.
        for (int i = 0; i < expectedBoardPieceSetUp.length; i++)
        {
            for (int j = 0; j < expectedBoardPieceSetUp[i].length; j++)
            {
                assertEquals(expectedBoardPieceSetUp[i][j], actualBoardPieceSetup[i][j]);
            }
        }
    }

    @Test
    public void testHintSetUp()
    {

    }
}
