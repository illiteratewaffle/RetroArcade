package gamelogic.Connect4;

public class C4GameLogic {

    private final C4Board c4Board;
    private int c4PiecesPlayed;
    private boolean isC4GameOver;
    private int[] C4lastPlayedPosition = new int[2];

    public C4GameLogic() {
        this.c4Board = new C4Board(6,7);
        System.out.println("A new connect four board has been created");
    }

    public boolean getC4IsGameOver() {
        return false;
    }
    public C4Piece getC4CurrentPlayer() {
        return C4Piece.BLANK;
    }
    public int getC4ColTopBlank() {
        return -1;
    }
    public C4Piece getC4Winner() {
        return C4Piece.BLANK;
    }
    public int[] getC4lastPlayedPosition() {
        return C4lastPlayedPosition;
    }
    public boolean c4DropPiece(int col, C4Piece piece) {
        return false;
    }
    public int getC4TotalPiecesPlayed() {
        return 0;
    }
    private boolean isC4ColFull(int col) {
        return false;
    }
    private void c4PlacePiece(int row, int col, C4Piece piece) {
    }
    @Override
    public String toString() {
        return c4Board.toString();
    }
}