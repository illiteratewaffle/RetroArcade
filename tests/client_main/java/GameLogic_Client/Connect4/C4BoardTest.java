package client_main.java.GameLogic_Client.Connect4;
import GameLogic_Client.Connect4.C4Board;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class C4BoardTest {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final C4Piece P = C4Piece.RED;
    private static final C4Piece Q = C4Piece.BLUE;
    private static final C4Piece B = C4Piece.BLANK;
    private static final int valRed = P.getValue();
    private static final int valBlue = Q.getValue();
    private static final int valBlank = B.getValue();

    C4Board board = new C4Board(ROWS, COLS);
    private C4Piece[][] emptyBoard() {
        return new C4Piece[ROWS][COLS];
    }

    @Test
    void getRedPiece() {
        board.setC4Piece(new Ivec2(2, 1), P);

        assertEquals(board.getPiece(new Ivec2(2, 1)), valRed);
    }

    @Test
    void getPieceMismatched() {
        board.setC4Piece(new Ivec2(2, 1), P);

        assertNotEquals(board.getPiece(new Ivec2(2, 1)), valBlue);
    }

    @Test
    void getBlankPiece() {
        board.setC4Piece(new Ivec2(2, 1), B);

        assertEquals(board.getPiece(new Ivec2(2, 1)), valBlank);
    }

    @Test
    void getMatchingBoard() {
        board.setC4Piece(new Ivec2(2, 1), Q);
        board.setC4Piece(new Ivec2(0, 0), Q);

        C4Piece[][] board2 = new C4Piece[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if((i == 1 && j == 2) || i == 0 && j == 0) {
                    board2[i][j] = Q;
                } else {
                    board2[i][j] = B;
                }
            }
        }

        assertEquals(Arrays.deepToString(board.getC4Board()), Arrays.deepToString(board2));
    }

    @Test
    void getMisMatchedBoard() {
        board.setC4Piece(new Ivec2(2, 1), Q);
        board.setC4Piece(new Ivec2(0, 0), P);

        C4Piece[][] board2 = new C4Piece[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if((i == 1 && j == 2) || i == 0 && j == 0) {
                    board2[i][j] = Q;
                } else {
                    board2[i][j] = B;
                }
            }
        }

        assertNotEquals(Arrays.deepToString(board.getC4Board()), Arrays.deepToString(board2));
    }
}
