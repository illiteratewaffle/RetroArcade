package client_main.java.GameLogic_Client.Checkers;

import client_main.java.GameLogic_Client.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class CheckersController implements IBoardGameController
{
    // The underlying checkers board to store the position of pieces.
    private CheckersBoard Board;

    //checks if the player can interact with the board
    private boolean TurnP1 = true;

    // Stores the set of valid moves that can currently be made as a hashmap.
    // The keys contain the coordinates of pieces that can move.
    // The values are the sets of moves for the respective pieces.
    private HashMap<ivec2, HashSet<CheckersMove>> ValidMoves;

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
        // To do: add a method to convert from an integer to a checkers piece enum value.
        int PieceID = Board.getPiece(PieceLocation);
        // Hashset to temporarily store the valid moves of this piece.
        HashSet<CheckersMove> PieceValidMoves = new HashSet<>();

        if (MustCapture)
        {
            // Add the moves that would result in a capture for this piece.
        }
        else
        {
            // Add all the valid moves of this piece.
            // Before adding each piece, check if it is a capture move and set the MustCapture flag.
            // As soon as the MustCapture flag is turned on, clear the set of Valid Moves, and restart.
        }

        // Only add the piece to the map of valid moves, if it can be moved.
        if (!PieceValidMoves.isEmpty())
        {
            ValidMoves.put(PieceLocation, PieceValidMoves);
        }
        return;
    }



// Internal State Methods. Used internally by the CheckersController to manage its state.
    /**
     * Signal the official beginning of a turn.
     * Resets the turn-relevant flags.
     * DOES NOT CALCULATE THE VALID MOVES (for reasons shown in EndTurn()).
     */
    private void StartTurn()
    {
        // Reset the flags.
        MustCapture = false;
        HasMovedThisTurn = false;
        CurrentPieceLocation = null;
    }


    /**
     * Update the map of ValidMoves that can be made by the current player.
     */
    private void UpdateValidMoves()
    {
        // Get the current player, and add the valid moves of all of their pieces to the Valid Moves map.
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


    /**
     * Validate and (if valid) Perform a move that the CheckersController
     * has calculated from aggregating the Player's input.
     * @param move The raw, unvalidated, move that has been calculated.
     */
    private void ProcessMove(CheckersMove move)
    {
        // Only make the move if it is in the set of valid moves.
        HashSet<CheckersMove> CurrentPieceValidMoves = ValidMoves.get(CurrentPieceLocation);
        if (CurrentPieceValidMoves.contains(move))
        {
            Board.MakeMove(move);
            HasMovedThisTurn = true;
        }
    }


    /**
     * Signal the end of the turn.
     * Alternates the current player between P1 and P2.
     * Provides a win-condition check, which requires updating the ValidMoves map.
     */
    private void EndTurn()
    {
        // Alternate between the 2 players' turn.
        TurnP1 = !TurnP1;

        // Perform the win check here.
        // A player wins if their opponent cannot make any other moves.
        // Hence, the need for this to be here.
        UpdateValidMoves();
        if (ValidMoves.isEmpty())
        {
            // We perform an additional check on the current player, too, to ensure that it is not a tie.
            TurnP1 = !TurnP1;
            UpdateValidMoves();

            // Reset the turn to the correct person.
            TurnP1 = !TurnP1;
            if (ValidMoves.isEmpty())
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

    public static void main(String[] args) {}



// Interface implementation.

    public void ReceiveInput(ivec2 input)
    {
        // Process the input. This will contain the bulk of the code for this class.
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
        return false;
    }


    public boolean WinnersChangedSinceLastCommand()
    {
        return false;
    }

    public boolean CurrentPlayerChangedSinceLastCommand()
    {
        return false;
    }

    public int BoardChangedSinceLastCommand()
    {
        return 0;
    }
}