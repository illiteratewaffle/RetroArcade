package client_main.java.GameLogic_Client.Checkers;

import client_main.java.GameLogic_Client.IBoardGameController;
import client_main.java.GameLogic_Client.Ivec2;

import java.util.ArrayList;
import java.util.HashMap;


public class CheckersController implements IBoardGameController
{
    // The underlying checkers board to store the position of pieces.
    private CheckersBoard board;

    //checks if the player can interact with the board
    private boolean turnP1 = true;

    // Stores the set of valid moves that can currently be made as a hashmap.
    // The keys contain the coordinates of pieces that can move.
    // The values are the sets of moves for the respective pieces.
    private HashMap<Ivec2, HashMap<Ivec2, CheckersMove>> ValidInputs;

    // Internal flag to check if we should force the Player to capture a piece
    // by only considering moves that would result in a capture.
    private boolean mustCapture = false;

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
    private int width = 8;
    private int height = 8;


    // Helper methods.
    private void AddPieceMoves(Ivec2 pieceLocation)
    {
        if (board.IsPiece(pieceLocation))
        {
            // Hashset to temporarily store the valid moves of this piece.
            HashMap<Ivec2, CheckersMove> pieceValidMoves = new HashMap<>();

            boolean shouldCheckTopLeft = false;
            boolean shouldCheckTopRight = false;
            boolean shouldCheckBottomLeft = false;
            boolean shouldCheckBottomRight = false;
            // King pieces can move in all 4 directions.
            if (board.IsKing(pieceLocation))
            {
                shouldCheckTopLeft = true;
                shouldCheckTopRight = true;
                shouldCheckBottomLeft = true;
                shouldCheckBottomRight = true;
            }
            // Player 1 pawns can only move up.
            else if (board.IsP1(pieceLocation))
            {
                shouldCheckTopLeft = true;
                shouldCheckTopRight = true;
            }
            // Player 2 pawns can only move down.
            else
            {
                shouldCheckBottomLeft = true;
                shouldCheckBottomRight = true;
            }

            if (shouldCheckTopLeft)
            {
                AddMoveInDirection(pieceLocation, new Ivec2(-1, 1), pieceValidMoves);
            }
            if (shouldCheckTopRight)
            {
                AddMoveInDirection(pieceLocation, new Ivec2(1, 1), pieceValidMoves);
            }
            if (shouldCheckBottomLeft)
            {
                AddMoveInDirection(pieceLocation, new Ivec2(-1, -1), pieceValidMoves);
            }
            if (shouldCheckBottomRight)
            {
                AddMoveInDirection(pieceLocation, new Ivec2(1, -1), pieceValidMoves);
            }

            // Only add the piece to the map of valid moves, if it can be moved.
            if (!pieceValidMoves.isEmpty())
            {
                ValidInputs.put(pieceLocation, pieceValidMoves);
            }
        }
        return;
    }


