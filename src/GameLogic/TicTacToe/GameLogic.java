package gamelogic.tictactoe;

public class GameLogic {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public GameLogic(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }
    public void startGame() {
        currentPlayer = player1;
    }
    public void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        }
        else
            currentPlayer = player1;
    }
    public boolean checkWin() {
        return true;
    }
    public boolean checkDraw() {
        return true;
    }
}
