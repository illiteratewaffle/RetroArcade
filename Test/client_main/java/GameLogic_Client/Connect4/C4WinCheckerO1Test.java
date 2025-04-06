package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.ivec2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import GameLogic_Client.Connect4.C4WinCheckerO1;

public class C4WinCheckerO1Test {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final C4Piece P = C4Piece.RED;
    private static final C4Piece Q = C4Piece.BLUE;

    private C4Piece[][] emptyBoard() {
        return new C4Piece[ROWS][COLS];
    }

    @Test
    void horizontalWin1() {
        C4Piece[][] board = emptyBoard();
        board[2][1] = P;
        board[2][2] = P;
        board[2][3] = P;
        board[2][4] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new ivec2(4, 2), P, board));
    }

    @Test
    void horizontalWin2() {
        C4Piece[][] board = emptyBoard();
        board[0][0] = P;
        board[0][1] = P;
        board[0][2] = P;
        board[0][3] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new ivec2(0, 0), P, board));
    }

    @Test
    void horizontalWin3() {
        C4Piece[][] board = emptyBoard();
        board[3][1] = P;
        board[3][2] = P;
        board[3][3] = P;
        board[3][4] = P;
        board[3][5] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new ivec2(3, 3), P, board));
    }

    @Test
    void horizontalWin4() {
        C4Piece[][] board = emptyBoard();
        board[5][3] = P;
        board[5][4] = P;
        board[5][5] = P;
        board[5][6] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new ivec2(5, 5), P, board));
    }

    @Test
    void horizontalNoWin1() {
        C4Piece[][] board = emptyBoard();
        board[1][2] = P;
        board[1][3] = P;
        board[1][4] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(3, 1), P, board));
    }

    @Test
    void horizontalNoWin2() {
        C4Piece[][] board = emptyBoard();
        board[0][0] = P;
        board[0][3] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(3, 0), P, board));
    }

    @Test
    void horizontalNoWin3() {
        C4Piece[][] board = emptyBoard();
        board[4][0] = P;
        board[4][1] = P;
        board[4][2] = null;
        board[4][3] = P;
        board[4][4] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(4, 4), P, board));
    }

    @Test
    void horizontalNoWin4() {
        C4Piece[][] board = emptyBoard();
        board[3][3] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(3, 3), P, board));
    }

    @Test
    void horizontalNoWin5() {
        C4Piece[][] board = emptyBoard();
        board[2][0] = P;
        board[2][1] = P;
        board[2][2] = P;
        board[2][3] = Q;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(2, 2), P, board));
    }

    @Test
    void horizontalNoWin6() {
        C4Piece[][] board = emptyBoard();
        board[5][6] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new ivec2(6, 5), P, board));
    }
}
