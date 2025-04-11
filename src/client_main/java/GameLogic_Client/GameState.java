// set package to game logic client
package GameLogic_Client;

/**
 * the states of each game ongoing (no one has won), p1 win, p2 win, and a tie. As these are all single player games we can generalize the game states to these four options
 */
public enum GameState {
    ONGOING,
    P1WIN,
    P2WIN,
    TIE
}