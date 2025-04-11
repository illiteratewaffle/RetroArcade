package client_main.GameLogic_Client.Connnect4;

import GameLogic_Client.Connect4.C4Board;
import GameLogic_Client.Connect4.C4GameLogic;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Connect4.C4WinCheckerO1;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Connect4Test {

    @Test
    void dropPiece(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        game.c4DropPiece(2, C4Piece.RED);
        game.c4DropPiece(2, C4Piece.BLUE);

        assertEquals(C4Piece.RED, board.getC4Piece(new Ivec2(2,5)));
        assertEquals(C4Piece.BLUE, board.getC4Piece(new Ivec2(2,4)));
        assertEquals(C4Piece.BLANK, board.getC4Piece(new Ivec2(1,5)));
    }

    @Test
    void winVertical(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int i = 0; i < 4; i++)
            game.c4DropPiece(2, C4Piece.RED);

        assertTrue(C4WinCheckerO1.checkVertical(new Ivec2(2,2), C4Piece.RED, board.getC4Board()));
    }

    @Test
    void winHorizontal(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int i = 0; i < 4; i++)
            game.c4DropPiece(i, C4Piece.RED);

        for(int i = 0; i < 4; i++)
            assertTrue(C4WinCheckerO1.checkHorizontal(new Ivec2(i,5), C4Piece.RED, board.getC4Board()));
    }

    @Test
    void winForwardsSlash(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int i = 0; i < 4; i++){
            for (int j = 0; j < i; j++)
                game.c4DropPiece(i, C4Piece.BLUE);
            game.c4DropPiece(i, C4Piece.RED);
        }

        for(int i = 0; i < 4; i++)
            assertTrue(C4WinCheckerO1.checkForwardSlash(new Ivec2(i,5-i), C4Piece.RED, board.getC4Board()),
                    "Failed at: " + new Ivec2(i,5-i).toString());
    }

    @Test
    void winBackSlash(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 3-i; j++)
                game.c4DropPiece(i, C4Piece.BLUE);
            game.c4DropPiece(i, C4Piece.RED);
        }

        for(int i = 0; i < 4; i++)
            assertTrue(C4WinCheckerO1.checkBackSlash(new Ivec2(i,2+i), C4Piece.RED, board.getC4Board()),
                    "Failed at: " + new Ivec2(i,2+i).toString());
    }

    @Test
    void winVerticalErrors(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 6; y++)
                assertFalse(C4WinCheckerO1.checkVertical(new Ivec2(x,y), C4Piece.RED, board.getC4Board()),
                        "Failed at: " + new Ivec2(x,y).toString());
        }
    }

    @Test
    void winHorizontalErrors(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 6; y++)
                assertFalse(C4WinCheckerO1.checkHorizontal(new Ivec2(x,y), C4Piece.RED, board.getC4Board()),
                        "Failed at: " + new Ivec2(x,y).toString());
        }
    }

    @Test
    void winForwardsSlashErrors(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 6; y++)
                assertFalse(C4WinCheckerO1.checkForwardSlash(new Ivec2(x,y), C4Piece.RED, board.getC4Board()),
                        "Failed at: " + new Ivec2(x,y).toString());
        }
    }

    @Test
    void winBackSlashErrors(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 6; y++)
                assertFalse(C4WinCheckerO1.checkBackSlash(new Ivec2(x,y), C4Piece.RED, board.getC4Board()),
                        "Failed at: " + new Ivec2(x,y).toString());
        }
    }

    @Test
    void isWinErrors(){
        C4GameLogic game = new C4GameLogic();
        C4Board board = game.getC4Board();

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 6; y++)
                assertFalse(C4WinCheckerO1.isC4Win(new Ivec2(x,y), C4Piece.RED, board.getC4Board()),
                        "Failed at: " + new Ivec2(x,y).toString());
        }
    }

}
