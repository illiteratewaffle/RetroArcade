package GameLogic_Client.Checkers;

// game logic class imports
import GameLogic_Client.AbstractBoard;
import GameLogic_Client.Ivec2;

// importing hash set
import java.util.HashSet;

/**
 * inherits from Resources.AbstractBoard.java
 */
public class CheckersBoard extends AbstractBoard
{
    // p1 and p2 piece locations
    private HashSet<Ivec2> p1PieceLocations = new HashSet<>();
    private HashSet<Ivec2> p2PieceLocations = new HashSet<>();


    /**
     * checkers board constructor which creates the board to play checkers on
     * @param rows the number of rows on the board
     * @param cols the number of columns of the board
     * @param board the 2d int array where the board will be stored
     */
    public CheckersBoard(int rows, int cols, int[][] board)
    {
        // instantiating the super class
        super(rows, cols, board);
        // Iterate through all the tiles and seek out the location of pieces of each player.
        for (int x = 0; x < cols; x++)
        {
            for (int y = 0; y < rows; y++)
            {
                Ivec2 point = new Ivec2(x, y);
                int PieceID = getPiece(point);
                // If the tile contains a piece, record it to the appropriate set of pieces.
                if (CheckersPiece.isPiece(PieceID))
                {
                    if (CheckersPiece.isP1(PieceID))
                    {
                        p1PieceLocations.add(point);
                    }
                    else
                    {
                        p2PieceLocations.add(point);
                    }
                }
            }
        }
    }


    /**
     * @return A read-only set of coordinates where P1 pieces are stored at.
     */
    public final HashSet<Ivec2> getP1PieceLocations()
    {
        return p1PieceLocations;
    }

    /**
     * @return A read-only set of coordinates where P2 pieces are stored at.
     */
    public final HashSet<Ivec2> getP2PieceLocations()
    {
        return p2PieceLocations;
    }


