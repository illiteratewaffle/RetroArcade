package GameLogic_Client.Checkers;

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
                if (IsPiece(PieceID))
                {
                    if (IsP1(PieceID))
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
            if (IsPiece(OldPieceID))
            {
                // The tile previously holds a P1 piece, which is now removed.
                if (IsP1(OldPieceID))
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
            if (IsPiece(piece))
            {
                // A new P1 piece is added in its place.
                if (IsP1(piece))
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
        return;
    }


    /**
     * @param PieceID
     * @return True if the PieceID is that of a Checkers Piece; False otherwise.
     */
    private boolean IsPiece(int PieceID)
    {
        // Additionally check to ensure that the PieceID is not invalid.
        CheckersPiece Piece = CheckersPiece.GetCheckersPiece(PieceID);
        return Piece != null && Piece != CheckersPiece.NONE;
    }

    /**
     * @param PieceID
     * @return True if the PieceID is that of a Player 1 Checkers Piece; False otherwise.
     */
    private boolean IsP1(int PieceID)
    {
        return CheckersPiece.P1PAWN.equals(PieceID) || CheckersPiece.P1KING.equals(PieceID);
    }
}