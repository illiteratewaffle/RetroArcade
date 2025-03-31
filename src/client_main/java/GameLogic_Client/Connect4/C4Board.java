package GameLogic_Client.Connect4;

import GameLogic_Client.AbstractBoard;
import GameLogic_Client.ivec2;

public class C4Board extends AbstractBoard {

    /**
     * Creates a blank board as an initial setup of the board for users
     * when they start or reset the game. Else simply creates a board of fixed lengths.
     * @param rows: number of rows in the connect-4 table.
     * @param cols: number of columns in the connect-4 table.
     */
    public C4Board(int rows, int cols) {
        super(rows, cols, new int[rows][cols]);
        initBlankBoard(); // fills board with BLANK pieces
    }

    /**
     * Place a token at a specified location (coordinates) on the board.
     */
    public void setC4Piece(ivec2 point, C4Piece piece) {
        setPiece(point, piece.getValue());;
    }

    /**
     * Get the coordinates of a placed token on the board.
     */
    public C4Piece getC4Piece(ivec2 point) {
        return C4Piece.fromInt(getPiece(point));
    }

    /**
     * Gets the current state of the game represented by a 2-D array.
     * @return 2-D array of the board.
     */
    public C4Piece[][] getC4Board() {
        int[][] rawBoard = getBoard(); // from AbstractBoard
        C4Piece[][] result = new C4Piece[rawBoard.length][rawBoard[0].length];

        for (int i = 0; i < rawBoard.length; i++) {
            for (int j = 0; j < rawBoard[i].length; j++) {
                result[i][j] = C4Piece.fromInt(rawBoard[i][j]);
            }
        }

        return result;
    }

    /**
     * Function to create a blank board [2-D array filled with blanks].
     */
//    void initC4BlankBoard() {
//        for (int i = 0; i < c4Board.length; i++) {
//            for (int j = 0; j < c4Board[i].length; j++) {
//                c4Board[i][j] = GameLogic.Connect4.C4Piece.BLANK; // Initialize all cells as BLANK
//            }
//        }
//    }

    /**
     * Console representation of the current board state with all the pieces.
     * @return a String version of the board.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        C4Piece[][] board = getC4Board();

        for (C4Piece[] row : board) {
            for (C4Piece piece : row) {
                String symbol = switch (piece) {
                    case RED -> "R";
                    case BLUE -> "B";
                    case BLANK -> "_";
                };
                sb.append(symbol).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }


//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//
//        // Construct 2D board grid representation in the console
//        for (int i = 0; i < c4Board.length; i++) {
//            for (int j = 0; j < c4Board[i].length; j++) {
//                String pieceRepresentation;
//
//                if (c4Board[i][j] == null || c4Board[i][j] == GameLogic.Connect4.C4Piece.BLANK) {
//                    pieceRepresentation = "_";  // Empty slot
//                } else if (c4Board[i][j] == GameLogic.Connect4.C4Piece.RED) {
//                    pieceRepresentation = "R";  // Red piece
//                } else if (c4Board[i][j] == GameLogic.Connect4.C4Piece.BLUE) {
//                    pieceRepresentation = "B";  // Blue piece
//                } else {
//                    pieceRepresentation = "?";  // Fallback in case of an unknown piece type
//                }
//
//                sb.append(pieceRepresentation);
//                sb.append(" "); // Space between pieces for readability
//            }
//            sb.append("\n"); // New line after each row
//        }
//        return sb.toString();
//    }




}
