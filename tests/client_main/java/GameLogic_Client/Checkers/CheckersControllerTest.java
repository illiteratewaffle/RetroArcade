package client_main.java.GameLogic_Client.Checkers;

import GameLogic_Client.Checkers.*;

import GameLogic_Client.Checkers.GameState;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

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
    public void testDefaultGameStateSetUp() {
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

        // gamestate should be ongoing before a player is removed
        assertEquals(GameState.ONGOING, checkersController.getState());
        checkersController.removePlayer(player);

        // gamestate should change after removal
        assertEquals(GameState.P1WIN, checkersController.getState());
    }
    @Test
    public void removePlayerTest0(){
        int player = 0;
        CheckersController checkersController = new CheckersController();

        // gamestate should be ongoing before a player is removed
        assertEquals(GameState.ONGOING, checkersController.getState());
        checkersController.removePlayer(player);

        // gamestate should change after removal
        assertEquals(GameState.P2WIN, checkersController.getState());
    }

    @Test
    public void getPieceMovesTest() {
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {3, 0, 3, 0, 3, 0, 3, 0},
                {0, 3, 0, 3, 0, 3, 0, 3},
                {3, 0, 3, 0, 3, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0}
        }));

        // chosen piece is the 3rd piece from 3rd row
        Ivec2 coords = new Ivec2(2, 2);
        // can not capture anything
        boolean[] mustCapture = {false};

        // find the possible moves
        var moves = gameLogic.getPieceMovesPublic(coords, mustCapture);

        // It should be able to move down right and left
        assertTrue(moves.containsKey(new Ivec2(3, 3)));
        assertTrue(moves.containsKey(new Ivec2(1, 3)));
        // It should not be able to move straight down
        assertFalse(moves.containsKey(new Ivec2(2, 3)));
    }

    @Test
    public void testUpdateValidInputs() {
        // Set up a board where P1 has pieces and valid moves
        int[][] boardSetup = new int[][]{
                {3, 0, 3, 0, 3, 0, 3, 0},
                {0, 3, 0, 3, 0, 3, 0, 3},
                {3, 0, 3, 0, 3, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0}
        };

        CheckersBoard board = new CheckersBoard(8, 8, boardSetup);
        CheckersController controller = new CheckersController(board);

        // Make sure game is ongoing
        assertEquals(GameState.ONGOING, controller.getState());
        boolean[] mustCapture = {false};

        controller.updateValidInputsPublic();
        Ivec2 start = new Ivec2(2, 2);
        var validInputs = controller.getPieceMovesPublic(new Ivec2(2,2), mustCapture);

        // Validate that moves exists
        assertFalse(validInputs.isEmpty());
        Ivec2 expectedTarget = new Ivec2(3, 3);
        assertTrue(validInputs.containsKey(expectedTarget));
    }

    public void getWinnerTest(){
        CheckersController controller = new CheckersController();

        GameState state = GameState.ONGOING;
        Assertions.assertEquals(GameState.ONGOING, controller.getState());

        state = GameState.P1WIN;
        Assertions.assertEquals(GameState.P1WIN, controller.getState());

        state = GameState.P2WIN;
        Assertions.assertEquals(GameState.P2WIN, controller.getState());

        state = GameState.TIE;
        Assertions.assertEquals(GameState.TIE, controller.getState());
    }




}

