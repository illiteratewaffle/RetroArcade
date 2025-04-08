package server.GameLogic_Client.Connect4;/*package client_main.java.GameLogic_Client.Connect4;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class C4GL implements Initializable {

    private C4GameLogic gameLogic;

    // FXML Injected components for the UI
    @FXML private GridPane gridPane;
    @FXML private Text messageText;

    @FXML private Button col0Button, col1Button, col2Button, col3Button, col4Button, col5Button, col6Button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new C4GameLogic();
        messageText.setText("Player 1's turn");

        // Setup button actions for columns
        col0Button.setOnAction(e -> onColClick(0));
        col1Button.setOnAction(e -> onColClick(1));
        col2Button.setOnAction(e -> onColClick(2));
        col3Button.setOnAction(e -> onColClick(3));
        col4Button.setOnAction(e -> onColClick(4));
        col5Button.setOnAction(e -> onColClick(5));
        col6Button.setOnAction(e -> onColClick(6));
    }

    // Handle the button click for each column
    private void onColClick(int colIndex) {
        // Get the current player
        C4Piece currentPlayer = gameLogic.getC4CurrentPlayer();

        // Drop the piece
        if (gameLogic.c4DropPiece(colIndex, currentPlayer)) {
            // After a successful move, update the board
            updateBoard(colIndex);

            // Check if the game is over
            if (gameLogic.getC4IsGameOver()) {
                messageText.setText("Game Over! " + (currentPlayer == C4Piece.RED ? "Player 1" : "Player 2") + " wins!");
                disableButtons();
            } else {
                // Update the message with the next player's turn
                messageText.setText((currentPlayer == C4Piece.RED ? "Player 2" : "Player 1") + "'s turn");
            }
        }
    }

    // Method to update the board UI (in response to game logic)
    private void updateBoard(int colIndex) {
        C4Piece[][] board = gameLogic.getC4Board().getC4Board();

        // Get the row where the piece landed
        int[] lastPosition = gameLogic.getC4lastPlayedPosition();
        int row = lastPosition[0];
        int col = lastPosition[1];

        if (row >= 0) {
            Circle piece = new Circle(20);
            piece.setFill(currentPlayerToColor(gameLogic.getC4CurrentPlayer()));
            gridPane.add(piece, col, 5 - row); // Add to the grid at the correct row and column (flip row index)
        }
    }

    private Color currentPlayerToColor(C4Piece piece) {
        switch (piece) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.YELLOW;
            default:
                return Color.TRANSPARENT;
        }
    }

    // Disable all buttons when the game ends
    private void disableButtons() {
        col0Button.setDisable(true);
        col1Button.setDisable(true);
        col2Button.setDisable(true);
        col3Button.setDisable(true);
        col4Button.setDisable(true);
        col5Button.setDisable(true);
        col6Button.setDisable(true);
    }
}
*/