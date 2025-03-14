package gamelogic.Connect4;

public class C4Board {
    private C4Piece[][] c4Board;

    public C4Board(int rows, int col) {
        c4Board = new C4Piece[rows][col];
        initC4BlankBoard(); // fills board with BLANK pieces
    }

    void setC4Piece() {
    }
    void getC4Piece() {
    }
    C4Piece[][] getC4Board() {
        return c4Board;
    }
    void initC4BlankBoard() {
        for (int i = 0; i < c4Board.length; i++) {
            for (int j = 0; j < c4Board[i].length; j++) {
                c4Board[i][j] = C4Piece.BLANK; // Initialize all cells as BLANK
            }
        }
    }

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