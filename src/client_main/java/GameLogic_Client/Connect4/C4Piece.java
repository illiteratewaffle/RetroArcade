package client_main.java.GameLogic_Client.Connect4;

/**
 * Enum representing the pieces in Connect-4: Player 1's piece, Player 2's piece, and a blank for blank spots.
 */
public enum C4Piece {
    RED(1), BLUE(2), BLANK(0);

    private final int value;

    C4Piece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static C4Piece fromInt(int val) {
        for (C4Piece piece : C4Piece.values()) {
            if (piece.value == val) return piece;
        }
        return BLANK; // default if unknown
    }
}