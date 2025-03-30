package client_main.java.GameLogic_Client.Checkers;

import client_main.java.GameLogic_Client.IBoardGameController;
import client_main.java.GameLogic_Client.ivec2;

import java.util.ArrayList;
import java.util.HashMap;


public class CheckersController implements IBoardGameController
{
    // The underlying checkers board to store the position of pieces.
    private CheckersBoard Board;

    //checks if the player can interact with the board
    private boolean TurnP1 = true;

    // Stores the set of valid moves that can currently be made as a hashmap.
    // The keys contain the coordinates of pieces that can move.
    // The values are the sets of moves for the respective pieces.
    private HashMap<ivec2, HashMap<ivec2, CheckersMove>> ValidInputs;

    // Internal flag to check if we should force the Player to capture a piece
    // by only considering moves that would result in a capture.
    private boolean MustCapture = false;

    // Internal flag to check if the current player has moved this turn.
    // If they have, their turn will end
    // as soon as they can no longer anything with the currently selected piece,
    // or if the current piece is made into a king.
    // This also allows us to restrict valid moves to those of the currently selected piece.
    private boolean HasMovedThisTurn = false;

    // Internal value to keep track of the location on the board for the currently selected piece.
    // If no pieces have been selected this turn, this value is null.
    private ivec2 CurrentPieceLocation = null;

    private GameState CurrentGameState = GameState.ONGOING;



    // Helper methods.
    private void AddPieceMoves(ivec2 PieceLocation)
    {
        if (Board.IsPiece(PieceLocation))
        {
            // Hashset to temporarily store the valid moves of this piece.
            HashMap<ivec2, CheckersMove> PieceValidMoves = new HashMap<>();

            boolean ShouldCheckTopLeft = false;
            boolean ShouldCheckTopRight = false;
            boolean ShouldCheckBottomLeft = false;
            boolean ShouldCheckBottomRight = false;
            // King pieces can move in all 4 directions.
            if (Board.IsKing(PieceLocation))
            {
                ShouldCheckTopLeft = true;
                ShouldCheckTopRight = true;
                ShouldCheckBottomLeft = true;
                ShouldCheckBottomRight = true;
            }
            // Player 1 pawns can only move up.
            else if (Board.IsP1(PieceLocation))
            {
                ShouldCheckTopLeft = true;
                ShouldCheckTopRight = true;
            }
            // Player 2 pawns can only move down.
            else
            {
                ShouldCheckBottomLeft = true;
                ShouldCheckBottomRight = true;
            }

            if (ShouldCheckTopLeft)
            {
                AddMoveInDirection(PieceLocation, new ivec2(-1, 1), PieceValidMoves);
            }
            if (ShouldCheckTopRight)
            {
                AddMoveInDirection(PieceLocation, new ivec2(1, 1), PieceValidMoves);
            }
            if (ShouldCheckBottomLeft)
            {
                AddMoveInDirection(PieceLocation, new ivec2(-1, -1), PieceValidMoves);
            }
            if (ShouldCheckBottomRight)
            {
                AddMoveInDirection(PieceLocation, new ivec2(1, -1), PieceValidMoves);
            }

            // Only add the piece to the map of valid moves, if it can be moved.
            if (!PieceValidMoves.isEmpty())
            {
                ValidInputs.put(PieceLocation, PieceValidMoves);
            }
        }
        return;
    }


