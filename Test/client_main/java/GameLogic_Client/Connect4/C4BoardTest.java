package client_main.java.GameLogic_Client.Connect4;
import GameLogic_Client.Connect4.C4Board;
import GameLogic_Client.Connect4.C4Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class C4BoardTest {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final C4Piece P = C4Piece.RED;
    private static final C4Piece Q = C4Piece.BLUE;

    @BeforeEach
    void createBoard() {
        C4Board board = new C4Board(ROWS, COLS);
    }
}
