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
    private CheckersController gameLogic;

    private GameState gameState;

    // Store important pieceIDs.
    final int empty = CheckersPiece.NONE.getValue();
    final int pawn1 = CheckersPiece.P1PAWN.getValue();
    final int king1 = CheckersPiece.P1KING.getValue();
    final int pawn2 = CheckersPiece.P2PAWN.getValue();
    final int king2 = CheckersPiece.P2KING.getValue();

    final int[][] defaultInitialBoardData = new int[][]{
            {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
            {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
            {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
            {empty, empty, empty, empty, empty, empty, empty, empty},
            {empty, empty, empty, empty, empty, empty, empty, empty},
            {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
            {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
            {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
    };

    CheckersBoard defaultCheckersBoard = new CheckersBoard(8, 8, defaultInitialBoardData);


    @Test
    public void testCheckersController()
    {}

    @Test
    public void testNormalGameStateSetUp()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        // The game should start from player 1's turn.
        assertEquals(0, gameLogic.getCurrentPlayer());
        // The game should still be ongoing.
        assertTrue(gameLogic.getGameOngoing());
        // There should be no winners yet.
        assertEquals(0, gameLogic.getWinner().length);
    }

    @Test
    public void testNormalPieceSetUp()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        // Check to ensure that the default board is correct.

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int i = 0; i < defaultInitialBoardData.length; i++)
        {
            for (int j = 0; j < defaultInitialBoardData[i].length; j++)
            {
                assertEquals(defaultInitialBoardData[i][j], actualBoard[i][j]);
            }
        }
    }




    @Test
    public void removePlayerTest1()
    {
        int player = 1;
        CheckersController checkersController = new CheckersController();

        // gamestate should be ongoing before a player is removed
        assertEquals(GameState.ONGOING, checkersController.getState());
        checkersController.removePlayer(player);

        // gamestate should change after removal
        assertEquals(GameState.P1WIN, checkersController.getState());
    }
    @Test
    public void removePlayerTest0()
    {
        int player = 0;
        CheckersController checkersController = new CheckersController();

        // gamestate should be ongoing before a player is removed
        assertEquals(GameState.ONGOING, checkersController.getState());
        checkersController.removePlayer(player);

        // gamestate should change after removal
        assertEquals(GameState.P2WIN, checkersController.getState());
    }



    @Test
    public void getP1PawnMoves()
    {
        // Modified board with a single pawn in the middle to test its moves.
        gameLogic = new CheckersController(new CheckersBoard(5, 5, new int[][]{
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, pawn1, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
        }));

        // The piece is in the middle of the board.
        Ivec2 coords = new Ivec2(2, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // This piece can only move upwards.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(3, 1),
                new CheckersMove(coords, new Ivec2(3, 1), null));
        expectedValidMoves.put(new Ivec2(1, 1),
                new CheckersMove(coords, new Ivec2(1, 1), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    public void getP2PawnMoves()
    {
        // Modified board with a single pawn in the middle to test its moves.
        gameLogic = new CheckersController(new CheckersBoard(5, 5, new int[][]{
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, pawn2, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
        }));

        // The piece is in the middle of the board.
        Ivec2 coords = new Ivec2(2, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // This piece can only move downwards.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(3, 3),
                new CheckersMove(coords, new Ivec2(3, 3), null));
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    public void getP1KingMoves()
    {
        // Modified board with a single king in the middle to test its moves.
        gameLogic = new CheckersController(new CheckersBoard(5, 5, new int[][]{
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, king1, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
        }));

        // The piece is in the middle of the board.
        Ivec2 coords = new Ivec2(2, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // This piece can move in any diagonals.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(3, 1),
                new CheckersMove(coords, new Ivec2(3, 1), null));
        expectedValidMoves.put(new Ivec2(1, 1),
                new CheckersMove(coords, new Ivec2(1, 1), null));
        expectedValidMoves.put(new Ivec2(3, 3),
                new CheckersMove(coords, new Ivec2(3, 3), null));
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    public void getP2KingMoves()
    {
        // Modified board with a single king in the middle to test its moves.
        gameLogic = new CheckersController(new CheckersBoard(5, 5, new int[][]{
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, king2, empty, empty},
                {empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty},
        }));

        // The piece is in the middle of the board.
        Ivec2 coords = new Ivec2(2, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // This piece can move in any diagonals.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(3, 1),
                new CheckersMove(coords, new Ivec2(3, 1), null));
        expectedValidMoves.put(new Ivec2(1, 1),
                new CheckersMove(coords, new Ivec2(1, 1), null));
        expectedValidMoves.put(new Ivec2(3, 3),
                new CheckersMove(coords, new Ivec2(3, 3), null));
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    // Test checking for valid moves of a piece, when it cannot, and does not need to, capture anything.
    public void getPieceMovesNoCaptureFilterTest1()
    {
        gameLogic = new CheckersController();

        // Chosen piece is the 3rd piece from 3rd row.
        Ivec2 coords = new Ivec2(2, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make, without capturing anything.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(3, 3),
                new CheckersMove(coords, new Ivec2(3, 3), null));
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));

        // The flag should still say that no capture is possible.
        assertFalse(mustCapture[0]);
    }


    @Test
    // Test checking for valid moves of a piece, when it can, but does not need tom capture something.
    public void getPieceMovesNoCaptureFilterTest2()
    {
        // Modified board where a capture is possible.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, pawn2, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, pawn2, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Chosen piece is the 2nd piece from 6th row.
        // This can make a capture.
        Ivec2 coords = new Ivec2(3, 5);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // It will be forced to make a capture here once a capture is detected.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), new Ivec2(2, 4)));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));

        // The flag should be updated to denote that a capture is indeed possible.
        assertTrue(mustCapture[0]);
    }


    @Test
    // Test checking for valid moves of a piece when
    // one of the tiles that it can move to is blocked by another piece of the same player.
    public void getBlockedPieceMovesTest1()
    {
        // Modified board where a piece is blocked.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, pawn1, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, empty, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Chosen piece is the 1st piece from 6th row.
        // This piece is blocked on the top right by another pawn of player 1.
        Ivec2 coords = new Ivec2(1, 5);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make, without capturing anything.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(0, 4),
                new CheckersMove(coords, new Ivec2(0, 4), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    // Test checking for valid moves of a piece when
    // one of the tiles that it can move to is blocked from being a capture by pieces of the other player.
    public void getBlockedPieceMovesTest2()
    {
        // Modified board where a capture is blocked and therefore not possible.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, empty, empty, empty, empty, pawn2, empty},
                {empty, empty, empty, pawn2, empty, empty, empty, empty},
                {empty, empty, pawn2, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Chosen piece is the 1st piece from 6th row.
        // This cannot make a capture because the capture does not have an empty tile to end on.
        Ivec2 coords = new Ivec2(1, 5);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // It will be forced to make a capture here once a capture is detected.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(0, 4),
                new CheckersMove(coords, new Ivec2(0, 4), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));

        // The flag should not be updated because a capture is not possible.
        assertFalse(mustCapture[0]);
    }


    @Test
    // Test checking for valid moves of a piece when
    // one of the tiles that it can move to is out of bounds.
    public void getBlockedPieceMovesTest3()
    {
        // Modified board where a piece is blocked.
        gameLogic = new CheckersController(defaultCheckersBoard);

        // Chosen piece is the 1st piece from 3rd row.
        // This piece is on the left edge of the board and so cannot move further in that direction.
        Ivec2 coords = new Ivec2(0, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make, without capturing anything.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));
    }


    @Test
    // Test checking for valid moves of a piece when
    // one of the tiles that it can move to is blocked from being a capture
    // because the capture would jump to an out of bounds tile.
    public void getBlockedPieceMovesTest4()
    {
        // Modified board where a capture is blocked and therefore not possible.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, empty, empty, empty, empty, empty, empty, pawn1},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn2},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Chosen piece is the 4th piece from 3rd row.
        // This cannot make a capture because the capture does not have an empty tile to end on.
        Ivec2 coords = new Ivec2(6, 2);
        // Does not need to capture anything.
        boolean[] mustCapture = {false};

        // Construct the hashMap of valid moves this piece can make.
        // It will be forced to make a capture here once a capture is detected.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(5, 3),
                new CheckersMove(coords, new Ivec2(5, 3), null));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));

        // The flag should not be updated because a capture is not possible.
        assertFalse(mustCapture[0]);
    }


    @Test
    // Test checking for valid moves of a piece, when it must, but cannot, capture anything.
    public void getPieceMovesMustCaptureTest1()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        // Chosen piece is the 3rd piece from 3rd row.
        // There is nothing to capture.
        Ivec2 coords = new Ivec2(2, 2);
        // Must capture something.
        boolean[] mustCapture = {true};

        assertTrue(gameLogic.getPieceMovesPublic(coords, mustCapture).isEmpty());

        // The mustCapture flag must stay turned on because once a player is forced to make a capture,
        // the condition will apply to all of their pieces on the board.
        assertTrue(mustCapture[0]);
    }

    @Test
    // Test checking for valid moves of a piece, when it must, and can, capture something.
    public void getPieceMovesMustCaptureTest2()
    {
        // Modified board where a capture is possible.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, pawn2, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, pawn2, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Chosen piece is the 2nd piece from 6th row.
        // This can make a capture.
        Ivec2 coords = new Ivec2(3, 5);
        // Must capture something.
        boolean[] mustCapture = {true};

        // Construct the hashMap of valid moves this piece can make.
        // It will be forced to make a capture here once a capture is detected.
        // The keys are the target coordinates of each move.
        HashMap<Ivec2, CheckersMove> expectedValidMoves = new HashMap<>();
        expectedValidMoves.put(new Ivec2(1, 3),
                new CheckersMove(coords, new Ivec2(1, 3), new Ivec2(2, 4)));

        assertEquals(expectedValidMoves, gameLogic.getPieceMovesPublic(coords, mustCapture));

        // The flag should stay on because the capture is still enforced.
        assertTrue(mustCapture[0]);
    }


    @Test
    public void testNoCaptureUpdateValidInputs()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{false};

        // Add the moves of pieces that can be moved.
        expectedValidInputs.put(new Ivec2(1, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(1, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(3, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(3, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(5, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(7, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(7, 5), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    public void testCanCaptureUpdateValidInputs()
    {
        gameLogic = new CheckersController();

        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{false};

        // Add the moves of pieces that can be moved.
        expectedValidInputs.put(new Ivec2(1, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(1, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(3, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(3, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(5, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(7, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(7, 5), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
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

