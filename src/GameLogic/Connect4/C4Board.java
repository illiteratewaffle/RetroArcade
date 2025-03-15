package GameLogic.Connect4;

public class C4Board {
    private C4Piece[][] c4Board;

    /**
     * Creates a blank board as an initial setup of the board for users
     * when they start or reset the game. Else simply creates a board of fixed lengths.
     * @param rows: number of rows in the connect-4 table.
     * @param col: number of columns in the connect-4 table.
     */
    public C4Board(int rows, int col) {
        c4Board = new C4Piece[rows][col];
        initC4BlankBoard(); // fills board with BLANK pieces
    }

    /**
     * Place a token at a specified location (coordinates) on the board.
     */
    void setC4Piece() {
    }

    /**
     * Get the coordinates of a placed token on the board.
     */
    void getC4Piece() {
    }

    /**
     * Gets the current state of the game represented by a 2-D array.
     * @return 2-D array of the board.
     */
    C4Piece[][] getC4Board() {
        return c4Board;
    }

    /**
     * Function to create a blank board [2-D array filled with blanks].
     */
    void initC4BlankBoard() {
        for (int i = 0; i < c4Board.length; i++) {
            for (int j = 0; j < c4Board[i].length; j++) {
                c4Board[i][j] = C4Piece.BLANK; // Initialize all cells as BLANK
            }
        }
    }

    /**
     * Console representation of the current board state with all the pieces.
     * @return a String version of the board.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Construct 2D board grid representation in the console
        for (int i = 0; i < c4Board.length; i++) {
            for (int j = 0; j < c4Board[i].length; j++) {
                String pieceRepresentation;

                if (c4Board[i][j] == null || c4Board[i][j] == C4Piece.BLANK) {
                    pieceRepresentation = "_";  // Empty slot
                } else if (c4Board[i][j] == C4Piece.RED) {
                    pieceRepresentation = "R";  // Red piece
                } else if (c4Board[i][j] == C4Piece.BLUE) {
                    pieceRepresentation = "B";  // Blue piece
                } else {
                    pieceRepresentation = "?";  // Fallback in case of an unknown piece type
                }

                sb.append(pieceRepresentation);
                sb.append(" "); // Space between pieces for readability
            }
            sb.append("\n"); // New line after each row
        }
        return sb.toString();
    }
}