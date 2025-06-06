// setting correct package for this test class
package client_main.java.GameLogic_Client.Checkers;

// importing game logic checkers class needed for these tests
import GameLogic_Client.Checkers.CheckersBoard;
import GameLogic_Client.Checkers.CheckersMove;
import GameLogic_Client.Checkers.CheckersPiece;
import GameLogic_Client.Ivec2;

// importing junit test libraries
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// importing hashset
import java.util.HashSet;

/**
 * Checkers board test class which tests the methods inside the checkers board class
 */
public class CheckersBoardTest {
    // checkers board global variable for testing
    private CheckersBoard checkersBoard;

    /**
     * set up method runs before each test so we can test each method in the class
     */
    @BeforeEach
    public void setUp() {
        // creating, rows, cols, and board
        int rows = 8;
        int cols = 8;
        int[][] board = new int[rows][cols];
        // instantiating a new checkers board object
        checkersBoard = new CheckersBoard(rows,cols, board);
    }

    /**
     * this first test checks the initial piece locations method
     */
    @Test
    public void testInitialPieceLocations() {
        // creating two hashsets of ivec2 one for each players calling the get p1 and p2 locations methods
        HashSet<Ivec2> p1Locations = checkersBoard.getP1PieceLocations();
        HashSet<Ivec2> p2Locations = checkersBoard.getP2PieceLocations();

        // then assert not null on both p1 and p2 locations
        assertNotNull(p1Locations);
        assertNotNull(p2Locations);
        // then assert true that by p1 and p2 locations are empty
        assertTrue(p1Locations.isEmpty());
        assertTrue(p2Locations.isEmpty());
    }

    /**
     * the test is p1 test the set piece method, but we are more interested in the isp1 area
     */
    @Test
    public void testIsP1Pawn() {
        // creating a position
        Ivec2 position = new Ivec2(6, 6);
        // creating the p1 piece
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p1Piece);

