package GameLogic.Checkers;

import GameLogic.*;
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
                if (PieceID != 0)
                {
                    if (PieceID == CheckersPiece.P1PAWN.ordinal() || PieceID == CheckersPiece.P1KING.ordinal())
                    {
                        P1PieceLocations.add(point);
                    }
                    if (PieceID == CheckersPiece.P2PAWN.ordinal() || PieceID == CheckersPiece.P2KING.ordinal())
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

    @Override
    public void setPiece(ivec2 point, int piece)
    {
        int OldPieceID = getPiece(point);
        // If the value of the tile is changed, we may need to update the location sets.
        if (OldPieceID != piece)
        {
            // The tile previously holds a P1 piece, which is now removed.
            if (OldPieceID == CheckersPiece.P1PAWN.ordinal() || OldPieceID == CheckersPiece.P1KING.ordinal())
            {
                P1PieceLocations.remove(point);
            }
            // The tile previously holds a P2 piece, which is now removed.
            else if (OldPieceID == CheckersPiece.P2PAWN.ordinal() || OldPieceID == CheckersPiece.P2KING.ordinal())
            {
                P2PieceLocations.remove(point);
            }

            // A new P1 piece is added in its place.
            if (piece == CheckersPiece.P1PAWN.ordinal() || piece == CheckersPiece.P1KING.ordinal())
            {
                P1PieceLocations.add(point);
            }
            // A new P2 piece is added in its place.
            else if (piece == CheckersPiece.P2PAWN.ordinal() || piece == CheckersPiece.P2KING.ordinal())
            {
                P2PieceLocations.add(point);
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
}