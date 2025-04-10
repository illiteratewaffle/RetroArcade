package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Ivec2;
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
        assertTrue(C4WinCheckerO1.checkHorizontal(new Ivec2(4, 2), P, board));
    }

    @Test
    void horizontalWin2() {
        C4Piece[][] board = emptyBoard();
        board[0][0] = P;
        board[0][1] = P;
        board[0][2] = P;
        board[0][3] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new Ivec2(0, 0), P, board));
    }

    @Test
    void horizontalWin3() {
        C4Piece[][] board = emptyBoard();
        board[3][1] = P;
        board[3][2] = P;
        board[3][3] = P;
        board[3][4] = P;
        board[3][5] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new Ivec2(3, 3), P, board));
    }

    @Test
    void horizontalWin4() {
        C4Piece[][] board = emptyBoard();
        board[5][3] = P;
        board[5][4] = P;
        board[5][5] = P;
        board[5][6] = P;
        assertTrue(C4WinCheckerO1.checkHorizontal(new Ivec2(5, 5), P, board));
    }

    @Test
    void horizontalNoWin1() {
        C4Piece[][] board = emptyBoard();
        board[1][2] = P;
        board[1][3] = P;
        board[1][4] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(3, 1), P, board));
    }

    @Test
    void horizontalNoWin2() {
        C4Piece[][] board = emptyBoard();
        board[0][0] = P;
        board[0][3] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(3, 0), P, board));
    }

    @Test
    void horizontalNoWin3() {
        C4Piece[][] board = emptyBoard();
        board[4][0] = P;
        board[4][1] = P;
        board[4][2] = null;
        board[4][3] = P;
        board[4][4] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(4, 4), P, board));
    }

    @Test
    void horizontalNoWin4() {
        C4Piece[][] board = emptyBoard();
        board[3][3] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(3, 3), P, board));
    }

    @Test
    void horizontalNoWin5() {
        C4Piece[][] board = emptyBoard();
        board[2][0] = P;
        board[2][1] = P;
        board[2][2] = P;
        board[2][3] = Q;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(2, 2), P, board));
    }

    @Test
    void horizontalNoWin6() {
        C4Piece[][] board = emptyBoard();
        board[5][6] = P;
        assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(6, 5), P, board));
    }


    /**
     * TEST CASES FOR VERTICAL WIN CONDITION
     */
    @Test
    void verticalWin1() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 2;

        board[0][winCol] = P;
        board[1][winCol] = P;
        board[2][winCol] = P;
        board[3][winCol] = P;

        assertTrue(C4WinCheckerO1.checkVertical(new Ivec2(2, 0), P, board));
    }

    @Test
    void verticalWin2() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 3;

        board[2][winCol] = P;
        board[3][winCol] = P;
        board[4][winCol] = P;
        board[5][winCol] = P;

        assertTrue(C4WinCheckerO1.checkVertical(new Ivec2(3, 2), P, board));
    }

    @Test
    void verticalWin3() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 5;

        board[1][winCol] = P;
        board[2][winCol] = P;
        board[3][winCol] = P;
        board[4][winCol] = P;
        board[5][winCol] = P;

        assertTrue(C4WinCheckerO1.checkVertical(new Ivec2(5, 1), P, board));
    }

    @Test
    void verticalNoWin1() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 6;

        board[0][winCol] = P;
        board[1][winCol] = Q;
        board[3][winCol] = P;
        board[4][winCol] = P;
        board[5][winCol] = P;

        assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(winCol, 1), P, board));
    }

    @Test
    void verticalNoWin2() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 4;

        board[1][winCol] = Q;
        board[2][winCol] = Q;
        board[4][winCol] = Q;
        board[5][winCol] = Q;

        assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(winCol, 1), Q, board));
    }

    @Test
    void verticalNoWin3() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 1;

        board[1][winCol] = Q;
        board[2][winCol] = Q;
        board[4][winCol] = Q;

        assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(winCol, 1), Q, board));
    }

    @Test
    void verticalNoWin4() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 0;

        assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(winCol, 0), P, board));
    }

    @Test
    void verticalNoWin5() throws Exception {
        C4Piece[][] board = emptyBoard();
        int winCol = 6;

        board[0][winCol] = P;
        board[1][winCol] = Q;
        board[3][winCol] = P;
        board[4][winCol] = P;
        board[5][winCol] = P;

        assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(winCol, 0), P, board));
    }


    /***
     * TEST CASES FOR FORWARD SLASH WIN CONDITION
     */
    @Test
    void forwardSlashWin1() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[3][1] = P;
        board[2][2] = P;
        board[1][3] = P;
        board[0][4] = P;

        assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(2, 2), P, board));
    }

    @Test
    void forwardSlashWin2() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[4][2] = P;
        board[3][3] = P;
        board[2][4] = P;
        board[1][5] = P;

        assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(2, 4), P, board));
    }

    @Test
    void forwardSlashWin3() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][0] = Q;
        board[4][1] = Q;
        board[3][2] = Q;
        board[2][3] = Q;
        board[1][4] = Q;
        board[0][5] = Q;

        assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(0, 5), Q, board));
    }

    @Test
    void forwardSlashWin4() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[3][3] = Q;
        board[2][4] = Q;
        board[1][5] = Q;
        board[0][6] = Q; // Top-right corner

        assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(4, 2), Q, board));
    }

    @Test
    void forwardSlashWin5() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][0] = P;
        board[4][1] = P;
        board[3][2] = P;
        board[2][3] = P; // Bottom-left corner

        assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(3, 2), P, board));
    }

    @Test
    void forwardSlashNoWin1() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][1] = P;
        board[4][2] = P;
        board[2][4] = P;
        board[1][5] = P;

        assertFalse(C4WinCheckerO1.checkForwardSlash(new Ivec2(5, 1), P, board));
    }

    @Test
    void forwardSlashNoWin2() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][1] = P;
        board[4][2] = P;
        board[3][3] = P; // Only three in a row

        assertFalse(C4WinCheckerO1.checkForwardSlash(new Ivec2(3, 3), P, board));
    }

    @Test
    void forwardSlashNoWin3() throws Exception {
        C4Piece[][] board = emptyBoard();

        assertFalse(C4WinCheckerO1.checkForwardSlash(new Ivec2(2, 2), P, board));
    }


    @Test
    void forwardSlashNoWin4() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][0] = P;
        board[4][1] = Q;
        board[3][2] = P;
        board[2][3] = P;
        board[1][4] = P;

        assertFalse(C4WinCheckerO1.checkForwardSlash(new Ivec2(4, 1), P, board));
    }

    /**
     * TEST CASES FOR BACKWARD SLASH WIN CONDITION
     */
    @Test
    void backwardSlashWin1() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[3][5] = P;
        board[2][4] = P;
        board[1][3] = P;
        board[0][2] = P;

        assertTrue(C4WinCheckerO1.checkBackSlash(new Ivec2(2, 0), P, board));
    }

    @Test
    void backwardSlashWin2() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[4][4] = P;
        board[3][3] = P;
        board[2][2] = P;
        board[1][1] = P;

        assertTrue(C4WinCheckerO1.checkBackSlash(new Ivec2(1, 1), P, board));
    }

    @Test
    void backwardSlashWin3() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][5] = Q;
        board[4][4] = Q;
        board[3][3] = Q;
        board[2][2] = Q;
        board[1][1] = Q;
        board[0][0] = Q;

        assertTrue(C4WinCheckerO1.checkBackSlash(new Ivec2(0, 0), Q, board));
    }

    @Test
    void backwardSlashWin4() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[3][3] = Q;
        board[2][2] = Q;
        board[1][1] = Q;
        board[0][0] = Q;

        assertTrue(C4WinCheckerO1.checkBackSlash(new Ivec2(2, 2), Q, board));
    }

    @Test
    void backwardSlashNoWin1() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][6] = P;
        board[4][5] = P;
        board[3][4] = P;
        board[1][2] = P;

        assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(2, 1), P, board));
    }

    @Test
    void backwardSlashNoWin2() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][5] = P;
        board[4][4] = P;
        board[3][3] = P; // Only three in a row

        assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(3, 3), P, board));
    }

    @Test
    void backwardSlashNoWin3() throws Exception {
        C4Piece[][] board = emptyBoard();

        assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(1, 1), Q, board));
    }

    @Test
    void backwardSlashNoWin4() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[5][5] = P;
        board[4][4] = Q; // Different piece here breaks the streak
        board[3][3] = P;
        board[2][2] = P;
        board[1][1] = P;

        assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(1, 1), P, board));
    }

    @Test
    void backwardSlashNoWin5() throws Exception {
        C4Piece[][] board = emptyBoard();

        board[4][4] = P;
        board[3][3] = P;
        board[2][2] = P;
        board[1][1] = P;

        assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(1, 1), Q, board));
    }
}