        // assert that the piece is a p1 pawn
        assertTrue(checkersBoard.isP1(position));
        // assert that the piece is not a p2 of any type
        assertFalse(checkersBoard.isP2(position));
    }

    /**
     * this test is the same as test is p1 but for p2
     */
    @Test
    public void testIsP2Pawn() {
        // created position and p2 piece
        Ivec2 position = new Ivec2(7, 7);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p2Piece);

        // assert that the piece is a p2 pawn and not a p1 of any type
        assertTrue(checkersBoard.isP2(position));
        assertFalse(checkersBoard.isP1(position));
    }

    /**
     * the test is p1 test the set piece method, but we are more interested in the isp1 area
     */
    @Test
    public void testIsP1King() {
        // creating a position
        Ivec2 position = new Ivec2(6, 6);
        // creating the p1 king piece
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p1Piece);

        // assert that the piece is a p1 king
        assertTrue(checkersBoard.isP1(position));
        // assert that the piece is not a p2 of any kind
        assertFalse(checkersBoard.isP2(position));
    }

    /**
     * this test is the same as test is p1 but for p2
     */
    @Test
    public void testIsP2King() {
        // created position and p2 king piece
        Ivec2 position = new Ivec2(7, 7);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p2Piece);

        // assert that the piece is a p2 king and not a p1 of any type
        assertTrue(checkersBoard.isP2(position));
        assertFalse(checkersBoard.isP1(position));
    }


    /**
     * this test checks the set piece p1 method
     */
    @Test
    public void testSetPieceP1() {
        // creating a position vector
        Ivec2 position = new Ivec2(0, 0);
        // creating the piece using the checkers piece enum and its get value method
        int p1Piece = CheckersPiece.P1PAWN.getValue(); // Assume P1 piece has this value.

        // calling the set piece method passing in the position and the int p1 piece
        checkersBoard.setPiece(position, p1Piece);
        // assert true that the piece at position is p1
        assertTrue(checkersBoard.isP1(position));
        // assert true that there is in fact a value inside the p1 locations set that contains the position
        assertTrue(checkersBoard.getP1PieceLocations().contains(position));
    }

    /**
     * this test is identical to the one above however it checks with a p2 piece
     */
    @Test
    public void testSetPieceP2() {
        // creating the position
        Ivec2 position = new Ivec2(1, 1);
        // creating the piece using the enum and the get value method
        int p2Piece = CheckersPiece.P2PAWN.getValue(); // Assume P2 piece has this value.

        // calling set piece passing in position and p2 piece
        checkersBoard.setPiece(position, p2Piece);
        // assert that there is a p2 piece at the position
        assertTrue(checkersBoard.isP2(position));
        // assert that in the p2 hashset there is a piece at position
        assertTrue(checkersBoard.getP2PieceLocations().contains(position));
    }

    /**
     * this test checks the make move method for player 1 pawn
     */
    @Test
    public void testMakeMoveP1Pawn() {
        // Setup for the test
        Ivec2 start = new Ivec2(3, 3);  // The starting position of the piece
        Ivec2 target = new Ivec2(4, 4); // The target position of the piece
        Ivec2 capture = new Ivec2(5, 5); // The position of the captured piece (can be null if no capture)

        // Assume we have a Player 1 piece at start position
        int p1Piece = CheckersPiece.P1PAWN.getValue();
        checkersBoard.setPiece(start, p1Piece);

        // creating the move class
        CheckersMove move = new CheckersMove(start, target, capture);
        // calling make move method
        checkersBoard.makeMove(move);

        // assert that p1 locations does not contain start
        assertFalse(checkersBoard.getP1PieceLocations().contains(start), "Start position should no longer have the piece.");
        // assert that the p1 locations does contain target
        assertTrue(checkersBoard.getP1PieceLocations().contains(target), "Target position should now have the piece.");
        // assert that p1 locations does not contain capture
        assertFalse(checkersBoard.getP1PieceLocations().contains(capture), "Captured position should no longer have the piece.");
    }

    /**
     * this test checks the make move method for player 2 pawn
     */
    @Test
    public void testMakeMoveP2Pawn() {
        // Setup for the test
        Ivec2 start = new Ivec2(3, 3);  // The starting position of the piece
        Ivec2 target = new Ivec2(4, 4); // The target position of the piece
        Ivec2 capture = new Ivec2(5, 5); // The position of the captured piece (can be null if no capture)

        // Assume we have a Player 2 piece at start position
        int p2Piece = CheckersPiece.P2PAWN.getValue();
        checkersBoard.setPiece(start, p2Piece);

        // creating the move class
        CheckersMove move = new CheckersMove(start, target, capture);
        // calling make move method
        checkersBoard.makeMove(move);

        // assert that p2 locations does not contain start
        assertFalse(checkersBoard.getP2PieceLocations().contains(start), "Start position should no longer have the piece.");
        // assert that the p2 locations does contain target
        assertTrue(checkersBoard.getP2PieceLocations().contains(target), "Target position should now have the piece.");
        // assert that p2 locations does not contain capture
        assertFalse(checkersBoard.getP2PieceLocations().contains(capture), "Captured position should no longer have the piece.");
    }

    /**
     * this test checks the make move method for player 1 king
     */
    @Test
    public void testMakeMoveP1King() {
        // Setup for the test
        Ivec2 start = new Ivec2(3, 3);  // The starting position of the piece
        Ivec2 target = new Ivec2(4, 4); // The target position of the piece
        Ivec2 capture = new Ivec2(5, 5); // The position of the captured piece (can be null if no capture)

        // Assume we have a Player 1 piece at start position
        int p1Piece = CheckersPiece.P1KING.getValue();
        checkersBoard.setPiece(start, p1Piece);

        // creating the move class
        CheckersMove move = new CheckersMove(start, target, capture);
        // calling make move method
        checkersBoard.makeMove(move);

        // assert that p1 locations does not contain start
        assertFalse(checkersBoard.getP1PieceLocations().contains(start), "Start position should no longer have the piece.");
        // assert that the p1 locations does contain target
        assertTrue(checkersBoard.getP1PieceLocations().contains(target), "Target position should now have the piece.");
        // assert that p1 locations does not contain capture
        assertFalse(checkersBoard.getP1PieceLocations().contains(capture), "Captured position should no longer have the piece.");
    }

    /**
     * this test checks the make move method for player 2 king
     */
    @Test
    public void testMakeMoveP2King() {
        // Setup for the test
        Ivec2 start = new Ivec2(3, 3);  // The starting position of the piece
        Ivec2 target = new Ivec2(4, 4); // The target position of the piece
        Ivec2 capture = new Ivec2(5, 5); // The position of the captured piece (can be null if no capture)

        // Assume we have a Player 2 piece at start position
        int p2Piece = CheckersPiece.P2KING.getValue();
        checkersBoard.setPiece(start, p2Piece);

        // creating the move class
        CheckersMove move = new CheckersMove(start, target, capture);
        // calling make move method
        checkersBoard.makeMove(move);

        // assert that p2 locations does not contain start
        assertFalse(checkersBoard.getP2PieceLocations().contains(start), "Start position should no longer have the piece.");
        // assert that the p2 locations does contain target
        assertTrue(checkersBoard.getP2PieceLocations().contains(target), "Target position should now have the piece.");
        // assert that p2 locations does not contain capture
        assertFalse(checkersBoard.getP2PieceLocations().contains(capture), "Captured position should no longer have the piece.");
    }

    /**
     * this test checks the remove piece method for player 1 pawn
     */
    @Test
    public void testRemovePieceP1Pawn() {
        // creating a position and piece
        Ivec2 position = new Ivec2(2, 2);
        int p1Piece = CheckersPiece.P1PAWN.getValue();
        // calling set piece method
        checkersBoard.setPiece(position, p1Piece);

        // now call set piece again by setting the value of the piece to none
        checkersBoard.setPiece(position, CheckersPiece.NONE.getValue());
        // assert that there is no longer a piece in the locations set
        assertFalse(checkersBoard.getP1PieceLocations().contains(position));
        // assert that there is no p1 piece at that position
        assertFalse(checkersBoard.isP1(position));
    }

    /**
     * this test checks the remove piece method for player 2 pawn
     */
    @Test
    public void testRemovePieceP2Pawn() {
        // creating a position and piece
        Ivec2 position = new Ivec2(2, 2);
        int p2Piece = CheckersPiece.P2PAWN.getValue();
        // calling set piece method
        checkersBoard.setPiece(position, p2Piece);

        // now call set piece again by setting the value of the piece to none
        checkersBoard.setPiece(position, CheckersPiece.NONE.getValue());
        // assert that there is no longer a piece in the locations set
        assertFalse(checkersBoard.getP2PieceLocations().contains(position));
        // assert that there is no p1 piece at that position
        assertFalse(checkersBoard.isP2(position));
    }

    /**
     * this test checks the remove piece method for player 1 king
     */
    @Test
    public void testRemovePieceP1King() {
        // creating a position and piece
        Ivec2 position = new Ivec2(2, 2);
        int p1Piece = CheckersPiece.P1KING.getValue();
        // calling set piece method
        checkersBoard.setPiece(position, p1Piece);

        // now call set piece again by setting the value of the piece to none
        checkersBoard.setPiece(position, CheckersPiece.NONE.getValue());
        // assert that there is no longer a piece in the locations set
        assertFalse(checkersBoard.getP1PieceLocations().contains(position));
        // assert that there is no p1 piece at that position
        assertFalse(checkersBoard.isP1(position));
    }

    /**
     * this test checks the remove piece method for player 2 king
     */
    @Test
    public void testRemovePieceP2PKing() {
        // creating a position and piece
        Ivec2 position = new Ivec2(2, 2);
        int p2Piece = CheckersPiece.P2KING.getValue();
        // calling set piece method
        checkersBoard.setPiece(position, p2Piece);

        // now call set piece again by setting the value of the piece to none
        checkersBoard.setPiece(position, CheckersPiece.NONE.getValue());
        // assert that there is no longer a piece in the locations set
        assertFalse(checkersBoard.getP2PieceLocations().contains(position));
        // assert that there is no p1 piece at that position
        assertFalse(checkersBoard.isP2(position));
    }

    /**
     * this test checks if the location we try to move to is valid
     */
    @Test
    public void testIsValidTile() {
        // creating a valid and invalid location vector
        Ivec2 validTile = new Ivec2(3, 3);
        Ivec2 invalidTile = new Ivec2(8, 8);

        // assert that the valid vector is valid
        assertTrue(checkersBoard.isValidTile(validTile));
        // assert the invalid vector is invalid
        assertFalse(checkersBoard.isValidTile(invalidTile));
    }

    /**
     * this test checks if the piece is a piece
     */
    @Test
    public void testIsPieceP1Pawn() {
        // creating a vector and piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // calling set piece
        checkersBoard.setPiece(position, p1Piece);

        // asserting that there is a piece at that location
        assertTrue(checkersBoard.isPiece(position));
        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * this test checks if the piece is a piece
     */
    @Test
    public void testIsPieceP2Pawn() {
        // creating a vector and piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // calling set piece
        checkersBoard.setPiece(position, p2Piece);

        // asserting that there is a piece at that location
        assertTrue(checkersBoard.isPiece(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * this test checks if the piece is a piece
     */
    @Test
    public void testIsPieceP1King() {
        // creating a vector and piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // calling set piece
        checkersBoard.setPiece(position, p1Piece);

        // asserting that there is a piece at that location
        assertTrue(checkersBoard.isPiece(position));
        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * this test checks if the piece is a piece
     */
    @Test
    public void testIsPieceP2King() {
        // creating a vector and piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // calling set piece
        checkersBoard.setPiece(position, p2Piece);

        // asserting that there is a piece at that location
        assertTrue(checkersBoard.isPiece(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * tests if the p1 piece is a king
     */
    @Test
    public void testIsKingP1() {
        // create a position value and a piece that is a king
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);
        // assert that the piece at position is a king
        assertTrue(checkersBoard.isKing(position));
        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * tests if the p2 piece is a king
     */
    @Test
    public void testIsKingP2() {
        // create a position value and a piece that is a king
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // assert that the piece at position is a king
        assertTrue(checkersBoard.isKing(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * test the is king method when the p1 piece is not a king
     */
    @Test
    public void testIsNotKingP1() {
        // create a position value and a piece that is not a king
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);
        // assert that the piece is not a king
        assertFalse(checkersBoard.isKing(position));
        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * test the is king method when the p2 piece is not a king
     */
    @Test
    public void testIsNotKingP2() {
        // create a position value and a piece that is not a king
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // assert that the piece is not a king
        assertFalse(checkersBoard.isKing(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * test the is pawn method when the p1 piece is a pawn
     */
    @Test
    public void testIsPawnP1() {
        // create a position value and a piece that is a pawn
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);
        // assert that the piece is a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is p1
        assertFalse(checkersBoard.isP2(position));
    }

    /**
     * test the is pawn method when the p2 piece is a pawn
     */
    @Test
    public void testIsPawnP2() {
        // create a position value and a piece that is a pawn
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // assert that the piece is a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * test the is pawn method when the p1 piece is not a pawn
     */
    @Test
    public void testIsNotPawnP1() {
        // create a position value and a piece that is not a pawn
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);
        // assert that the piece is not a pawn
        assertFalse(checkersBoard.isPawn(position));
        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * test the is pawn method when the p2 piece is not a pawn
     */
    @Test
    public void testIsNotPawnP2() {
        // create a position value and a piece that is not a pawn
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // assert that the piece is not a pawn
        assertFalse(checkersBoard.isPawn(position));
        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * tests the make pawn method for a p1 player
     */
    @Test
    public void testMakePawnP1() {
        // create position and p1 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call set piece
        checkersBoard.setPiece(position, p1Piece);
        // then call the make pawn method
        checkersBoard.makePawn(position);

        // assert that the piece is now a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is still a p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * tests the make pawn method for p2
     */
    @Test
    public void testMakePawnP2() {
        // creating a position and p2 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // call the make pawn method position in the position
        checkersBoard.makePawn(position);

        // assert the piece is now a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is still p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * test for converting a pawn to a pawn for p1
     */
    @Test
    public void testMakePawnPawnP1() {
        // creating a position and p1 pawn
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // calling set piece
        checkersBoard.setPiece(position, p1Piece);

        // asserting this will throw and illegal argument exception when make pawn is called
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makePawn(position));
        // assert the piece is still a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is still p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * test for converting a pawn to a pawn for p2
     */
    @Test
    public void testMakePawnPawnP2() {
        // creating a position and p2 pawn
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);

        // asserting this will throw and illegal argument exception when make pawn is called
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makePawn(position));
        // assert the piece is still a pawn
        assertTrue(checkersBoard.isPawn(position));
        // assert the piece is still p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * test to try to convert an empty space on the board to pawn
     */
    @Test
    public void testMakePawnNone() {
        // creation and position and empty piece
        Ivec2 position = new Ivec2(5, 5);
        int piece = CheckersPiece.NONE.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, piece);

        // asserting this will throw and illegal argument exception when make pawn is called
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makePawn(position));
        // assert there is no piece p1 or p2 at position
        assertFalse(checkersBoard.isPiece(position));
    }

    /**
     * test making a king from a pawn using p1
     */
    @Test
    public void testMakeKingP1() {
        // create a position and piece for p1
        Ivec2 position = new Ivec2(7, 7);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p1Piece);
        // call make king method
        checkersBoard.makeKing(position);

        // assert the piece is still p1
        assertTrue(checkersBoard.isP1(position));
        // assert piece is a king
        assertTrue(checkersBoard.isKing(position));
    }

    @Test
    public void testMakeKingP2() {
        // create a position and piece for p2
        Ivec2 position = new Ivec2(7, 7);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call set piece method
        checkersBoard.setPiece(position, p2Piece);
        // call make king method
        checkersBoard.makeKing(position);

        // assert that the piece is still p2
        assertTrue(checkersBoard.isP2(position));
        // assert the piece is a king
        assertTrue(checkersBoard.isKing(position));
    }

    /**
     * tests converting a king to a king with p1
     */
    @Test
    public void testMakeKingKingP1() {
        // creating a position and p1 king piece
        Ivec2 position = new Ivec2(7, 7);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, p1Piece);

        // Illegal argument when makeKing is called on a non-pawn piece.
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeKing(position));
        // assert the piece is still p1
        assertTrue(checkersBoard.isP1(position));
        // assert the piece is still a king
        assertTrue(checkersBoard.isKing(position));
    }

    /**
     * tests converting a king to a king with p2
     */
    @Test
    public void testMakeKingKingP2() {
        // creating a position and p2 king piece
        Ivec2 position = new Ivec2(7, 7);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, p2Piece);

        // Illegal argument when makeKing is called on a non-pawn piece.
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeKing(position));
        // assert the piece is still p2
        assertTrue(checkersBoard.isP2(position));
        // assert the piece is still a king
        assertTrue(checkersBoard.isKing(position));
    }

    /**
     * tests make king on a position with no piece
     */
    @Test
    public void testMakeKingNone() {
        // creating position and a none piece
        Ivec2 position = new Ivec2(7, 7);
        int piece = CheckersPiece.NONE.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, piece);

        // Illegal argument when makeKing is called on a non-pawn piece.
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeKing(position));
        // assert that there is no piece p1 or p2 at position
        assertFalse(checkersBoard.isPiece(position));
    }

    /**
     * tests for making a p1 pawn piece from a p2 pawn piece
     */
    @Test
    public void testMakeP1Pawn() {
        // making a position and p2 pawn piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, p2Piece);
        // calling make p1 method
        checkersBoard.makeP1(position);

        // assert the piece is no1 a piece
        assertTrue(checkersBoard.isP1(position));
        // assert the piece is still a pawn
        assertTrue(checkersBoard.isPawn(position));
    }

    /**
     * tests for making a p1 king piece from a p2 king piece
     */
    @Test
    public void testMakeP1King() {
        // making a position and p2 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);
        // call the make p1 method
        checkersBoard.makeP1(position);

        // assert the piece is p1
        assertTrue(checkersBoard.isP1(position));
        // asser the piece is still a king
        assertTrue(checkersBoard.isKing(position));
    }

    /**
     * tests converting a p1 pawn to a p1 pawn
     */
    @Test
    public void testMakeP1P1Pawn() {
        // creating a position and p1 pawn piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);

        // assert the make p1 throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP1(position));
        // assert the piece is still a p1
        assertTrue(checkersBoard.isP1(position));
    }

    /**
     * tests converting a p1 pawn to a p1 pawn
     */
    @Test
    public void testMakeP1P1King() {
        // creating a position and p1 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);

        // assert the make p1 throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP1(position));
        // assert the piece is still a p1
        assertTrue(checkersBoard.isP1(position));
    }

    @Test
    public void testMakeP1None() {
        // creating a position and none piece
        Ivec2 position = new Ivec2(5, 5);
        int piece = CheckersPiece.NONE.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, piece);
        // assert that the make p1 method throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP1(position));
        // assert there is no piece at position
        assertFalse(checkersBoard.isPiece(position));
    }


    /**
     * tests for making a p2 pawn piece from a p1 pawn piece
     */
    @Test
    public void testMakeP2Pawn() {
        // making a position and p2 pawn piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        // calling set piece method
        checkersBoard.setPiece(position, p1Piece);
        // calling make p2 method
        checkersBoard.makeP2(position);

        // assert the piece is no1 a piece
        assertTrue(checkersBoard.isP2(position));
        // assert the piece is still a pawn
        assertTrue(checkersBoard.isPawn(position));
    }

    /**
     * tests for making a p2 king piece from a p1 king piece
     */
    @Test
    public void testMakeP2King() {
        // making a position and p1 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p1Piece);
        // call the make p2 method
        checkersBoard.makeP2(position);

        // assert the piece is p2
        assertTrue(checkersBoard.isP2(position));
        // assert the piece is still a king
        assertTrue(checkersBoard.isKing(position));
    }

    /**
     * tests converting a p2 pawn to a p2 pawn
     */
    @Test
    public void testMakeP2P2Pawn() {
        // creating a position and p2 pawn piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);

        // assert the make p1 throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP2(position));
        // assert the piece is still a p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * tests converting a p2 pawn to a p2 king
     */
    @Test
    public void testMakeP2P2King() {
        // creating a position and p2 king piece
        Ivec2 position = new Ivec2(5, 5);
        int p2Piece = CheckersPiece.P2KING.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, p2Piece);

        // assert the make p2 throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP2(position));
        // assert the piece is still a p2
        assertTrue(checkersBoard.isP2(position));
    }

    /**
     * Make p2 piece to none
     */
    @Test
    public void testMakeP2None() {
        // creating a position and none piece
        Ivec2 position = new Ivec2(5, 5);
        int piece = CheckersPiece.NONE.getValue();

        // call the set piece method
        checkersBoard.setPiece(position, piece);
        // assert that the make p2 method throws an exception
        assertThrows(IllegalArgumentException.class, () -> checkersBoard.makeP2(position));
        // assert there is no piece at position
        assertFalse(checkersBoard.isPiece(position));
    }
}