    private void AddMoveInDirection(Ivec2 pieceLocation, Ivec2 direction,
                                    HashMap<Ivec2, CheckersMove> pieceValidMoves)
    {
        if (mustCapture)
        {
            // Check if this move can capture a piece.
            Ivec2 captureLocation = pieceLocation.Add(direction);
            if (board.IsValidTile(captureLocation) &&
                    (turnP1 && board.IsP2(captureLocation)) || (!turnP1 && board.IsP1(captureLocation)))
            {
                // Check if after capturing a piece, the moving piece can get to a valid empty tile.
                Ivec2 targetLocation = captureLocation.Add(direction);
                if (board.IsValidTile(targetLocation) && !board.IsPiece(targetLocation))
                {
                    pieceValidMoves.put(targetLocation,
                            new CheckersMove(pieceLocation, targetLocation, captureLocation));
                }
            }
        }
        else
        {
            Ivec2 targetLocation = pieceLocation.Add(direction);
            // Check if there is a valid tile to move to.
            if (board.IsValidTile(targetLocation))
            {
                // Check if this valid tile is empty.
                // If so, this is a normal move.
                if (!board.IsPiece(targetLocation))
                {
                    pieceValidMoves.put(targetLocation,
                            new CheckersMove(pieceLocation, targetLocation, null));
                }
                // If there is a piece there, check if it can be captured.
                // This involves first checking if it belongs to the other player.
                else if ((turnP1 && board.IsP2(targetLocation)) || (!turnP1 && board.IsP1(targetLocation)))
                {
                    // We then set the CaptureLocation to the position of the target piece,
                    // And move the targetLocation of the move behind the piece.
                    Ivec2 captureLocation = targetLocation;
                    targetLocation = targetLocation.Add(direction);
                    // Check to ensure that we can jump to the targetLocation (the tile is empty and valid).
                    if (board.IsValidTile(targetLocation) && !board.IsPiece(targetLocation))
                    {
                        // This move is a capture move.
                        // All the moves before this did not result in a capture, and so should be removed.
                        ValidInputs.clear();
                        pieceValidMoves.clear();
                        mustCapture = true;

                        // Add the capture move.
                        pieceValidMoves.put(targetLocation,
                                new CheckersMove(pieceLocation, targetLocation, captureLocation));
                    }
                }
            }
        }
    }


// Internal State Methods. Used internally by the CheckersController to manage its state.
    /**
     * Signal the official beginning of a turn.
     * DOES NOT ADJUST FLAGS NOR CALCULATE THE VALID MOVES (for reasons shown in EndTurn()).
     */
    private void StartTurn()
    {
    }


    /**
     * Update the map of ValidInputs that can be made by the current player.
     */
    private void UpdateValidInputs()
    {
        // Get the current player, and add the valid moves of all of their pieces to the Valid Moves map.

        // If the Player has moved this turn, lock them to only using the piece they have selected.
        if (hasMovedThisTurn) AddPieceMoves(currentPieceLocation);
            // Otherwise, add the moves of all the pieces of the current player.
        else
        {
            if (turnP1)
            {
                for (Ivec2 PieceLocation : board.getP1PieceLocations())
                {
                    AddPieceMoves(PieceLocation);
                }
            }
            else
            {
                for (Ivec2 PieceLocation : board.getP2PieceLocations())
                {
                    AddPieceMoves(PieceLocation);
                }
            }
        }
    }


