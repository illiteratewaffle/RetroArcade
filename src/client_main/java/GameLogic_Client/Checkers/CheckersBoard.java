package client_main.java.GameLogic_Client.Checkers;

import client_main.java.GameLogic_Client.ivec2;
import client_main.java.GameLogic_Client.AbstractBoard;

import java.util.HashSet;

// inherits from Resources.AbstractBoard.java
public class CheckersBoard extends AbstractBoard
{
    // To-do: Add comparison methods (and other basic addition operations) to ivec2.
    private HashSet<ivec2> P1PieceLocations = new HashSet<>();
    private HashSet<ivec2> P2PieceLocations = new HashSet<>();


    public CheckersBoard(int rows, int cols, int[][] board)
    {
        super(rows, cols, board);
        // Iterate through all the tiles and seek out the location of pieces of each player.
        for (int x = 0; x < cols; x++)
        {
            for (int y = 0; y < rows; y++)
            {
                ivec2 point = new ivec2(x, y);
                int PieceID = getPiece(point);
                // If the tile contains a piece, record it to the appropriate set of pieces.
                if (CheckersPiece.IsPiece(PieceID))
                {
                    if (CheckersPiece.IsP1(PieceID))
                    {
                        P1PieceLocations.add(point);
                    }
                    else
                    {
                        P2PieceLocations.add(point);
                    }
                }
            }
        }
    }


    /**
     * @return A read-only set of coordinates where P1 pieces are stored at.
     */
    public final HashSet<ivec2> getP1PieceLocations()
    {
        return P1PieceLocations;
    }

