package client_main.java.GameLogic_Client.Checkers;

import GameLogic_Client.Checkers.*;

import GameLogic_Client.GameState;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        // The size of the game board should be the same as that of the passed argument.
        assertEquals(defaultCheckersBoard.getSize(), gameLogic.getBoardSize());
        // The game should start from player 1's turn.
        assertEquals(0, gameLogic.getCurrentPlayer());
        // The game should still be ongoing.
        assertTrue(gameLogic.getGameOngoing());
        // There should be no winners yet (state == 3).
        assertEquals(3, gameLogic.getWinner());
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
    // because the capture would jump to an out-of-bounds tile.
    public void getBlockedPieceMovesTest4()
    {
        // Modified board where a capture is blocked and therefore not possible.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, empty, empty, empty, empty, empty, empty, pawn1},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
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

        // Ensure that this test is only running for player 1.
        // We have yet to test for turn changing.
        assertEquals(0, gameLogic.getCurrentPlayer());

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
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, pawn2, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Ensure that this test is only running for player 1.
        // We have yet to test for turn changing.
        assertEquals(0, gameLogic.getCurrentPlayer());

        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{true};

        // Add the capture moves of pieces that can be actually perform a capture.
        expectedValidInputs.put(new Ivec2(5, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 5), mustCapture));
        expectedValidInputs.put(new Ivec2(7, 5),
                gameLogic.getPieceMovesPublic(new Ivec2(7, 5), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    public void selectPieceFailTest()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        assertEquals(0, gameLogic.getCurrentPlayer());

        Set<Ivec2> selectablePieces = gameLogic.updateValidInputsPublic().keySet();
        for (int y = 0; y < defaultInitialBoardData.length; y++)
        {
            for (int x = 0; x < defaultInitialBoardData[y].length; x++)
            {
                Ivec2 inputPos = new Ivec2(x, y);
                if (!selectablePieces.contains(inputPos))
                {
                    gameLogic.receiveInput(inputPos);
                    // This piece should not be selectable.
                    assertNull(gameLogic.getCurrentPieceLocation());
                }
            }
        }

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The board should not have changed here.
        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < defaultInitialBoardData.length; y++)
        {
            for (int x = 0; x < defaultInitialBoardData[y].length; x++)
            {
                assertEquals(defaultInitialBoardData[y][x], actualBoard[y][x]);
            }
        }
    }


    @Test
    // Test if clicking on a selectable piece will select it.
    public void selectSinglePieceTest1()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the Leftmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(1, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The board should not have changed here.
        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < defaultInitialBoardData.length; y++)
        {
            for (int x = 0; x < defaultInitialBoardData[y].length; x++)
            {
                assertEquals(defaultInitialBoardData[y][x], actualBoard[y][x]);
            }
        }
    }

    @Test
    // Test if clicking on a selectable piece will replace the currently selected one.
    public void selectSinglePieceTest2()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the Leftmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(1, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Pass an input to the second Leftmost piece on the 6th row.
        Ivec2 nexInputPos = new Ivec2(3, 5);
        gameLogic.receiveInput(nexInputPos);
        assertEquals(nexInputPos, gameLogic.getCurrentPieceLocation());

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());
    }


    @Test
    // Test if clicking on a selected piece will deselect it.
    public void deselectSinglePieceTest1()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the Leftmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(1, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Pressing on the same piece again should deselect it.
        gameLogic.receiveInput(inputPos);
        assertNull(gameLogic.getCurrentPieceLocation());

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The board should not have changed here.
        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < defaultInitialBoardData.length; y++)
        {
            for (int x = 0; x < defaultInitialBoardData[y].length; x++)
            {
                assertEquals(defaultInitialBoardData[y][x], actualBoard[y][x]);
            }
        }
    }


    @Test
    // Test if clicking on a non-selectable tile will deselect the current piece.
    public void deselectSinglePieceTest2()
    {
        gameLogic = new CheckersController(defaultCheckersBoard);

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the Leftmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(1, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Pressing on something that cannot be selected should deselect the current piece.
        Ivec2 newInputPos = new Ivec2(0, 0);
        gameLogic.receiveInput(inputPos);
        assertNull(gameLogic.getCurrentPieceLocation());

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The board should not have changed here.
        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < defaultInitialBoardData.length; y++)
        {
            for (int x = 0; x < defaultInitialBoardData[y].length; x++)
            {
                assertEquals(defaultInitialBoardData[y][x], actualBoard[y][x]);
            }
        }
    }


    @Test
    public void movePieceNoCaptureTest()
    {
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, empty, empty, empty, empty},
                {empty, empty, empty, pawn2, empty, pawn2, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the rightmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(7, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position.
        Ivec2 newInputPos = new Ivec2(6, 4);
        gameLogic.receiveInput(newInputPos);

        // This should end the turn and clear the position of the currently selected piece.
        assertNull(gameLogic.getCurrentPieceLocation());
        assertEquals(1, gameLogic.getCurrentPlayer());

        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, empty, empty, empty, empty},
                {empty, empty, empty, pawn2, empty, pawn2, empty, empty},
                {empty, empty, empty, empty, empty, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }


        // Check if the map of valid inputs for player 2 is now in effect.
        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{true};

        // Add the moves of pieces that can be moved.
        // It will result in a capture this turn.
        expectedValidInputs.put(new Ivec2(5, 3),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 3), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    // Test when a piece can make a capture, and the input does not specify a capture move.
    public void movePieceCaptureTest1()
    {
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        int[][] modifiedBoardData = new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, pawn2, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };
        gameLogic = new CheckersController(new CheckersBoard(8, 8, modifiedBoardData));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the second rightmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(5, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is not a capture.
        Ivec2 newInputPos = new Ivec2(4, 4);
        gameLogic.receiveInput(newInputPos);


        // This should be equivalent to selecting a non-selectable tile,
        // and thus deselect the current piece.
        assertNull(gameLogic.getCurrentPieceLocation());

        // The turn should not end here.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The board should not have changed here.
        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < modifiedBoardData.length; y++)
        {
            for (int x = 0; x < modifiedBoardData[y].length; x++)
            {
                assertEquals(modifiedBoardData[y][x], actualBoard[y][x]);
            }
        }
    }


    @Test
    // Test when a piece can make a capture, and the input specifies a capture move,
    // but the capture does not lead to another capture.
    public void movePieceCaptureTest2()
    {
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, pawn2, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the second rightmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(5, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(7, 3);
        gameLogic.receiveInput(newInputPos);


        // Move to the next turn.
        assertNull(gameLogic.getCurrentPieceLocation());

        assertEquals(1, gameLogic.getCurrentPlayer());


        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, pawn1},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, empty, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }


        // Check if the map of valid inputs for player 2 is now in effect.
        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{false};

        // Add the moves of pieces that can be moved.
        expectedValidInputs.put(new Ivec2(0, 2),
                gameLogic.getPieceMovesPublic(new Ivec2(0, 2), mustCapture));
        expectedValidInputs.put(new Ivec2(2, 2),
                gameLogic.getPieceMovesPublic(new Ivec2(2, 2), mustCapture));
        expectedValidInputs.put(new Ivec2(4, 2),
                gameLogic.getPieceMovesPublic(new Ivec2(4, 2), mustCapture));
        expectedValidInputs.put(new Ivec2(5, 1),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 1), mustCapture));
        expectedValidInputs.put(new Ivec2(7, 1),
                gameLogic.getPieceMovesPublic(new Ivec2(7, 1), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    // Test when a piece can make a capture, and the input specifies a capture move,
    // and the capture does lead to another capture.
    public void movePieceCaptureTest3()
    {
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, empty, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, pawn2, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the rightmost piece on the 6th row.
        Ivec2 inputPos = new Ivec2(7, 5);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(5, 3);
        gameLogic.receiveInput(newInputPos);


        // The currently selected piece should be forced to make another capture.
        assertEquals(newInputPos, gameLogic.getCurrentPieceLocation());

        // The current player's turn should continue.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, empty, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, pawn1, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }


        // Check if the map of valid inputs still only contain moves for the selected piece.
        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        // A capture is forced.
        boolean[] mustCapture = new boolean[]{true};

        // Add the captures of the selected piece.
        expectedValidInputs.put(new Ivec2(5, 3),
                gameLogic.getPieceMovesPublic(new Ivec2(5, 3), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    // Test when a piece can make a capture, and the input specifies a capture move,
    // and the capture does lead to another capture, but the move has made the selected piece into a king.
    public void movePieceCaptureTest4()
    {
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, empty, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, king1, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the rightmost piece on the 3rd row.
        Ivec2 inputPos = new Ivec2(6, 2);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(4, 0);
        gameLogic.receiveInput(newInputPos);


        // The currently selected piece should be forced to make another capture.
        assertEquals(newInputPos, gameLogic.getCurrentPieceLocation());

        // The current player's turn should continue.
        assertEquals(0, gameLogic.getCurrentPlayer());


        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {pawn2, empty, pawn2, empty, king1, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, empty, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }


        // Check if the map of valid inputs for player 2 is now in effect.
        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        boolean[] mustCapture = new boolean[]{true};

        expectedValidInputs.put(newInputPos,
                gameLogic.getPieceMovesPublic(newInputPos, mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    // Test when a piece can make a capture, and the input specifies a capture move,
    // and the capture does lead to another capture,
    // and the move was a movement to the backlines by a king piece (which should not be made king again).
    public void movePieceCaptureTest5()
    {
        // Modified board such that only the 2 right-most pieces on the 6th row can make a capture.
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {pawn2, empty, pawn2, empty, empty, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, king1, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        assertEquals(0, gameLogic.getCurrentPlayer());

        // Pass an input to the rightmost piece on the 3rd row.
        Ivec2 inputPos = new Ivec2(6, 2);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(4, 0);
        gameLogic.receiveInput(newInputPos);


        // This turn should still continue because the selected piece will not be made king again.
        assertEquals(newInputPos, gameLogic.getCurrentPieceLocation());

        assertEquals(0, gameLogic.getCurrentPlayer());


        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {pawn2, empty, pawn2, empty, king1, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, empty, empty, pawn2},
                {pawn2, empty, empty, empty, pawn2, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }


        // Check if the map of valid inputs still only contain moves for the selected piece.
        HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> expectedValidInputs = new HashMap<>();

        // A capture is forced.
        boolean[] mustCapture = new boolean[]{true};

        // Add the captures of the selected piece.
        expectedValidInputs.put(new Ivec2(4, 0),
                gameLogic.getPieceMovesPublic(new Ivec2(4, 0), mustCapture));

        assertEquals(expectedValidInputs, gameLogic.updateValidInputsPublic());
    }


    @Test
    public void checkNoOpponentWinTest()
    {
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, pawn2, empty, empty},
                {empty, empty, empty, empty, empty, empty, king1, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Assert initial states.
        assertTrue(gameLogic.getGameOngoing());
        assertEquals(0, gameLogic.getCurrentPlayer());

        // Make the capture.
        // Pass an input to the rightmost piece on the 3rd row.
        Ivec2 inputPos = new Ivec2(6, 2);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(4, 0);
        gameLogic.receiveInput(newInputPos);

        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {empty, empty, empty, empty, king1, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, empty},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }

        // There are no pieces belonging to player 2 left.
        // This should result in a win for player 1.
        assertFalse(gameLogic.getGameOngoing());
        // This should be player 1 (represented by index 0).
        assertEquals(0, gameLogic.getWinner());

        // After a win, there should be no valid inputs left.
        assertTrue(gameLogic.updateValidInputsPublic().isEmpty());
    }


    @Test
    public void checkImmobilisedOpponentWinTest()
    {
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, pawn2, empty, empty},
                {empty, empty, empty, empty, empty, empty, king1, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn2},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        }));

        // Assert initial states.
        assertTrue(gameLogic.getGameOngoing());
        assertEquals(0, gameLogic.getCurrentPlayer());

        // Make the capture.
        // Pass an input to the rightmost piece on the 3rd row.
        Ivec2 inputPos = new Ivec2(6, 2);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        // Make the move to a new position that is a capture.
        Ivec2 newInputPos = new Ivec2(4, 0);
        gameLogic.receiveInput(newInputPos);

        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {empty, empty, empty, empty, king1, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn2},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }

        // The only piece belonging to player 2 should be unable to move.
        // This should result in a win for player 1.
        assertFalse(gameLogic.getGameOngoing());
        // This should be player 1 (represented by index 0).
        assertEquals(0, gameLogic.getWinner());

        // After a win, there should be no valid inputs left.
        assertTrue(gameLogic.updateValidInputsPublic().isEmpty());
    }


    @Test
    public void checkTieTest()
    {
        gameLogic = new CheckersController(new CheckersBoard(8, 8, new int[][]{
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, pawn1}
        }));

        // Assert initial states.
        assertTrue(gameLogic.getGameOngoing());
        assertEquals(0, gameLogic.getCurrentPlayer());

        // Make the only possible move.
        // Pass an input to the rightmost piece on the 8th row.
        Ivec2 inputPos = new Ivec2(7, 7);
        gameLogic.receiveInput(inputPos);
        assertEquals(inputPos, gameLogic.getCurrentPieceLocation());

        Ivec2 newInputPos = new Ivec2(6, 6);
        gameLogic.receiveInput(newInputPos);

        // The expected layout of the board after the move.
        int[][] expectedBoard = new int[][]{
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn2, empty, pawn2, empty, pawn2, empty, pawn2, empty},
                {empty, pawn2, empty, pawn2, empty, pawn2, empty, pawn2},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, pawn1, empty, pawn1, empty, pawn1, empty, pawn1},
                {pawn1, empty, pawn1, empty, pawn1, empty, pawn1, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty}
        };

        int[][] actualBoard = gameLogic.getBoardCells(0b01).getFirst();

        for (int y = 0; y < expectedBoard.length; y++)
        {
            for (int x = 0; x < expectedBoard[y].length; x++)
            {
                assertEquals(expectedBoard[y][x], actualBoard[y][x]);
            }
        }

        // No players should be able to make any other moves.
        assertFalse(gameLogic.getGameOngoing());
        // There should be exactly 2 winners (tie); this is state 2.
        assertEquals(2, gameLogic.getWinner());

        // After a win, there should be no valid inputs left.
        assertTrue(gameLogic.updateValidInputsPublic().isEmpty());
    }
}

