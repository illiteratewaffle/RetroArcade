package GameLogic_Client.Checkers;

import GameLogic_Client.AbstractBoard;
import GameLogic_Client.Ivec2;


public class CheckersHighlightBoard extends AbstractBoard {
    public CheckersHighlightBoard(int rows, int cols, int[][] board) {
        super(rows, cols, board);
    }


    /**
     * Wrapper method for <code>setPiece</code> to set the value of a tile to a highlight value.
     * @param tileCoordinate The coordinate of the tile to highlight.
     * @param highlight The type of highlight to apply.
     */
    public void markTile(Ivec2 tileCoordinate, CheckersHighlightType highlight) {
        setPiece(tileCoordinate, (highlight == null) ? 0 : highlight.getValue());
    }
}