    private void AddMoveInDirection(ivec2 PieceLocation, ivec2 Direction,
                                    HashMap<ivec2, CheckersMove> PieceValidMoves)
    {
        if (MustCapture)
        {
            // Check if this move can capture a piece.
            ivec2 CaptureLocation = PieceLocation.Add(Direction);
            if (Board.IsValidTile(CaptureLocation) &&
                    (TurnP1 && Board.IsP2(CaptureLocation)) || (!TurnP1 && Board.IsP1(CaptureLocation)))
            {
                // Check if after capturing a piece, the moving piece can get to a valid empty tile.
                ivec2 TargetLocation = CaptureLocation.Add(Direction);
                if (Board.IsValidTile(TargetLocation) && !Board.IsPiece(TargetLocation))
                {
                    PieceValidMoves.put(TargetLocation,
                            new CheckersMove(PieceLocation, TargetLocation, CaptureLocation));
                }
            }
        }
        else
        {
            ivec2 TargetLocation = PieceLocation.Add(Direction);
            // Check if there is a valid tile to move to.
            if (Board.IsValidTile(TargetLocation))
            {
                // Check if this valid tile is empty.
                // If so, this is a normal move.
                if (!Board.IsPiece(TargetLocation))
                {
                    PieceValidMoves.put(TargetLocation,
                            new CheckersMove(PieceLocation, TargetLocation, null));
                }
                // If there is a piece there, check if it can be captured.
                // This involves first checking if it belongs to the other player.
                else if ((TurnP1 && Board.IsP2(TargetLocation)) || (!TurnP1 && Board.IsP1(TargetLocation)))
                {
                    // We then set the CaptureLocation to the position of the target piece,
                    // And move the TargetLocation of the move behind the piece.
                    ivec2 CaptureLocation = TargetLocation;
                    TargetLocation = TargetLocation.Add(Direction);
                    // Check to ensure that we can jump to the TargetLocation (the tile is empty and valid).
                    if (Board.IsValidTile(TargetLocation) && !Board.IsPiece(TargetLocation))
                    {
                        // This move is a capture move.
                        // All the moves before this did not result in a capture, and so should be removed.
                        ValidInputs.clear();
                        PieceValidMoves.clear();
                        MustCapture = true;

                        // Add the capture move.
                        PieceValidMoves.put(TargetLocation,
                                new CheckersMove(PieceLocation, TargetLocation, CaptureLocation));
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
        if (HasMovedThisTurn) AddPieceMoves(CurrentPieceLocation);
            // Otherwise, add the moves of all the pieces of the current player.
        else
        {
            if (TurnP1)
            {
                for (ivec2 PieceLocation : Board.getP1PieceLocations())
                {
                    AddPieceMoves(PieceLocation);
                }
            }
            else
            {
                for (ivec2 PieceLocation : Board.getP2PieceLocations())
                {
                    AddPieceMoves(PieceLocation);
                }
            }
        }
    }


    /**
     * Validate and (if valid) Perform a move that the CheckersController
     * has calculated from aggregating the Player's input.
     * @param PieceCoord The coordinate of the piece making the move.
     * @param TargetCoord The coordinate of the tile the piece will be moved to.
     * @return True if the Move Input is valid; False otherwise.
     */
    private boolean ProcessMoveInput(ivec2 PieceCoord, ivec2 TargetCoord)
    {
        // First check if the piece at PieceCoord can be moved.
        HashMap<ivec2, CheckersMove> PieceValidMoves = ValidInputs.get(PieceCoord);
        if (PieceValidMoves != null)
        {
            // Then check if the TargetCoord is a valid position to move said piece to.
            CheckersMove Move = PieceValidMoves.get(TargetCoord);
            if (Move != null)
            {
                Board.MakeMove(Move);
                CurrentPieceLocation = TargetCoord;
                HasMovedThisTurn = true;

                // Try to king the selected piece.
                boolean HasKinged = false;
                if (Board.IsKing(CurrentPieceLocation))
                {
                    if (TurnP1)
                    {
                        if (CurrentPieceLocation.y == GetBoardSize().y - 1)
                        {
                            Board.setPiece(CurrentPieceLocation, CheckersPiece.P1KING.ordinal());
                            HasKinged = true;
                        }
                    }
                    else if (CurrentPieceLocation.y == 0)
                    {
                        Board.setPiece(CurrentPieceLocation, CheckersPiece.P2KING.ordinal());
                        HasKinged = true;
                    }
                }

                // If the selected piece has been kinged, or if it did not capture any pieces, end the turn.
                if (!MustCapture || HasKinged)
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
        MustCapture = false;
        HasMovedThisTurn = false;
        CurrentPieceLocation = null;

        // Alternate between the 2 players' turn.
        TurnP1 = !TurnP1;

        // Perform the win check here.
        // A player wins if their opponent cannot make any other moves.
        // Hence, the need for this to be here.
        // As the behaviour of UpdateValidInputs is dependent on the turn-state,
        // This method must also manage the turn-state flags.
        UpdateValidInputs();
        if (ValidInputs.isEmpty())
        {
            // We perform an additional check on the current player, too, to ensure that it is not a tie.
            TurnP1 = !TurnP1;
            UpdateValidInputs();

            // Reset the turn to the correct person.
            TurnP1 = !TurnP1;
            if (ValidInputs.isEmpty())
            {
                CurrentGameState = GameState.TIE;
            }
            else
            {
                // If there are no valid moves for P1 to make, P2 wins.
                // Otherwise, P1 wins.
                // This cannot be a tie since the above proves to us that
                // 1 and only 1 of the 2 players cannot make a move.
                CurrentGameState = TurnP1 ? GameState.P2WIN : GameState.P1WIN;
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
    protected boolean GameOngoingChanged = false;
    protected boolean WinnersChanged = false;
    protected boolean CurrentPlayerChanged = false;
    protected int BoardChanged = 0;

    public void ReceiveInput(ivec2 input)
    {
        // Reset the flags to help detect changes since the last input.
        GameOngoingChanged = false;
        WinnersChanged = false;
        CurrentPlayerChanged = false;
        BoardChanged = 0;

        // Process the input. This will contain the bulk of the code for this class.
        // If we have a piece selected, try and process the move, or select another piece.
        if (CurrentPieceLocation != null)
        {
            // Check if the piece can perform that move.
            boolean ValidMoveInput = ProcessMoveInput(CurrentPieceLocation, input);
            // If the input was not a valid move, it must be a request to reselect the current piece.
            // If the player has moved this turn, force them to use this piece until the turn ends.
            if (!ValidMoveInput && !HasMovedThisTurn)
            {
                if (ValidInputs.containsKey(input)) CurrentPieceLocation = input;
                else CurrentPieceLocation = null;
            }
        }
        // Otherwise, simply try to select another piece.
        else
        {
            if (ValidInputs.containsKey(input)) CurrentPieceLocation = input;
        }

        return;
    }


    public void RemovePlayer(int Player) throws IndexOutOfBoundsException
    {
        // We only have 2 players, denoted 0 and 1.
        if (Player >= 0 && Player <= 1)
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


    public ArrayList<int[][]> GetBoardCells(int LayerMask)
    {
        ArrayList<int[][]> BoardCells = new ArrayList<>();
        // Check the first bit of the map, which represents the piece layer.
        if ((LayerMask & 0b1) != 0)
        {
            BoardCells.add(Board.getBoard());
        }
        // Check the second bit of the map, which represents the hint layer.
        if ((LayerMask & 0b10) != 0)
        {
            // Construct a 2D int array of hints and add it to the BoardCells array list.
        }
        return BoardCells;
    }

    /**
     * @return The size of the Board.
     */
    public ivec2 GetBoardSize()
    {
        return new ivec2(0, 0);
    }

    /**
     * @return The index of the current player (the player whose turn is currently ongoing).
     */
    public int GetCurrentPlayer()
    {
        return TurnP1 ? 0 : 1;
    }


    public boolean GameOngoingChangedSinceLastCommand()
    {
        return GameOngoingChanged;
    }


    public boolean WinnersChangedSinceLastCommand()
    {
        return WinnersChanged;
    }

    public boolean CurrentPlayerChangedSinceLastCommand()
    {
        return CurrentPlayerChanged;
    }

    public int BoardChangedSinceLastCommand()
    {
        return BoardChanged;
    }
}