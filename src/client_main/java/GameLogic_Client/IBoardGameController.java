package GameLogic_Client;
import java.util.ArrayList;

/**
 * An Interface for the Networking Module to interact with the Game.<br><br>
 *
 * Inputs from the Clients should be passed to the GameController through ReceiveInput and RemovePlayer.<br>
 * This class will handle the inputs appropriately,
 * and record the changes occurring after the reception of said input.<br><br>
 *
 * The Networking Class using this Interface should use <code>*SinceLastCommand</> getter methods
 * to check if any changes of interests have occurred,
 * then use other Getter methods to retrieve the relevant more in-depth information.
 */
public interface IBoardGameController
{
    /**
     * Receive a 2D-Integer-Coordinate Input from the Player, and process it.
     * @param input A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */
    void ReceiveInput(ivec2 input);

    /**
     * Remove the Player of a given Index (counting from 0) from the game.
     * This will not change the index of other Players.
     * @param Player The index of the Player to remove.
     * @throws IndexOutOfBoundsException If no players with the given Index exists.
     */
    void RemovePlayer(int Player) throws IndexOutOfBoundsException;

//  Additional method added for completion's sake. Is non-applicable for this project.
//  /**
//   * Add a Player using the given InitData to the game.
//   * @param InitData The PlayerData containing the arguments for initialising the given Player.
//   * @return The Index of the newly added Player.
//   * @throws IllegalArgumentException If InitData contains invalid or missing information.
//  */
//  int AddPlayer(PlayerData InitData) throws IllegalArgumentException;


    /**
     * @return An array of integers containing the Index of the winners of the game.
     * If there are multiple winners, the game may be interpreted as a tie between said winners.
     */
    int[] GetWinner();

    /**
     * @return True if the game is still ongoing.
     */
    boolean GetGameOngoing();

    /**
     * @param LayerMask A bit-string, where the bits of all the layers to query are set to 1.
     * @return An array list of 2D integer arrays representing the cells of the board at each of the requested layer.
     */
    ArrayList<int[][]> GetBoardCells(int LayerMask);

    /**
     * @return The size of the Board.
     */
    ivec2 GetBoardSize();

    /**
     * @return The index of the current player (the player whose turn is currently ongoing).
     */
    int GetCurrentPlayer();


// Methods to check if any Changes had occurred after the use of the above Interface Methods.
// Provides a rudimentary way to check if a change had occurred as a result of an Interface Method.

    /**
     * @return True if the Game Ongoing has been changed
     * since the last call to ReceiveInput or RemovePlayer.
     */
    boolean GameOngoingChangedSinceLastCommand();

    /**
     * @return True if the List of Winners has been changed
     * since the last call to ReceiveInput or RemovePlayer.
     */
    boolean WinnersChangedSinceLastCommand();

    /**
     * @return True if the Current Player has been changed
     * since the last call to ReceiveInput or RemovePlayer.
     */
    boolean CurrentPlayerChangedSinceLastCommand();

    /**
     * @return The bit-mask for all the Layers that has been changed
     * since the last call to ReceiveInput or RemovePlayer.
     */
    int BoardChangedSinceLastCommand();
}