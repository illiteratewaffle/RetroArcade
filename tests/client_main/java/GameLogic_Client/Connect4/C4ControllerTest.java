package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.*;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Ivec2;
import GameLogic_Client.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class C4ControllerTest {

    @Test
    void start() {
        C4Controller controller = new C4Controller();
        controller.start();
        assertNotNull(controller.c4GameLogic);
    }

    @Test
    void receiveInputValidMove() {
        C4Controller controller = new C4Controller();
        controller.start();

        controller.receiveInput(new Ivec2(0, 0));
        controller.receiveInput(new Ivec2(6, 0));
        controller.receiveInput(new Ivec2(0, 0));

        C4Piece[][] board = controller.getC4Board();
        int lastRow = board.length - 1;

        assertEquals(C4Piece.RED, board[lastRow][0]); //tests that RED (1st) piece goes in the bottom left
        assertEquals(C4Piece.BLUE, board[lastRow][6]); //tests that BLUE (2nd) piece goes in the bottom right
        assertEquals(C4Piece.RED, board[lastRow-1][0]); //tests that RED (3rd) piece goes above the first red piece
    }

    @Test
    void receiveInputInvalidMove() {
        C4Controller controller = new C4Controller();
        controller.start();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        controller.receiveInput(new Ivec2(-1, 0));

        System.setOut(originalOut); // reset

        String printed = out.toString();
        assertTrue(printed.contains("Invalid column, out of bounds")); //tests that the print message prints
    }

    @Test
    void testGetWinner_withTie() {
        C4Controller controller = new C4Controller();
        controller.start();

        //fill board with no four pieces in a row for either RED or BLUE pieces
        C4Piece[][] pattern = {
                {C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED},
                {C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE},
                {C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED},
                {C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE},
                {C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED},
                {C4Piece.RED, C4Piece.RED, C4Piece.BLUE, C4Piece.BLUE, C4Piece.RED, C4Piece.RED, C4Piece.BLUE}
        };

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                controller.c4GameLogic.getC4Board().setC4Piece(new Ivec2(col, row), pattern[row][col]);
            }
        }

        System.out.println("Board layout:");
        System.out.println(controller.c4GameLogic.getC4Board().toString());

        controller.c4GameLogic.gameState = GameState.TIE;
        assertEquals(0, controller.getWinner());
    }

    @Test
    void getC4Board() {
    }

    @Test
    void getC4IsGameOver() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void getGameOngoing() {
    }

    @Test
    void getBoardCells() {
    }

    @Test
    void getBoardSize() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void gameOngoingChangedSinceLastCommand() {
    }

    @Test
    void winnersChangedSinceLastCommand() {
    }

    @Test
    void currentPlayerChangedSinceLastCommand() {
    }

    @Test
    void boardChangedSinceLastCommand() {
    }

    @Test
    void getC4WinnerAsEnum() {
    }

    @Test
    void getC4CurrentPlayer() {
    }

    @Test
    void printBoard() {
    }

    @Test
    void getC4ColHint() {
    }
}