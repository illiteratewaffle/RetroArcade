package client_main.java.GameLogic_Client.Checkers;


import GameLogic_Client.Checkers.CheckersBoard;
import GameLogic_Client.Checkers.CheckersMove;
import GameLogic_Client.Checkers.CheckersPiece;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CheckersBoardTest {

    int[][] getEmptyBoard(){
        int[][] ar = new int[8][];
        for(int i = 0; i < 8; i++)
            ar[i] = new int[8];
        return ar;
    }

    @Test
    void setPiece(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());

        board.setPiece(new Ivec2(0,0), CheckersPiece.P1PAWN.getValue());
        board.setPiece(new Ivec2(1,0), CheckersPiece.P1PAWN.getValue());
        board.setPiece(new Ivec2(7,7), CheckersPiece.P1PAWN.getValue());

        assertEquals(board.getPiece(new Ivec2(0,0)), CheckersPiece.P1PAWN.getValue());
    }

    @Test
    void move(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());
        CheckersMove move1 = new CheckersMove(new Ivec2(0,0), new Ivec2(1,1), null);
        CheckersMove move2 = new CheckersMove(new Ivec2(1,1), new Ivec2(0,2), null);

        board.setPiece(new Ivec2(0,0), CheckersPiece.P2PAWN.getValue());

        board.makeMove(move1);
        board.makeMove(move2);

        assertEquals(board.getPiece(new Ivec2(0,2)), CheckersPiece.P2PAWN.getValue());
        assertEquals(board.getPiece(new Ivec2(0,0)), CheckersPiece.NONE.getValue());
    }

    @Test
    void capture(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());
        CheckersMove move1 = new CheckersMove(new Ivec2(0,0), new Ivec2(2,2), new Ivec2(1,1));
        CheckersMove move2 = new CheckersMove(new Ivec2(2,2), new Ivec2(3,3), null);

        board.setPiece(new Ivec2(0,0), CheckersPiece.P2PAWN.getValue());
        board.setPiece(new Ivec2(1,1), CheckersPiece.P1KING.getValue());

        board.makeMove(move1);
        board.makeMove(move2);

        assertEquals(board.getPiece(new Ivec2(3,3)), CheckersPiece.P2PAWN.getValue());
        assertEquals(board.getPiece(new Ivec2(1,1)), CheckersPiece.NONE.getValue());
    }

    @Test
    void isP1(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());

        board.setPiece(new Ivec2(5,1), CheckersPiece.P1PAWN.getValue());

        assertEquals(board.isP1(new Ivec2(5,1)), true);
        assertEquals(board.isP1(new Ivec2(5,2)), false);
    }

    @Test
    void isP2(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());

        board.setPiece(new Ivec2(5,1), CheckersPiece.P2PAWN.getValue());

        assertEquals(board.isP2(new Ivec2(5,1)), true);
        assertEquals(board.isP2(new Ivec2(5,2)), false);
    }

    @Test
    void isKing(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());

        board.setPiece(new Ivec2(5,1), CheckersPiece.P1KING.getValue());

        assertEquals(board.isKing(new Ivec2(5,2)), false);
        assertEquals(board.isKing(new Ivec2(5,1)), true);
    }

    @Test
    void isPawn(){
        CheckersBoard board = new CheckersBoard(8,8, getEmptyBoard());

        board.setPiece(new Ivec2(5,1), CheckersPiece.P1PAWN.getValue());

        assertEquals(board.isPawn(new Ivec2(5,2)), false);
        assertEquals(board.isPawn(new Ivec2(5,1)), true);
    }
}
