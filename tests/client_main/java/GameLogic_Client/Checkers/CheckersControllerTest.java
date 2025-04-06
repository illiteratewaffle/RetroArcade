package client_main.java.GameLogic_Client.Checkers;

import GameLogic_Client.Checkers.CheckersController;
import GameLogic_Client.Checkers.GameState;

import GameLogic_Client.Checkers.CheckersPiece;
import GameLogic_Client.Checkers.GameState;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class CheckersControllerTest {
    private CheckersController checkersController;
    private GameState gameState;
    @BeforeEach
    public void setUp() {
        checkersController = new CheckersController();
    }

    @Test
    public void testCheckersController() {

    }

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
    public void testDefaultBoardSetUp() {
        CheckersController checkersController = new CheckersController();

        int emptyValue = CheckersPiece.NONE.getValue();
        int p1PawnValue = CheckersPiece.P1PAWN.getValue();
        int p2PawnValue = CheckersPiece.P2PAWN.getValue();
    }
    private CheckersController gameLogic;

    @Test
    public void removePlayerTest1(){
        int player = 1;
        CheckersController checkersController = new CheckersController();
        checkersController.removePlayer(player);
        assertEquals(GameState.P1WIN, checkersController.getState());
    }
    @Test
    public void removePlayerTest0(){
        int player = 0;
        CheckersController checkersController = new CheckersController();
        checkersController.removePlayer(player);
        assertEquals(GameState.P2WIN, checkersController.getState());
    }

}
