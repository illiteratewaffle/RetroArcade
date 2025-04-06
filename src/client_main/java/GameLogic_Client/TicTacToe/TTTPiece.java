package GameLogic_Client.TicTacToe;

/**
 * Enum representing the possible pieces in a Tic-Tac-Toe game.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public enum TTTPiece {
    EMPTY(0),  // No piece placed
    X(1),      // X piece
    O(2);      // O piece

    private final int value;

    TTTPiece(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value of the piece.
     * @return 0 for EMPTY, 1 for X, 2 for O
     */
    public int getValue() {
        return value;
    }

    /**
     * Converts an integer to a TTTPiece.
     * @param value the integer value (0 = EMPTY, 1 = X, 2 = O)
     * @return corresponding TTTPiece
     */
    public static TTTPiece fromInt(int value) {
        switch (value) {
            case 1: return X;
            case 2: return O;
            default: return EMPTY;
        }
    }
}
