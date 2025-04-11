package GameLogic_Client.Checkers;

/**
 * enum for the declaration of different piece types 0 for none, 1 for p1 pawn, 2 for p2 pawn, and so on
 */
public enum CheckersPiece {
    // enum values
    NONE(0),
    P1PAWN(1),
    P1KING(2),
    P2PAWN(3),
    P2KING(4);


    // The internal value of this enum.
    private final int value;


    /**
     * Generate a CheckersPiece enum with the given value.
     * @param value The value for the generated enum. No 2 enums should have the same value.
     */
    CheckersPiece(int value)
    {
        this.value = value;
    }


    /**
     * @return The integer value of this enum.
     */
    public int getValue()
    {
        return value;
    }




    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the pieceID is equal to this enum's value; False otherwise.
     */
    public boolean equals(int pieceID)
    {
        return pieceID == value;
    }

    /**
     * @param pieceID The integer ID of a piece.
     * @return The CheckersPiece enum with the same value as the specified PieceID.<br>
     * If no such pieces exist, null is returned instead.
     */
    public static CheckersPiece getCheckersPiece(int pieceID)
    {
        for (CheckersPiece piece : CheckersPiece.values())
        {
            if (piece.equals(pieceID)) return piece;
        }
        return null;
    }


    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the PieceID is that of a Checkers Piece; False otherwise.
     */
    public static boolean isPiece(int pieceID)
    {
        // Additionally check to ensure that the PieceID is not invalid.
        CheckersPiece Piece = CheckersPiece.getCheckersPiece(pieceID);
        return Piece != null && Piece != CheckersPiece.NONE;
    }

    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the PieceID is that of a Player 1 Checkers Piece; False otherwise.
     */
    public static boolean isP1(int pieceID)
    {
        return CheckersPiece.P1PAWN.equals(pieceID) || CheckersPiece.P1KING.equals(pieceID);
    }

    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the PieceID is that of a Player 2 Checkers Piece; False otherwise.
     */
    public static boolean isP2(int pieceID)
    {
        return CheckersPiece.P2PAWN.equals(pieceID) || CheckersPiece.P2KING.equals(pieceID);
    }

    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the PieceID is that of a King Piece; False otherwise.
     */
    public static boolean isKing(int pieceID)
    {
        return CheckersPiece.P1KING.equals(pieceID) || CheckersPiece.P2KING.equals(pieceID);
    }

    /**
     * @param pieceID The integer ID of a piece.
     * @return True if the PieceID is that of a Pawn Piece; False otherwise.
     */
    public static boolean isPawn(int pieceID)
    {
        return CheckersPiece.P1PAWN.equals(pieceID) || CheckersPiece.P2PAWN.equals(pieceID);
    }
}