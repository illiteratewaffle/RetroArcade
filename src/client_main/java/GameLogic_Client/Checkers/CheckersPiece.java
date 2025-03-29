package client_main.java.GameLogic_Client.Checkers;

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


    /**
     * @param PieceID
     * @return True if the PieceID is that of a Checkers Piece; False otherwise.
     */
    public static boolean IsPiece(int PieceID)
    {
        // Additionally check to ensure that the PieceID is not invalid.
        CheckersPiece Piece = CheckersPiece.GetCheckersPiece(PieceID);
        return Piece != null && Piece != CheckersPiece.NONE;
    }

    /**
     * @param PieceID
     * @return True if the PieceID is that of a Player 1 Checkers Piece; False otherwise.
     */
    public static boolean IsP1(int PieceID)
    {
        return CheckersPiece.P1PAWN.equals(PieceID) || CheckersPiece.P1KING.equals(PieceID);
    }

    /**
     * @param PieceID
     * @return True if the PieceID is that of a Player 2 Checkers Piece; False otherwise.
     */
    public static boolean IsP2(int PieceID)
    {
        return CheckersPiece.P2PAWN.equals(PieceID) || CheckersPiece.P2KING.equals(PieceID);
    }

    /**
     * @param PieceID
     * @return True if the PieceID is that of a King Piece; False otherwise.
     */
    public static boolean IsKing(int PieceID)
    {
        return CheckersPiece.P1KING.equals(PieceID) || CheckersPiece.P2KING.equals(PieceID);
    }

    /**
     * @param PieceID
     * @return True if the PieceID is that of a Pawn Piece; False otherwise.
     */
    public static boolean IsPawn(int PieceID)
    {
        return CheckersPiece.P1PAWN.equals(PieceID) || CheckersPiece.P2PAWN.equals(PieceID);
    }
}