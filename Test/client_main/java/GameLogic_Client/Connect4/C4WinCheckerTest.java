package client_main.java.GameLogic_Client.Connect4;

import org.junit.jupiter.api.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class C4WinCheckerTest {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private C4Piece[][] board;

    @BeforeEach
    void setUp() {
        board = new C4Piece[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = C4Piece.BLANK;
            }
        }
    }

    // Passes
    @Test
    void horizontalWin1() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][1] = C4Piece.BLUE;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 1, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalWin2() throws Exception {
        int winRow = 0;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 3, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalWin3() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][1] = C4Piece.BLUE;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.RED;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 2, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalWin4() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][1] = C4Piece.BLUE;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.RED;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 0, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalWin5() throws Exception {
        int winRow = 0;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 6, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalWin6() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][1] = C4Piece.BLUE;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.RED;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, 4, C4Piece.BLUE, board));
    }

    // Fails
    @Test
    void horizontalNoWin1() throws Exception {
        int winRow = 0;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.RED;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, 3, C4Piece.BLUE, board));
    }

    // Passes
    @Test
    void horizontalNoWin2() throws Exception {
        int winRow = 0;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, 5, C4Piece.BLUE, board));
    }

    // Fails
    @Test
    void horizontalNoWin3() throws Exception {
        int winRow = 0;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, 3, C4Piece.BLUE, board));
    }

    // Fails
    @Test
    void horizontalNoWin4() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, 3, C4Piece.BLUE, board));
    }

}