    /**
     * set piece method which sets the location of a piece from one spot to another
     * @param point the coordinate point which the piece should be set at.
     * @param piece the type of piece that should be set at the location.
     */
    @Override
    public void setPiece(Ivec2 point, int piece)
    {
        int oldPieceID = getPiece(point);
        // If the value of the tile is changed, we may need to update the location sets.
        if (oldPieceID != piece)
        {
            // Check if we just removed a piece from the board tile.
            if (CheckersPiece.isPiece(oldPieceID))
            {
                // The tile previously holds a P1 piece, which is now removed.
                if (CheckersPiece.isP1(oldPieceID))
                {
                    p1PieceLocations.remove(point);
                }
                // The tile previously holds a P2 piece, which is now removed.
                else
                {
                    p2PieceLocations.remove(point);
                }
            }

            // Check if we are adding a new piece in its place.
            if (CheckersPiece.isPiece(piece))
            {
                // A new P1 piece is added in its place.
                if (CheckersPiece.isP1(piece))
                {
                    p1PieceLocations.add(point);
                }
                // A new P2 piece is added in its place.
                else
                {
                    p2PieceLocations.add(point);
                }
            }
            else
            {
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
     * @param move The move to perform.
     */
    public void makeMove(CheckersMove move)
    {
        // Perform the relevant sets, as dictated by the move.
        // Move the piece in the start position of the move to the target position.
        setPiece(move.getTargetCoordinate(), getPiece(move.getStartCoordinate()));
        // This means that the tile at the start position must be cleared.
        setPiece(move.getStartCoordinate(), CheckersPiece.NONE.getValue());
        // If there is a capture, additionally clear the tile at the capture position.
        if (move.getCaptureCoordinate() != null)
        {
            // call set piece method
            setPiece(move.getCaptureCoordinate(), CheckersPiece.NONE.getValue());
        }
    }


    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if the tileCoord points to a Tile that is on the Board; False otherwise.
     */
    public boolean isValidTile(Ivec2 tileCoord)
    {
        // creating a board size vector
        Ivec2 boardSize = getSize();
        // returning true if the tile is valid false if not
        return tileCoord.x >= 0 && tileCoord.x < boardSize.x && tileCoord.y >= 0 && tileCoord.y < boardSize.y;
    }


    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if there is a valid Checkers Piece at the specified TileCoord; False otherwise.
     */
    public boolean isPiece(Ivec2 tileCoord)
    {
        // return the boolean value of is piece from the checkers piece enum
        return CheckersPiece.isPiece(getPiece(tileCoord));
    }

    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if the Piece at the specified tileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean isP1(Ivec2 tileCoord)
    {
        // return the boolean is p1 using checkers piece enum
        return CheckersPiece.isP1(getPiece(tileCoord));
    }

    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if the Piece at the specified TileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean isP2(Ivec2 tileCoord)
    {
        // return the is p2 boolean using the same enum as above
        return CheckersPiece.isP2(getPiece(tileCoord));
    }


    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if the Piece at the specified TileCoord is a King Piece; False otherwise.
     */
    public boolean isKing(Ivec2 tileCoord)
    {
        // again return boolean for is king using enum
        return CheckersPiece.isKing(getPiece(tileCoord));
    }

    /**
     * @param tileCoord the tile on the board we want to check
     * @return True if the Piece at the specified tileCoord is a Pawn Piece; False otherwise.
     */
    public boolean isPawn(Ivec2 tileCoord)
    {
        // return is pawn from checkers piece enum
        return CheckersPiece.isPawn(getPiece(tileCoord));
    }



    /**
     * Convert a Pawn Piece at the specified Tile Coordinate into a King Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Pawn Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeKing(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        // if the tile is valid
        if (isValidTile(tileCoord))
        {
            // get the piece id
            int pieceID = getPiece(tileCoord);
            // if the piece is a pawn
            if (CheckersPiece.isPawn(pieceID))
            {
                // if the piece is p1's
                if (CheckersPiece.isP1(getPiece(tileCoord)))
                {
                    // call set piece method passing in p1 king
                    setPiece(tileCoord, CheckersPiece.P1KING.getValue());
                }
                // if its p2's
                else
                {
                    // call set piece passing in p2 king
                    setPiece(tileCoord, CheckersPiece.P2KING.getValue());
                }
            }
            // if the piece passing is already a king throw an illegal arg exception
            else throw new IllegalArgumentException("Can only make Pawn Pieces into Kings.");
        }
        // if the tile is invalid thrown an index exception
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a King Piece at the specified Tile Coordinate into a Pawn Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a King Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makePawn(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        // if the tile is valid
        if (isValidTile(tileCoord))
        {
            // get the piece
            int PieceID = getPiece(tileCoord);
            // if the piece is a king
            if (CheckersPiece.isKing(PieceID))
            {
                // if the piece is p1's
                if (CheckersPiece.isP1(getPiece(tileCoord)))
                {
                    // call set piece passing in p1 pawn value
                    setPiece(tileCoord, CheckersPiece.P1PAWN.getValue());
                }
                // if the piece is p2's
                else
                {
                    // call set piece passing in p2 pawn value
                    setPiece(tileCoord, CheckersPiece.P2PAWN.getValue());
                }
            }
            // if the piece is already a pawn throw an illegal argument exception
            else throw new IllegalArgumentException("Can only make King Pieces into Pawns.");
        }
        // if the tile is invalid throw an index exception
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a Player 2's Piece at the specified Tile Coordinate into a Player 1's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Player 2's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeP1(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        // if the tile is valid
        if (isValidTile(tileCoord))
        {
            // get the piece
            int pieceID = getPiece(tileCoord);
            // if the piece is p2
            if (CheckersPiece.isP2(pieceID))
            {
                // if the piece is a pawn
                if (CheckersPiece.isPawn(getPiece(tileCoord)))
                {
                    // set the piece passing in p1 pawn
                    setPiece(tileCoord, CheckersPiece.P1PAWN.getValue());
                }
                // if the piece is a king
                else
                {
                    // call set piece passing in p1 king
                    setPiece(tileCoord, CheckersPiece.P1KING.getValue());
                }
            }
            // if piece is already p1 throw illegal argument exception
            else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        }
        // if the tile is invalid throw index exception
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }


    /**
     * Convert a Player 1's Piece at the specified Tile Coordinate into a Player 2's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     * @param tileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Player 1's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void makeP2(Ivec2 tileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        // if the tile is valid
        if (isValidTile(tileCoord))
        {
            // get the piece
            int pieceID = getPiece(tileCoord);
            // if the piece is a p1 piece
            if (CheckersPiece.isP1(pieceID))
            {
                // is the piece is a pawn
                if (CheckersPiece.isPawn(getPiece(tileCoord)))
                {
                    // call set piece and pass in p2 pawn
                    setPiece(tileCoord, CheckersPiece.P2PAWN.getValue());
                }
                // if the piece is a king
                else
                {
                    // call set piece and pass in p2 king
                    setPiece(tileCoord, CheckersPiece.P2KING.getValue());
                }
            }
            // if the piece is already p2 throw illegal argument exception
            else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        }
        // if the tile is invalid throw index exception
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }
}