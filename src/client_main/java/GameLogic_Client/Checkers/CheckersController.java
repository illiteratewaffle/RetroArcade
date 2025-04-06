package GameLogic_Client.Checkers;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class CheckersController implements IBoardGameController
{
    // The underlying checkers board to store the position of pieces.
    private CheckersBoard board;

    //checks if the player can interact with the board
    private boolean turnP1 = true;

    // Stores the set of valid moves that can currently be made as a hashmap.
    // The keys contain the coordinates of pieces that can move.
    // The values are the sets of moves for the respective pieces.
    private HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> validInputs;

    // Internal flag to check if the current player has moved this turn.
    // If they have, their turn will end
    // as soon as they can no longer anything with the currently selected piece,
    // or if the current piece is made into a king.
    // This also allows us to restrict valid moves to those of the currently selected piece.
    private boolean hasMovedThisTurn = false;

    // Internal value to keep track of the location on the board for the currently selected piece.
    // If no pieces have been selected this turn, this value is null.
    private Ivec2 currentPieceLocation = null;

    private GameState currentGameState = GameState.ONGOING;


    // Helper methods.

    // Pre-calculated Ivec2 values for moving in common diagonal directions in checkers.
    private final static Ivec2 emptyMove = new Ivec2(0, 0);
    private final static Ivec2 topLeftMove = emptyMove.moveUp(1).moveLeft(1);
    private final static Ivec2 topRightMove = emptyMove.moveUp(1).moveRight(1);
    private final static Ivec2 bottomLeftMove = emptyMove.moveDown(1).moveLeft(1);
    private final static Ivec2 bottomRightMove = emptyMove.moveDown(1).moveRight(1);
    /**
     * @param pieceLocation
     * The location of the piece on the board.<br>
     * If the location is invalid, or if its corresponding tile does not contain a piece,
     * an empty HashMap will be returned by default.
     * @param mustCapture
     * An array of at least 1 boolean.<br>
     * The first value specifies whether all the moves by this piece must result in a capture or not.<br>
     * Note that a piece will be forced to make a capture whenever possible.<br>
     * When this case occurs, the value pointed at will be set to true for completion.<br>
     * If this array is null or empty, the caller will not detect any changes in it, and
     * it will be assumed that the moves by this piece do not need to result in a captures.
     * @return
     * A <code>HashMap</code> containing all possible moves this piece can make (with the filter considered).
     * This maps the Target Position of each move to a corresponding <code>CheckersMove</code> instance.<br>
     * The Target Position is used for mapping and validating discrete <code>Ivec2</code> user inputs,
     * while the <code>CheckersMove</code> instance stores all the data needed
     * for the <code>CheckersBoard</code> to make the move.
     */
    @NotNull
    private HashMap<Ivec2, CheckersMove> getPieceMoves(Ivec2 pieceLocation, boolean[] mustCapture)
    {
        // HashMap to store the valid moves of this piece.
        HashMap<Ivec2, CheckersMove> pieceValidMoves = new HashMap<>();

        // Edge case for when the pieceLocation does not refer to a valid tile with a piece on it.
        if (!(board.isValidTile(pieceLocation) && board.isPiece(pieceLocation))) return pieceValidMoves;

        // Edge case for when the array does not contain sufficient information.
        // This is handled by simply redirecting the MustCapture pointer to a default valid array.
        // Modifying this array will not affect the argument passed to this method.
        if (mustCapture == null || mustCapture.length < 1) mustCapture = new boolean[]{false};

        // Flags to determine which directions to check for a move in.
        boolean shouldCheckTopLeft = false;
        boolean shouldCheckTopRight = false;
        boolean shouldCheckBottomLeft = false;
        boolean shouldCheckBottomRight = false;

        //To Ryan: problem?

        // King pieces can move in all 4 directions.
        if (board.isKing(pieceLocation))
        {
            shouldCheckTopLeft = true;
            shouldCheckTopRight = true;
            shouldCheckBottomLeft = true;
            shouldCheckBottomRight = true;
        }
        // Player 1's pawns can only move up.
        else if (board.isP1(pieceLocation))
        {
            shouldCheckTopLeft = true;
            shouldCheckTopRight = true;
        }
        // Player 2's pawns can only move down.
        else
        {
            shouldCheckBottomLeft = true;
            shouldCheckBottomRight = true;
        }

        // Temporarily store the move in each direction for preprocessing.
        CheckersMove NewMove;

        // Keep track of whether the current set of moves would result in a capture.
        boolean storedMovesAreCaptures = mustCapture[0];

        if (shouldCheckTopLeft)
        {
            NewMove = getMoveInDirection(pieceLocation, topLeftMove, mustCapture);
            // Only add a move if it is valid.
            if (NewMove != null)
            {
                // Update the state of whether all the moves should result in a capture.
                // We do not need to clear the set of moves we have found so far, as it is empty.
                storedMovesAreCaptures = mustCapture[0];
                pieceValidMoves.put(NewMove.getTargetCoordinate(), NewMove);
            }
        }
        if (shouldCheckTopRight)
        {
            NewMove = getMoveInDirection(pieceLocation, topRightMove, mustCapture);
            if (NewMove != null)
            {
                // If this new move would result in a capture,
                // and all the previously added moves are not,
                // the latter should be removed.
                if (!storedMovesAreCaptures && mustCapture[0])
                {
                    pieceValidMoves.clear();
                    storedMovesAreCaptures = true;
                }
                pieceValidMoves.put(NewMove.getTargetCoordinate(), NewMove);
            }
        }
        // Same as above.
        if (shouldCheckBottomLeft)
        {
            NewMove = getMoveInDirection(pieceLocation, bottomLeftMove, mustCapture);
            if (NewMove != null)
            {
                if (!storedMovesAreCaptures && mustCapture[0])
                {
                    pieceValidMoves.clear();
                    storedMovesAreCaptures = true;
                }
                pieceValidMoves.put(NewMove.getTargetCoordinate(), NewMove);
            }
        }
        // Same as above.
        if (shouldCheckBottomRight)
        {
            NewMove = getMoveInDirection(pieceLocation, bottomRightMove, mustCapture);
            if (NewMove != null)
            {
                if (!storedMovesAreCaptures && mustCapture[0])
                {
                    pieceValidMoves.clear();
                    storedMovesAreCaptures = true;
                }
                pieceValidMoves.put(NewMove.getTargetCoordinate(), NewMove);
            }
        }

        return pieceValidMoves;
    }


    /**
     * @param pieceLocation
     * The location of the piece on the board.<br>
     * If the location is invalid, or if its corresponding tile does not contain a piece,
     * <code>null</code> will be returned instead.
     * @param MustCapture
     * An array of at least 1 <code>boolean</code>.<br>
     * The first value specifies whether the move must result in a capture or not.<br>
     * If the move is a capture, this first value will also be set to true.
     * If this array is null or empty, the caller will not detect any changes in it, and
     * it will be assumed that the returned move does not need to result in a captures.
     * @return
     * A CheckersMove instance for the specified piece, or null if no valid moves can be taken.<br>
     * A valid move must end on a valid empty tile of the board, and result in a capture if specified.
     */
    @Nullable
    private CheckersMove getMoveInDirection(Ivec2 pieceLocation, Ivec2 direction, boolean[] MustCapture)
    {
        // Edge case for when the pieceLocation does not refer to a valid tile with a piece on it.
        if (!(board.isValidTile(pieceLocation) && board.isPiece(pieceLocation))) return null;

        // Edge case for when the array does not contain sufficient information.
        // This is handled by simply redirecting the MustCapture pointer to a default valid array.
        // Modifying this array will not affect the argument passed to this method.
        if (MustCapture == null || MustCapture.length < 1) MustCapture = new boolean[]{false};

        if (MustCapture[0])
        {
            // Check if this move can capture a piece.
            Ivec2 captureLocation = pieceLocation.add(direction);
            // A piece can only be captured if its owner is different from that of the current piece.
            if (board.isValidTile(captureLocation) &&
                    ((board.isP1(pieceLocation) && board.isP2(captureLocation)) ||
                            (board.isP2(pieceLocation) && board.isP1(captureLocation))
                    )
            )
            {
                // Check if after capturing a piece, the moving piece can get to a valid empty tile.
                Ivec2 targetLocation = captureLocation.add(direction);
                if (board.isValidTile(targetLocation) && !board.isPiece(targetLocation))
                {
                    return new CheckersMove(pieceLocation, targetLocation, captureLocation);
                }
            }
        }
        else
        {
            Ivec2 targetLocation = pieceLocation.add(direction);
            // Check if there is a valid tile to move to.
            if (board.isValidTile(targetLocation))
            {
                // Check if this valid tile is empty.
                // If so, this is a normal move.
                if (!board.isPiece(targetLocation))
                {
                    // Return the normal move.
                    return new CheckersMove(pieceLocation, targetLocation, null);
                }
                // If there is a piece there, check if it can be captured.
                // This involves first checking if its owner is different from that of the current piece.
                else if ((board.isP1(pieceLocation) && board.isP2(targetLocation)) ||
                        (board.isP2(pieceLocation) && board.isP1(targetLocation))
                )
                {
                    // We then set the captureLocation to the position of the target piece,
                    // And move the targetLocation of the move behind the piece.
                    Ivec2 captureLocation = targetLocation;
                    targetLocation = targetLocation.add(direction);
                    // Check to ensure that we can jump to the targetLocation (the tile is empty and valid).
                    if (board.isValidTile(targetLocation) && !board.isPiece(targetLocation))
                    {
                        // This move is a capture move.
                        MustCapture[0] = true;

                        // Return the capture move.
                        return new CheckersMove(pieceLocation, targetLocation, captureLocation);
                    }
                }
            }
        }
        // No valid move in this direction.
        return null;
    }


// Default variables for starting a game.
    final static int defaultWidth = 8;
    final static int defaultHeight = 8;

    // Player 1's pieces begin at the bottom of the board, moving upwards.
    final static Ivec2[] defaultInitLocationsP1 = new Ivec2[]
        {
                new Ivec2(1, 7), new Ivec2(3, 7), new Ivec2(5, 7), new Ivec2(7, 7),
                new Ivec2(0, 6), new Ivec2(2, 6), new Ivec2(4, 6), new Ivec2(6, 6),
                new Ivec2(1, 5), new Ivec2(3, 5), new Ivec2(5, 5), new Ivec2(7, 5)
        };

    // Player 2's pieces begin at the top of the board, moving downwards.
    final static Ivec2[] defaultInitLocationsP2 = new Ivec2[]
        {
                new Ivec2(0, 0), new Ivec2(2, 0), new Ivec2(4, 0), new Ivec2(6, 0),
                new Ivec2(1, 1), new Ivec2(3, 1), new Ivec2(5, 1), new Ivec2(7, 1),
                new Ivec2(0, 2), new Ivec2(2, 2), new Ivec2(4, 2), new Ivec2(6, 2)
        };


    /**
     * Create a controller that simulates the logic of a default 8x8 game of checkers.
     */
    public CheckersController()
    {
        board = new CheckersBoard(defaultHeight, defaultWidth, new int[defaultWidth][defaultHeight]);
        for (Ivec2 pieceLocation : defaultInitLocationsP1)
        {
            board.setPiece(pieceLocation, CheckersPiece.P1PAWN.getValue());
        }
        for (Ivec2 pieceLocation : defaultInitLocationsP2)
        {
            board.setPiece(pieceLocation, CheckersPiece.P2PAWN.getValue());
        }
        validInputs = new HashMap<Ivec2, HashMap<Ivec2, CheckersMove>>();
        // Prepare the game controller for the next (first) turn.
        updateNextTurnGameState();
    }

    /**
     * Create a controller that simulates the logic of a game of checkers on the specified board.
     * @param board The CheckersBoard containing the initial piece layout of the game.
     */
    public CheckersController(CheckersBoard board)
    {
        this.board = board;
        validInputs = new HashMap<Ivec2, HashMap<Ivec2, CheckersMove>>();
        // Prepare the game controller for the next (first) turn.
        updateNextTurnGameState();
    }

    /**
     * prints out how the board looks at any point (continously????)
     * commented this out bc there was an error -ava
     * <br><br>
     * Reworked into a test method for showing the board
     * until I figure out how to get the GUI working on my local device. - Leo
     */
   public void printBoard()
   {
       for (int y = defaultHeight - 1; y >= 0; y--)
       {
           int x = 0;
           String Cell;
           for (; x < defaultWidth - 1; x++)
           {
               Cell = "[ " + String.valueOf(board.getPiece(new Ivec2(x, y))) + " ]";
               System.out.print(Cell);
           }
           Cell = "[ " + String.valueOf(board.getPiece(new Ivec2(x, y))) + " ]\n";
           System.out.print(Cell);
       }
   }


   public static void main(String[] args)
   {
       CheckersController test = new CheckersController();
       test.receiveInput(new Ivec2(1, 2));
       test.receiveInput(new Ivec2(1, 3));
       test.receiveInput(new Ivec2(1, 2));
       test.receiveInput(new Ivec2(2, 3));
       test.printBoard();
   }



// Internal State Methods. Used internally by the CheckersController to manage its state.
    /**
     * Update the map of ValidInputs that can be made by the current player.<br>
     * This takes into account the player in charge of the turn, and whether they have moved this turn.
     */
    private void updateValidInputs()
    {
        // Invalidate the current set of valid inputs, since we will be recalculating it.
        validInputs.clear();


        // Do not allow for further movements if the game has already ended.
        if (currentGameState == GameState.ONGOING)
        {
            // Get the current player, and add the valid moves of all of their pieces to the Valid Moves map.

            // Temporarily store the set of moves that can be made by each piece.
            HashMap<Ivec2, CheckersMove> currentPieceMoves;

            // If the Player has moved this turn, lock them to only using the piece they have selected.
            if (hasMovedThisTurn)
            {
                // Successive moves after the first one in a turn must always result in a capture.
                currentPieceMoves = getPieceMoves(currentPieceLocation, new boolean[]{true});
                // Only add the piece if it can be moved.
                if (!currentPieceMoves.isEmpty())
                {
                    validInputs.put(currentPieceLocation, currentPieceMoves);
                }
            }
            // Otherwise, add the moves of all the pieces of the current player.
            else
            {
                // Initially, the first move in a turn does not need to result in a capture.
                boolean[] MustCapture = new boolean[]{false};

                // Keep track of whether the current set of moves would result in a capture.
                boolean storedMovesAreCaptures = false;

                // Get the set of pieces that can be moved this turn.
                HashSet<Ivec2> PieceLocations = (turnP1 ?
                        board.getP1PieceLocations() : board.getP2PieceLocations());

                // Add all of their possible moves.
                for (Ivec2 PieceLocation : PieceLocations)
                {
                    currentPieceMoves = getPieceMoves(PieceLocation, MustCapture);
                    // Only add the piece if it can be moved.
                    if (!currentPieceMoves.isEmpty())
                    {
                        // If this piece is forced to capture other pieces
                        // (that is, the current player can capture a piece in general),
                        // all the moves that do not result in a capture must be invalidated.
                        if (!storedMovesAreCaptures && MustCapture[0])
                        {
                            validInputs.clear();
                            storedMovesAreCaptures = true;
                        }
                        validInputs.put(PieceLocation, currentPieceMoves);
                    }
                }
            }
        }
    }


    /**
     * Helper method to validate, and perform a move represented by the aggregation of the Player's input.
     * This input aggregation is done by the <code>receiveInput</code> method, which will call this helper.
     * @param pieceLocation The coordinate of the piece making the move.
     * @param targetLocation The coordinate of the tile the piece will be moved to.
     * @return <code>True</code> if the Move Input is valid; <code>False</code> otherwise.
     */
    private boolean processMoveInput(Ivec2 pieceLocation, Ivec2 targetLocation)
    {
        // First check if the piece at PieceLocation can be moved.
        HashMap<Ivec2, CheckersMove> pieceValidMoves = validInputs.get(pieceLocation);
        if (pieceValidMoves != null)
        {
            // Then check if the TargetLocation is a position said piece can make a move to.
            CheckersMove move = pieceValidMoves.get(targetLocation);
            if (move != null)
            {
                board.makeMove(move);

                // After moving, the location of the current piece will be changed to the targetLocation.
                currentPieceLocation = targetLocation;

                // A move has been made this turn.
                hasMovedThisTurn = true;

                // Try to king the selected piece.
                boolean hasKinged = false;

                // Can only make a pawn piece into a king piece.
                // Additionally, this piece must be at either the bottom or top end of the board.
                // If it belongs to player 1, it must be at the top end.
                // If it belongs to player 2, it must be at the bottom end.
                if (board.isPawn(currentPieceLocation) &&
                        currentPieceLocation.y == (turnP1 ? 0 : getBoardSize().y - 1))
                {
                    board.makeKing(currentPieceLocation);
                    hasKinged = true;
                }

                // If the selected piece has been made into a king,
                // or if the move made did not result in a capture, end the turn.
                if (!move.isCapture() || hasKinged)
                {
                    endTurn();
                }
                // Otherwise, check if we can capture another piece.
                else
                {
                    updateValidInputs();
                    // End the turn if no further moves can be made.
                    if (validInputs.isEmpty())
                    {
                        endTurn();
                    }
                }

                // If a move has been made, then the locations of the pieces would have been changed.
                // Additionally, if the locations of the pieces are changed, so must the hint highlights.
                // We set the bit flags for both the piece and hint layers.
                boardChanged |= 0b11;
                return true;
            }
        }
        // If the execution falls through to here, the move input is invalid.
        return false;
    }


    /**
     * Update the game state for the next turn.<br>
     * This includes recalculating the set of valid inputs, and checking if there are any winners.<br>
     * This method should only be called after all turn-state flags have been refreshed.
     */
    private void updateNextTurnGameState()
    {
        // A player wins if their opponent cannot make any other moves during the latter's turn.
        // As the behaviour of updateValidInputs is dependent on the turn-state flags,
        // these flags must be refreshed prior to calling this method to prevent faulty behaviours.
        updateValidInputs();
        if (validInputs.isEmpty())
        {
            // We perform an additional check on the other player, too, to ensure that it is not a tie.
            turnP1 = !turnP1;
            updateValidInputs();

            // Reset the turn to the correct person.
            turnP1 = !turnP1;
            if (validInputs.isEmpty())
            {
                currentGameState = GameState.TIE;
            }
            else
            {
                // If there are no valid moves for P1 to make, P2 wins.
                // Otherwise, P1 wins.
                // This cannot be a tie since the above proves to us that
                // 1 and only 1 of the 2 players cannot make a move.
                currentGameState = turnP1 ? GameState.P2WIN : GameState.P1WIN;
            }

            // In either case, when this block is entered,
            // the game should be ending, with at least 1 winner determined.
            winnersChanged = true;
            gameOngoingChanged = true;
            updateValidInputs();
        }
    }


    /**
     * Signal the end of the turn.
     * Resets the turn-state flags.
     * Alternates the current player between P1 and P2.
     * Provides a win-condition check, which requires updating the ValidInputs map.
     */
    private void endTurn()
    {
        // Clear the turn-state flags for this turn.
        hasMovedThisTurn = false;
        currentPieceLocation = null;

        // Alternate between the 2 players' turn.
        turnP1 = !turnP1;

        // We have swapped to the other player's turn.
        currentPlayerChanged = true;

        // Update the game state for the next turn.
        updateNextTurnGameState();
    }


    /**
     * Helper method to generate a 2D integer array of hint highlights for the <code>CheckersBoard</code>.
     * @return A 2D integer array where each cell contains the value of a highlight type
     * for the corresponding cell in the <code>CheckersBoard</code>.
     */
    private int[][] getBoardHighlight()
    {
        // Create a blank Board to highlight using the Ivec2 coordinates that we have.
        Ivec2 boardSize = getBoardSize();
        CheckersHighlightBoard highlightBoard = new CheckersHighlightBoard(
                boardSize.y, boardSize.x, new int[boardSize.x][boardSize.y]);

        // Only perform highlighting if the game is still ongoing.
        if (currentGameState == GameState.ONGOING)
        {
            // Highlight all the pieces that can be selected.
            for (Ivec2 validPieces : validInputs.keySet())
            {
                highlightBoard.markTile(validPieces, CheckersHighlightType.PIECE);
            }

            if (currentPieceLocation != null)
            {
                // The CheckersMove instances contain all information on the valid moves for the selected piece.
                // We only need to add the captures that it can make and the locations that it can move to.
                for (CheckersMove validMove : validInputs.get(currentPieceLocation).values())
                {
                    if (validMove.isCapture())
                    {
                        highlightBoard.markTile(validMove.getCaptureCoordinate(), CheckersHighlightType.CAPTURE);
                    }
                    highlightBoard.markTile(validMove.getTargetCoordinate(), CheckersHighlightType.TILE);
                }
                // Additionally mark the location of the selected piece.
                highlightBoard.markTile(currentPieceLocation, CheckersHighlightType.SELECTED_PIECE);
            }
        }

        return highlightBoard.getBoard();
    }


// Interface implementation.
    protected boolean gameOngoingChanged = false;
    protected boolean winnersChanged = false;
    protected boolean currentPlayerChanged = false;
    protected int boardChanged = 0;

    public void receiveInput(Ivec2 input)
    {
        // Reset the flags to help detect changes since the last input.
        gameOngoingChanged = false;
        winnersChanged = false;
        currentPlayerChanged = false;
        boardChanged = 0;

        // Process the input. This will contain the bulk of the code for this class.
        // If we have a piece selected, try and process the move, or select another piece.
        if (currentPieceLocation != null)
        {
            // Check if the piece can perform that move.
            boolean validMoveInput = processMoveInput(currentPieceLocation, input);
            // If the input was not a valid move, it must be a request to reselect the current piece.
            // If the player has moved this turn, force them to use this piece until the turn ends.
            if (!validMoveInput && !hasMovedThisTurn)
            {
                // Select a different piece.
                if (!input.equals(currentPieceLocation) && validInputs.containsKey(input))
                {
                    currentPieceLocation = input;
                }
                // Selecting the same piece, or an invalid tile is treated as deselecting the current piece.
                else currentPieceLocation = null;

                // In either case, the selected piece would have been changed.
                // This should lead to a change the hint highlights,
                // which specifies the possible inputs, including the tiles the current piece can move to.
                boardChanged |= 0b10;
            }
        }
        // Otherwise, simply try to select another piece.
        else
        {

            if (validInputs.containsKey(input))
            {
                currentPieceLocation = input;
                // Changing the selected piece (from null to something) should change the hint highlights,
                // which specifies the possible inputs, including the tiles the current piece can move to.
                boardChanged |= 0b10;
            }
        }

        return;
    }


    public void removePlayer(int player) throws IndexOutOfBoundsException
    {
        // Reset the flags to help detect changes since the last input.
        gameOngoingChanged = false;
        winnersChanged = false;
        currentPlayerChanged = false;
        boardChanged = 0;

        // We only have 2 players, denoted 0 and 1.
        if (player >= 0 && player <= 1)
        {
            // Declare the other player the winner.
            currentGameState = (player == 0) ? GameState.P2WIN : GameState.P1WIN;

            // A winner should be decided as soon as this occurs.
            winnersChanged = true;
        }
        else throw new IndexOutOfBoundsException("Players in Checkers are denoted as either 0 or 1 only.");
    }


    public int[] getWinner()
    {
        return switch (currentGameState)
        {
            case P1WIN -> new int[]{0};
            case P2WIN -> new int[]{1};
            case TIE -> new int[]{0, 1};
            // By default, declare nobody as the winner.
            default -> new int[]{};
        };
    }


    public boolean getGameOngoing()
    {
        return currentGameState == GameState.ONGOING;
    }


    public ArrayList<int[][]> getBoardCells(int layerMask)
    {
        ArrayList<int[][]> boardCells = new ArrayList<>();
        // Check the first bit of the mask, which represents the piece layer.
        if ((layerMask & 0b1) != 0)
        {
            boardCells.add(board.getBoard());
        }
        // Check the second bit of the mask, which represents the hint layer.
        if ((layerMask & 0b10) != 0)
        {
            // Construct a 2D int array of hints and add it to the boardCells array list.
            boardCells.add(getBoardHighlight());
        }
        return boardCells;
    }


    public Ivec2 getBoardSize()
    {
        return board.getSize();
    }


    public int getCurrentPlayer()
    {
        return turnP1 ? 0 : 1;
    }


    public boolean gameOngoingChangedSinceLastCommand()
    {
        return gameOngoingChanged;
    }


    public boolean winnersChangedSinceLastCommand()
    {
        return winnersChanged;
    }

    public boolean currentPlayerChangedSinceLastCommand()
    {
        return currentPlayerChanged;
    }

    public int boardChangedSinceLastCommand()
    {
        return boardChanged;
    }
}