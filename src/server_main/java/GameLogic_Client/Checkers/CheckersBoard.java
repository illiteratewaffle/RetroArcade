package GameLogic_Client.Checkers;

import GameLogic_Client.AbstractBoard;
import GameLogic_Client.Ivec2;

import java.util.HashSet;

// inherits from Resources.AbstractBoard.java
public class CheckersBoard extends AbstractBoard {
    // To-do: Add comparison methods (and other basic addition operations) to ivec2.
    private HashSet<Ivec2> p1PieceLocations = new HashSet<>();
    private HashSet<Ivec2> p2PieceLocations = new HashSet<>();


    public CheckersBoard(int rows, int cols, int[][] board) {
        super(rows, cols, board);
        // Iterate through all the tiles and seek out the location of pieces of each player.
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Ivec2 point = new Ivec2(x, y);
                int PieceID = getPiece(point);
                // If the tile contains a piece, record it to the appropriate set of pieces.
                if (CheckersPiece.isPiece(PieceID)) {
                    if (CheckersPiece.isP1(PieceID)) {
                        p1PieceLocations.add(point);
                    } else {
                        p2PieceLocations.add(point);
                    }
                }
            }
        }
    }


    /**
     * @return A read-only set of coordinates where P1 pieces are stored at.
     */
    public final HashSet<Ivec2> getP1PieceLocations() {
        return p1PieceLocations;
    }

    /**
     * @return A read-only set of coordinates where P2 pieces are stored at.
     */
    public final HashSet<Ivec2> getP2PieceLocations() {
        return p2PieceLocations;
    }


    @Override
    public void setPiece(Ivec2 point, int piece) {
        int oldPieceID = getPiece(point);
        // If the value of the tile is changed, we may need to update the location sets.
        if (oldPieceID != piece) {
            // Check if we just removed a piece from the board tile.
            if (CheckersPiece.isPiece(oldPieceID)) {
                // The tile previously holds a P1 piece, which is now removed.
                if (CheckersPiece.isP1(oldPieceID)) {
                    p1PieceLocations.remove(point);
                }
                // The tile previously holds a P2 piece, which is now removed.
                else {
                    p2PieceLocations.remove(point);
                }
            }

            // Check if we are adding a new piece in its place.
            if (CheckersPiece.isPiece(piece)) {
                // A new P1 piece is added in its place.
                if (CheckersPiece.isP1(piece)) {
                    p1PieceLocations.add(point);
                }
                // A new P2 piece is added in its place.
                else {
                    p2PieceLocations.add(point);
                }
            } else {
                // Ensure that we will not set any tiles to contain an invalid piece.
                piece = CheckersPiece.NONE.getValue();
            }
            // We do not consider any other situations.
        }
        super.setPiece(point, piece);
    }


    /**
     * Perform a sequence of set operations that would alter the pieces on several tiles
     * to mimic the effect of a move in checkers.
     *
     * @param move The move to perform.
     */
    public void makeMove(CheckersMove move) {
        // Perform the relevant sets, as dictated by the move.
        // Move the piece in the start position of the move to the target position.
        setPiece(move.getTargetCoordinate(), getPiece(move.getStartCoordinate()));
        // This means that the tile at the start position must be cleared.
        setPiece(move.getStartCoordinate(), CheckersPiece.NONE.getValue());
        // If there is a capture, additionally clear the tile at the capture position.
        if (move.getCaptureCoordinate() != null) {
            setPiece(move.getCaptureCoordinate(), CheckersPiece.NONE.getValue());
        }
        return;
    }


    /**
     * @param tileCoord
     * @return True if the tileCoord points to a Tile that is on the Board; False otherwise.
     */
    public boolean isValidTile(Ivec2 tileCoord) {
        Ivec2 boardSize = getSize();
        return tileCoord.x >= 0 && tileCoord.x < boardSize.x && tileCoord.y >= 0 && tileCoord.y < boardSize.y;
    }


    /**
     * @param tileCoord
     * @return True if there is a valid Checkers Piece at the specified TileCoord; False otherwise.
     */
    public boolean isPiece(Ivec2 tileCoord) {
        return CheckersPiece.isPiece(getPiece(tileCoord));
    }

    /**
     * @param tileCoord
     * @return True if the Piece at the specified tileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean isP1(Ivec2 tileCoord) {
        return CheckersPiece.isP1(getPiece(tileCoord));
    }

    /**
     * @param tileCoord
     * @return True if the Piece at the specified TileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean isP2(Ivec2 tileCoord) {
        return CheckersPiece.isP2(getPiece(tileCoord));
    }


    /**
     * @param tileCoord
     * @return True if the Piece at the specified TileCoord is a King Piece; False otherwise.
     */
    public boolean isKing(Ivec2 tileCoord) {
        return CheckersPiece.isKing(getPiece(tileCoord));
    }

    /**
     * @param tileCoord
     * @return True if the Piece at the specified tileCoord is a Pawn Piece; False otherwise.
     */
    public boolean isPawn(Ivec2 tileCoord) {
        return CheckersPiece.isPawn(getPiece(tileCoord));
    }


    /**
     * Convert a Pawn Piece at the specified Tile Coordinate into a King Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     *
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException  If the specified tile does not contain a Pawn Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeKing(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isValidTile(tileCoord)) {
            int pieceID = getPiece(tileCoord);
            if (CheckersPiece.isPawn(pieceID)) {
                if (CheckersPiece.isP1(getPiece(tileCoord))) {
                    setPiece(tileCoord, CheckersPiece.P1KING.getValue());
                } else {
                    setPiece(tileCoord, CheckersPiece.P2KING.getValue());
                }
            } else throw new IllegalArgumentException("Can only make Pawn Pieces into Kings.");
        } else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a King Piece at the specified Tile Coordinate into a Pawn Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     *
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException  If the specified tile does not contain a King Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makePawn(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isValidTile(tileCoord)) {
            int PieceID = getPiece(tileCoord);
            if (CheckersPiece.isKing(PieceID)) {
                if (CheckersPiece.isP1(getPiece(tileCoord))) {
                    setPiece(tileCoord, CheckersPiece.P1PAWN.getValue());
                } else {
                    setPiece(tileCoord, CheckersPiece.P2PAWN.getValue());
                }
            } else throw new IllegalArgumentException("Can only make King Pieces into Pawns.");
        } else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a Player 2's Piece at the specified Tile Coordinate into a Player 1's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     *
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException  If the specified tile does not contain a Player 2's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeP1(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isValidTile(tileCoord)) {
            int pieceID = getPiece(tileCoord);
            if (CheckersPiece.isP2(pieceID)) {
                if (CheckersPiece.isPawn(getPiece(tileCoord))) {
                    setPiece(tileCoord, CheckersPiece.P1PAWN.getValue());
                } else {
                    setPiece(tileCoord, CheckersPiece.P1KING.getValue());
                }
            } else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        } else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }


    /**
     * Convert a Player 1's Piece at the specified Tile Coordinate into a Player 2's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     *
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException  If the specified tile does not contain a Player 1's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeP2(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isValidTile(tileCoord)) {
            int pieceID = getPiece(tileCoord);
            if (CheckersPiece.isP1(pieceID)) {
                if (CheckersPiece.isPawn(getPiece(tileCoord))) {
                    setPiece(tileCoord, CheckersPiece.P2PAWN.getValue());
                } else {
                    setPiece(tileCoord, CheckersPiece.P2KING.getValue());
                }
            } else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        } else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }
}