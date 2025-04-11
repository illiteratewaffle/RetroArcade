package GameLogic_Client.Connect4;

import GameLogic_Client.AbstractBoard;
import GameLogic_Client.Ivec2;

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
        System.out.println("A new Connect-Four board has been created");
    }


    /**
     * Place a token at a specified location (coordinates) on the board
     * by getting the enum value of the piece.
     * @param point coordinate of piece
     * @param piece RED/BLUE/BLANK
     */
    public void setC4Piece(Ivec2 point, C4Piece piece) {
        setPiece(point, piece.getValue());;
    }


    /**
     * Get the coordinates of a placed token on the board.
     * @param point coordinates of piece
     * @return piece RED/BLUE/BLANK
     */
    public C4Piece getC4Piece(Ivec2 point) {
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

}
