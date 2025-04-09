package client_main.java.GameLogic_Client.Connect4;
import GameLogic_Client.Connect4.C4WinChecker;
import client_main.java.GameLogic_Client.Connect4.C4Piece;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class C4WinCheckerTest {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private client_main.java.GameLogic_Client.Connect4.C4Piece[][] board;

    @BeforeEach
    void setUp() {
        board = new C4Piece[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = C4Piece.BLANK;
            }
        }
    }


    @Test
    void horizontalWin1() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][1] = C4Piece.BLUE;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalWin2() throws Exception {
        int winRow = 0;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


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

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


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

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalWin5() throws Exception {
        int winRow = 0;
        board[winRow][3] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


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

        assertTrue(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalNoWin1() throws Exception {
        int winRow = 0;
        board[winRow][2] = C4Piece.BLUE;
        board[winRow][3] = C4Piece.RED;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalNoWin2() throws Exception {
        int winRow = 0;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalNoWin3() throws Exception {
        int winRow = 0;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    @Test
    void horizontalNoWin4() throws Exception {
        int winRow = 0;
        board[winRow][0] = C4Piece.BLUE;
        board[winRow][4] = C4Piece.BLUE;
        board[winRow][5] = C4Piece.BLUE;
        board[winRow][6] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Horizontal(winRow, C4Piece.BLUE, board));
    }


    /**
     * TEST CASES FOR VERTICAL WIN CONDITION
     */
    @Test
    void verticalWin1() throws Exception {
        int winCol = 2;
        board[0][winCol] = C4Piece.RED;
        board[1][winCol] = C4Piece.RED;
        board[2][winCol] = C4Piece.RED;
        board[3][winCol] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4Vertical(0, winCol, C4Piece.RED, board));
    }

    @Test
    void verticalWin2() throws Exception {
        int winCol = 3;
        board[2][winCol] = C4Piece.RED;
        board[3][winCol] = C4Piece.RED;
        board[4][winCol] = C4Piece.RED;
        board[5][winCol] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4Vertical(2, winCol, C4Piece.RED, board));
    }

    @Test
    void verticalWin3() throws Exception {
        int winCol = 5;
        board[0][winCol] = C4Piece.BLUE;
        board[1][winCol] = C4Piece.BLUE;
        board[2][winCol] = C4Piece.BLUE;
        board[3][winCol] = C4Piece.BLUE;
        board[4][winCol] = C4Piece.BLUE; // More than 4 in a row

        assertTrue(C4WinChecker.checkC4Vertical(0, winCol, C4Piece.BLUE, board));
    }

    @Test
    void verticalNoWin1() throws Exception {
        int winCol = 6;
        board[0][winCol] = C4Piece.BLANK;
        board[1][winCol] = C4Piece.RED;
        board[2][winCol] = C4Piece.BLUE;
        board[4][winCol] = C4Piece.BLUE;
        board[5][winCol] = C4Piece.BLUE;
        assertFalse(C4WinChecker.checkC4Vertical(2, winCol, C4Piece.RED, board));
    }

    @Test
    void verticalNoWin2() throws Exception {
        int winCol = 4;
        board[1][winCol] = C4Piece.BLUE;
        board[2][winCol] = C4Piece.BLUE;
        board[4][winCol] = C4Piece.BLUE;
        board[5][winCol] = C4Piece.BLUE;

        assertFalse(C4WinChecker.checkC4Vertical(1, winCol, C4Piece.BLUE, board));
    }

    @Test
    void verticalNoWin3() throws Exception {
        int winCol = 1;
        board[0][winCol] = C4Piece.RED;
        board[1][winCol] = C4Piece.RED;
        board[2][winCol] = C4Piece.RED;

        assertFalse(C4WinChecker.checkC4Vertical(0, winCol, C4Piece.RED, board));
    }

    @Test
    void verticalNoWin4() throws Exception {
        int winCol = 0;
        assertFalse(C4WinChecker.checkC4Vertical(0, winCol, C4Piece.RED, board));
    }

    @Test
    void verticalNoWin5() throws Exception {
        int winCol = 6;
        board[1][winCol] = C4Piece.BLUE;
        board[2][winCol] = C4Piece.RED;
        board[4][winCol] = C4Piece.BLUE;
        board[5][winCol] = C4Piece.BLUE;
        assertFalse(C4WinChecker.checkC4Vertical(2, winCol, C4Piece.RED, board));
    }


    /***
     * TEST CASES FOR FORWARD SLASH WIN CONDITION
     */
    @Test
    void forwardSlashWin1() throws Exception {
        board[3][1] = C4Piece.RED;
        board[2][2] = C4Piece.RED;
        board[1][3] = C4Piece.RED;
        board[0][4] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4ForwardSlash(3, 1, C4Piece.RED, board));
    }

    @Test
    void forwardSlashWin2() throws Exception {
        board[4][2] = C4Piece.RED;
        board[3][3] = C4Piece.RED;
        board[2][4] = C4Piece.RED;
        board[1][5] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4ForwardSlash(3, 3, C4Piece.RED, board));
    }

    @Test
    void forwardSlashWin3() throws Exception {
        board[5][0] = C4Piece.BLUE;
        board[4][1] = C4Piece.BLUE;
        board[3][2] = C4Piece.BLUE;
        board[2][3] = C4Piece.BLUE;
        board[1][4] = C4Piece.BLUE;
        board[0][5] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4ForwardSlash(3, 2, C4Piece.BLUE, board));
    }

    @Test
    void forwardSlashWin4() throws Exception {
        board[3][3] = C4Piece.BLUE;
        board[2][4] = C4Piece.BLUE;
        board[1][5] = C4Piece.BLUE;
        board[0][6] = C4Piece.BLUE; // Top-right corner

        assertTrue(C4WinChecker.checkC4ForwardSlash(2, 4, C4Piece.BLUE, board));
    }

    @Test
    void forwardSlashWin5() throws Exception {
        board[5][0] = C4Piece.RED;
        board[4][1] = C4Piece.RED;
        board[3][2] = C4Piece.RED;
        board[2][3] = C4Piece.RED; // Bottom-left corner

        assertTrue(C4WinChecker.checkC4ForwardSlash(5, 0, C4Piece.RED, board));
    }

    @Test
    void forwardSlashNoWin1() throws Exception {
        board[5][1] = C4Piece.RED;
        board[4][2] = C4Piece.RED;
        board[2][4] = C4Piece.RED;
        board[1][5] = C4Piece.RED;

        assertFalse(C4WinChecker.checkC4ForwardSlash(5, 1, C4Piece.RED, board));
    }

    @Test
    void forwardSlashNoWin2() throws Exception {
        board[5][1] = C4Piece.RED;
        board[4][2] = C4Piece.RED;
        board[3][3] = C4Piece.RED; // Only three in a row

        assertFalse(C4WinChecker.checkC4ForwardSlash(5, 1, C4Piece.RED, board));
    }

    @Test
    void forwardSlashNoWin3() throws Exception {
        assertFalse(C4WinChecker.checkC4ForwardSlash(2, 2, C4Piece.RED, board));
    }


    @Test
    void forwardSlashNoWin4() throws Exception {
        board[5][0] = C4Piece.RED;
        board[4][1] = C4Piece.BLUE; // Different piece here breaks the streak
        board[3][2] = C4Piece.RED;
        board[2][3] = C4Piece.RED;
        board[1][4] = C4Piece.RED;

        assertFalse(C4WinChecker.checkC4ForwardSlash(5, 0, C4Piece.RED, board));
    }

    /**
     * TEST CASES FOR BACKWARD SLASH WIN CONDITION
     */
    @Test
    void backwardSlashWin1() throws Exception {
        board[3][5] = C4Piece.RED;
        board[2][4] = C4Piece.RED;
        board[1][3] = C4Piece.RED;
        board[0][2] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4BackwardSlash(3, 5, C4Piece.RED, board));
    }

    @Test
    void backwardSlashWin2() throws Exception {
        board[4][4] = C4Piece.RED;
        board[3][3] = C4Piece.RED;
        board[2][2] = C4Piece.RED;
        board[1][1] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4BackwardSlash(3, 3, C4Piece.RED, board));
    }

    @Test
    void backwardSlashWin3() throws Exception {
        board[5][5] = C4Piece.BLUE;
        board[4][4] = C4Piece.BLUE;
        board[3][3] = C4Piece.BLUE;
        board[2][2] = C4Piece.BLUE;
        board[1][1] = C4Piece.BLUE;
        board[0][0] = C4Piece.BLUE;

        assertTrue(C4WinChecker.checkC4BackwardSlash(3, 3, C4Piece.BLUE, board));
    }

    @Test
    void backwardSlashWin4() throws Exception {
        board[3][3] = C4Piece.BLUE;
        board[2][2] = C4Piece.BLUE;
        board[1][1] = C4Piece.BLUE;
        board[0][0] = C4Piece.BLUE; // Top-left corner

        assertTrue(C4WinChecker.checkC4BackwardSlash(2, 2, C4Piece.BLUE, board));
    }

    @Test
    void backwardSlashWin5() throws Exception {
        board[5][6] = C4Piece.RED;
        board[4][5] = C4Piece.RED;
        board[3][4] = C4Piece.RED;
        board[2][3] = C4Piece.RED;

        assertTrue(C4WinChecker.checkC4BackwardSlash(5, 6, C4Piece.RED, board));
    }

    @Test
    void backwardSlashNoWin1() throws Exception {
        board[5][5] = C4Piece.RED;
        board[4][4] = C4Piece.RED;
        board[2][2] = C4Piece.RED;
        board[1][1] = C4Piece.RED;

        assertFalse(C4WinChecker.checkC4BackwardSlash(5, 5, C4Piece.RED, board));
    }

    @Test
    void backwardSlashNoWin2() throws Exception {
        board[5][5] = C4Piece.RED;
        board[4][4] = C4Piece.RED;
        board[3][3] = C4Piece.RED; // Only three in a row

        assertFalse(C4WinChecker.checkC4BackwardSlash(5, 5, C4Piece.RED, board));
    }

    @Test
    void backwardSlashNoWin3() throws Exception {
        assertFalse(C4WinChecker.checkC4BackwardSlash(2, 2, C4Piece.RED, board));
    }


    @Test
    void backwardSlashNoWin4() throws Exception {
        board[5][5] = C4Piece.RED;
        board[4][4] = C4Piece.BLUE; // Different piece here breaks the streak
        board[3][3] = C4Piece.RED;
        board[2][2] = C4Piece.RED;
        board[1][1] = C4Piece.RED;

        assertFalse(C4WinChecker.checkC4BackwardSlash(5, 4, C4Piece.RED, board));
    }

}