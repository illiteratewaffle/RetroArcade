// setting the correct package
package client_main.java.GameLogic_Client.Checkers;

// class imports for this test class. The imports are the highlight board class, the highlight type enum, and the ivec2 class
import GameLogic_Client.Checkers.CheckersHighlightBoard;
import GameLogic_Client.Checkers.CheckersHighlightType;
import GameLogic_Client.Ivec2;

// importing junit libraries test, before each, and assertions
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * highlight board test class
 */
public class CheckersHighlightBoardTest {
    // creating a highlight board and coordinate vector
    private CheckersHighlightBoard board;
    private Ivec2 tileCoordinate;

    /**
     * this block runs before each test to setup the highlight board
     */
    @BeforeEach
    public void setUp() {
        // Set up a 8x8 board
        int[][] initialBoard = new int[8][8];
        // creating the highlight board object
        board = new CheckersHighlightBoard(8, 8, initialBoard);
        // create the tile coordinate vector
        tileCoordinate = new Ivec2(3, 4); // Set a coordinate for testing
    }

    /**
     * this test checks if the highlight type of the vector is PIECE
     */
    @Test
    public void testMarkTile_withPieceHighlight() {
        // Test marking a tile with the piece highlight
        CheckersHighlightType highlight = CheckersHighlightType.PIECE;
        board.markTile(tileCoordinate, highlight);

        // Assert the tile at the coordinate has the highlight type piece
        assertEquals(highlight.getValue(), board.getPiece(tileCoordinate));
    }

    /**
     * this test checks if the highlight type of the vector is SELECTED_PIECE
     */
    @Test
    public void testMarkTile_withSelectPieceHighlight() {
        // Test marking a tile with the selected piece highlight
        CheckersHighlightType highlight = CheckersHighlightType.SELECTED_PIECE;
        board.markTile(tileCoordinate, highlight);

        // Assert the tile at the coordinate has the highlight type selected piece
        assertEquals(highlight.getValue(), board.getPiece(tileCoordinate));
    }

    /**
     * this test checks if the highlight type of the vector is CAPTURE
     */
    @Test
    public void testMarkTile_withCaptureHighlight() {
        // Test marking a tile with the capture highlight
        CheckersHighlightType highlight = CheckersHighlightType.CAPTURE;
        board.markTile(tileCoordinate, highlight);

        // Assert the tile at the coordinate has the capture type
        assertEquals(highlight.getValue(), board.getPiece(tileCoordinate));
    }

    /**
     * this test checks if the highlight type of the vector is TILE
     */
    @Test
    public void testMarkTile_withTileHighlight() {
        // Test marking a tile with the tile highlight
        CheckersHighlightType highlight = CheckersHighlightType.TILE;
        board.markTile(tileCoordinate, highlight);

        // Assert the tile at the coordinate has the tile highlight
        assertEquals(highlight.getValue(), board.getPiece(tileCoordinate));
    }

    /**
     * this test checks if the highlight type of the vector is Null
     */
    @Test
    public void testMarkTile_withNullHighlight() {
        // Test marking a tile with a null highlight
        board.markTile(tileCoordinate, null);

        // Assert the tile at the coordinate is reset to 0
        assertEquals(0, board.getPiece(tileCoordinate));
    }
}
