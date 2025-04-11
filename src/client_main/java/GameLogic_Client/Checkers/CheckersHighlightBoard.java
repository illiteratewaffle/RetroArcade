package GameLogic_Client.Checkers;

// importing the abstract board and ivec2 classes
import GameLogic_Client.AbstractBoard;
import GameLogic_Client.Ivec2;


public class CheckersHighlightBoard extends AbstractBoard
{
    /**
     * constructor for the highlight board which takes the number of rows and columns in the board as well as the board itself
     * @param rows the number of rows
     * @param cols the number of columns
     * @param board the 2d board array
     */
    public CheckersHighlightBoard(int rows, int cols, int[][] board)
    {
        // instantiate the super class
        super(rows, cols, board);
    }


    /**
     * Wrapper method for <code>setPiece</code> to set the value of a tile to a highlight value.
     * @param tileCoordinate The coordinate of the tile to highlight.
     * @param highlight The type of highlight to apply.
     */
    public void markTile(Ivec2 tileCoordinate, CheckersHighlightType highlight)
    {
        // call the set piece method passing in the cord and either 0 or the highlight value based on if highlight is null
        setPiece(tileCoordinate, (highlight == null) ? 0 : highlight.getValue());
    }
}