    /**
     * Validate and (if valid) Perform a move that the CheckersController
     * has calculated from aggregating the Player's input.
     * @param pieceCoord The coordinate of the piece making the move.
     * @param targetCoord The coordinate of the tile the piece will be moved to.
     * @return True if the Move Input is valid; False otherwise.
     */
    private boolean ProcessMoveInput(Ivec2 pieceCoord, Ivec2 targetCoord)
    {
        // First check if the piece at PieceCoord can be moved.
        HashMap<Ivec2, CheckersMove> pieceValidMoves = ValidInputs.get(pieceCoord);
        if (pieceValidMoves != null)
        {
            // Then check if the TargetCoord is a valid position to move said piece to.
            CheckersMove Move = pieceValidMoves.get(targetCoord);
            if (Move != null)
            {
                board.MakeMove(Move);
                currentPieceLocation = targetCoord;
                hasMovedThisTurn = true;

                // Try to king the selected piece.
                boolean hasKinged = board.IsKing(targetCoord);
                if (board.IsKing(currentPieceLocation))
                {
                    if (turnP1)
                    {
                        if (currentPieceLocation.y == GetBoardSize().y - 1)
                        {
                            board.setPiece(currentPieceLocation, CheckersPiece.P1KING.ordinal());
                            hasKinged = true;
                        }
                    }
                    else if (currentPieceLocation.y == 0)
                    {
                        board.setPiece(currentPieceLocation, CheckersPiece.P2KING.ordinal());
                        hasKinged = true;
                    }
                }

                // If the selected piece has been kinged, or if it did not capture any pieces, end the turn.
                if (!mustCapture || hasKinged)
                {
                    EndTurn();
                }
                // Otherwise, check if we can capture another piece.
                else
                {
                    UpdateValidInputs();
                    if (ValidInputs.isEmpty())
                    {
                        EndTurn();
                    }
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Signal the end of the turn.
     * Resets the turn-state flags.
     * Alternates the current player between P1 and P2.
     * Provides a win-condition check, which requires updating the ValidInputs map.
     */
    private void EndTurn()
    {
        // Clear the turn-state flags for this turn.
        mustCapture = false;
        hasMovedThisTurn = false;
        currentPieceLocation = null;

        // Alternate between the 2 players' turn.
        turnP1 = !turnP1;

        // Perform the win check here.
        // A player wins if their opponent cannot make any other moves.
        // Hence, the need for this to be here.
        // As the behaviour of UpdateValidInputs is dependent on the turn-state,
        // This method must also manage the turn-state flags.
        UpdateValidInputs();
        if (ValidInputs.isEmpty())
        {
            // We perform an additional check on the current player, too, to ensure that it is not a tie.
            turnP1 = !turnP1;
            UpdateValidInputs();

            // Reset the turn to the correct person.
            turnP1 = !turnP1;
            if (ValidInputs.isEmpty())
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
        }

        // Other end of turn cleanup.
    }


    /**
     * prints out how the board looks at any point (continously????)
     * commented this out bc there was an error -ava
     */
   /* void printBoard(){
        System.out.println(CheckersBoard);
    }*/


// Interface implementation.
    protected boolean gameOngoingChanged = false;
    protected boolean winnersChanged = false;
    protected boolean currentPlayerChanged = false;
    protected int boardChanged = 0;

    public void ReceiveInput(Ivec2 input)
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
            boolean ValidMoveInput = ProcessMoveInput(currentPieceLocation, input);
            // If the input was not a valid move, it must be a request to reselect the current piece.
            // If the player has moved this turn, force them to use this piece until the turn ends.
            if (!ValidMoveInput && !hasMovedThisTurn)
            {
                if (ValidInputs.containsKey(input)) currentPieceLocation = input;
                else currentPieceLocation = null;
            }
        }
        // Otherwise, simply try to select another piece.
        else
        {
            if (ValidInputs.containsKey(input)) currentPieceLocation = input;
        }

        return;
    }


    public void RemovePlayer(int player) throws IndexOutOfBoundsException
    {
        // We only have 2 players, denoted 0 and 1.
        if (player >= 0 && player <= 1)
        {
            // Declare the other player the winner.
        }
        else throw new IndexOutOfBoundsException("Players in Checkers are denoted as either 0 or 1 only.");
    }


    public int[] GetWinner()
    {
        return new int[0];
    }


    public boolean GetGameOngoing()
    {
        return true;
    }


    public ArrayList<int[][]> GetBoardCells(int layerMask)
    {
        ArrayList<int[][]> boardCells = new ArrayList<>();
        // Check the first bit of the map, which represents the piece layer.
        if ((layerMask & 0b1) != 0)
        {
            boardCells.add(board.getBoard());
        }
        // Check the second bit of the map, which represents the hint layer.
        if ((layerMask & 0b10) != 0)
        {
            // Construct a 2D int array of hints and add it to the boardCells array list.
        }
        return boardCells;
    }

    /**
     * @return The size of the Board.
     */
    public Ivec2 GetBoardSize()
    {
        return new Ivec2(width, height);
    }

    /**
     * @return The index of the current player (the player whose turn is currently ongoing).
     */
    public int GetCurrentPlayer()
    {
        return turnP1 ? 0 : 1;
    }


    public boolean GameOngoingChangedSinceLastCommand()
    {
        return gameOngoingChanged;
    }


    public boolean WinnersChangedSinceLastCommand()
    {
        return winnersChanged;
    }

    public boolean CurrentPlayerChangedSinceLastCommand()
    {
        return currentPlayerChanged;
    }

    public int BoardChangedSinceLastCommand()
    {
        return boardChanged;
    }
}