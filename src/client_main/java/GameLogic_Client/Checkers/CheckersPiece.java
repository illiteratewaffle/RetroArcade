package client_main.java.GameLogic_Client.Checkers;

public enum CheckersPiece {
    NONE,
    P1PAWN,
    P1KING,
    P2PAWN,
    P2KING;


    /**
     * @param pieceID
     * @return True if the pieceID is equal to this enum's ordinal; False otherwise.
     */
    public boolean equals(int pieceID)
    {
        return pieceID == this.ordinal();
    }

    /**
     * @param pieceID
     * @return The CheckersPiece enum value with the same ordinal as the specified PieceID.<br>
     * If no such pieces exist, null is returned instead.
     */
    public static CheckersPiece getCheckersPiece(int pieceID)
    {
        for (CheckersPiece piece : CheckersPiece.values())
        {
            if (piece.ordinal() == pieceID) return piece;
        }
        return null;
    }


    /**
     * @param pieceID
     * @return True if the PieceID is that of a Checkers Piece; False otherwise.
     */
    public static boolean isPiece(int pieceID)
    {
        // Additionally check to ensure that the PieceID is not invalid.
        CheckersPiece Piece = CheckersPiece.getCheckersPiece(pieceID);
        return Piece != null && Piece != CheckersPiece.NONE;
    }

    /**
     * @param pieceID
     * @return True if the PieceID is that of a Player 1 Checkers Piece; False otherwise.
     */
    public static boolean isP1(int pieceID)
    {
        return CheckersPiece.P1PAWN.equals(pieceID) || CheckersPiece.P1KING.equals(pieceID);
    }

    /**
     * @param pieceID
     * @return True if the PieceID is that of a Player 2 Checkers Piece; False otherwise.
     */
    public static boolean isP2(int pieceID)
    {
        return CheckersPiece.P2PAWN.equals(pieceID) || CheckersPiece.P2KING.equals(pieceID);
    }

    /**
     * @param pieceID
     * @return True if the PieceID is that of a King Piece; False otherwise.
     */
    public static boolean isKing(int pieceID)
    {
        return CheckersPiece.P1KING.equals(pieceID) || CheckersPiece.P2KING.equals(pieceID);
    }

    /**
     * @param pieceID
     * @return True if the PieceID is that of a Pawn Piece; False otherwise.
     */
    public static boolean isPawn(int pieceID)
    {
        return CheckersPiece.P1PAWN.equals(pieceID) || CheckersPiece.P2PAWN.equals(pieceID);
    }
}