    /**
     * @return A read-only set of coordinates where P2 pieces are stored at.
     */
    public final HashSet<ivec2> getP2PieceLocations()
    {
        return P2PieceLocations;
    }

    
    public void setPiece(ivec2 point, int piece)
    {
        int OldPieceID = getPiece(point);
        // If the value of the tile is changed, we may need to update the location sets.
        if (OldPieceID != piece)
        {
            // Check if we just removed a piece from the board tile.
            if (CheckersPiece.IsPiece(OldPieceID))
            {
                // The tile previously holds a P1 piece, which is now removed.
                if (CheckersPiece.IsP1(OldPieceID))
                {
                    P1PieceLocations.remove(point);
                }
                // The tile previously holds a P2 piece, which is now removed.
                else
                {
                    P2PieceLocations.remove(point);
                }
            }

            // Check if we are adding a new piece in its place.
            if (CheckersPiece.IsPiece(piece))
            {
                // A new P1 piece is added in its place.
                if (CheckersPiece.IsP1(piece))
                {
                    P1PieceLocations.add(point);
                }
                // A new P2 piece is added in its place.
                else
                {
                    P2PieceLocations.add(point);
                }
            }
            else
            {
                // Ensure that we will not set any tiles to contain an invalid piece.
                piece = CheckersPiece.NONE.ordinal();
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
    public void MakeMove(CheckersMove move)
    {
        // Perform the relevant sets, as dictated by the move.
        // Move the piece in the start position of the move to the target position.
        setPiece(move.getTargetCord(), getPiece(move.getStartCord()));
        // This means that the tile at the start position must be cleared.
        setPiece(move.getStartCord(), CheckersPiece.NONE.ordinal());
        // If there is a capture, additionally clear the tile at the capture position.
        if (move.getCaptureCord() != null)
        {
            setPiece(move.getCaptureCord(), CheckersPiece.NONE.ordinal());
        }
        return;
    }


    /**
     * @param TileCoord
     * @return True if the TileCoord points to a Tile that is on the Board; False otherwise.
     */
    public boolean IsValidTile(ivec2 TileCoord)
    {
        ivec2 BoardSize = getSize();
        return TileCoord.x > 0 && TileCoord.x < BoardSize.x && TileCoord.y > 0 && TileCoord.y < BoardSize.y;
    }


    /**
     * @param TileCoord
     * @return True if there is a valid Checkers Piece at the specified TileCoord; False otherwise.
     */
    public boolean IsPiece(ivec2 TileCoord)
    {
        return CheckersPiece.IsPiece(getPiece(TileCoord));
    }

    /**
     * @param TileCoord
     * @return True if the Piece at the specified TileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean IsP1(ivec2 TileCoord)
    {
        return CheckersPiece.IsP1(getPiece(TileCoord));
    }

    /**
     * @param TileCoord
     * @return True if the Piece at the specified TileCoord is a Player 1 Piece; False otherwise.
     */
    public boolean IsP2(ivec2 TileCoord)
    {
        return CheckersPiece.IsP2(getPiece(TileCoord));
    }


    /**
     * @param TileCoord
     * @return True if the Piece at the specified TileCoord is a King Piece; False otherwise.
     */
    public boolean IsKing(ivec2 TileCoord)
    {
        return CheckersPiece.IsKing(getPiece(TileCoord));
    }

    /**
     * @param TileCoord
     * @return True if the Piece at the specified TileCoord is a Pawn Piece; False otherwise.
     */
    public boolean IsPawn(ivec2 TileCoord)
    {
        return CheckersPiece.IsPawn(getPiece(TileCoord));
    }



    /**
     * Convert a Pawn Piece at the specified Tile Coordinate into a King Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     * @param TileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Pawn Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void MakeKing(ivec2 TileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (IsValidTile(TileCoord))
        {
            int PieceID = getPiece(TileCoord);
            if (CheckersPiece.IsPawn(PieceID))
            {
                if (CheckersPiece.IsP1(getPiece(TileCoord)))
                {
                    setPiece(TileCoord, CheckersPiece.P1KING.ordinal());
                }
                else
                {
                    setPiece(TileCoord, CheckersPiece.P2KING.ordinal());
                }
            }
            else throw new IllegalArgumentException("Can only make Pawn Pieces into Kings.");
        }
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a King Piece at the specified Tile Coordinate into a Pawn Piece.<br>
     * The Owner of the Piece (either Player 1 or Player 2) is maintained.
     * @param TileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a King Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void MakePawn(ivec2 TileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (IsValidTile(TileCoord))
        {
            int PieceID = getPiece(TileCoord);
            if (CheckersPiece.IsKing(PieceID))
            {
                if (CheckersPiece.IsP1(getPiece(TileCoord)))
                {
                    setPiece(TileCoord, CheckersPiece.P1PAWN.ordinal());
                }
                else
                {
                    setPiece(TileCoord, CheckersPiece.P2PAWN.ordinal());
                }
            }
            else throw new IllegalArgumentException("Can only make King Pieces into Pawns.");
        }
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }

    /**
     * Convert a Player 2's Piece at the specified Tile Coordinate into a Player 1's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     * @param TileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Player 2's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void MakeP1(ivec2 TileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (IsValidTile(TileCoord))
        {
            int PieceID = getPiece(TileCoord);
            if (CheckersPiece.IsP2(PieceID))
            {
                if (CheckersPiece.IsPawn(getPiece(TileCoord)))
                {
                    setPiece(TileCoord, CheckersPiece.P1PAWN.ordinal());
                }
                else
                {
                    setPiece(TileCoord, CheckersPiece.P1KING.ordinal());
                }
            }
            else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        }
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }


    /**
     * Convert a Player 1's Piece at the specified Tile Coordinate into a Player 2's Piece.<br>
     * The State of the Piece as either a Pawn or a King is maintained.
     * @param TileCoord The coordinate of the tile to perform the conversion on.
     * @throws IllegalArgumentException If the specified tile does not contain a Player 1's Piece.
     * @throws IndexOutOfBoundsException If the tile coordinate is out of the bounds of the board.
     */
    public void MakeP2(ivec2 TileCoord) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (IsValidTile(TileCoord))
        {
            int PieceID = getPiece(TileCoord);
            if (CheckersPiece.IsP1(PieceID))
            {
                if (CheckersPiece.IsPawn(getPiece(TileCoord)))
                {
                    setPiece(TileCoord, CheckersPiece.P2PAWN.ordinal());
                }
                else
                {
                    setPiece(TileCoord, CheckersPiece.P2KING.ordinal());
                }
            }
            else throw new IllegalArgumentException("Can only make Player 2 Pieces into Player 1 Pieces.");
        }
        else throw new IndexOutOfBoundsException("Out of Bounds Tile Coordinates.");
    }
}