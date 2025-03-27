package GameLogic_Client.Checkers;

public enum CheckersPiece {
    NONE,
    P1PAWN,
    P1KING,
    P2PAWN,
    P2KING;


    /**
     * @param PieceID
     * @return True if the PieceID is equal to this enum's ordinal; False otherwise.
     */
    public boolean equals(int PieceID)
    {
        return PieceID == this.ordinal();
    }

    /**
     * @param PieceID
     * @return The CheckersPiece enum value with the same ordinal as the specified PieceID.<br>
     * If no such pieces exist, null is returned instead.
     */
    public static CheckersPiece GetCheckersPiece(int PieceID)
    {
        for (CheckersPiece Piece : CheckersPiece.values())
        {
            if (Piece.ordinal() == PieceID) return Piece;
        }
        return null;
    }
}