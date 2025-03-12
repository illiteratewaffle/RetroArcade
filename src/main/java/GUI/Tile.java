package GUI;

public class Tile{
    private char piece;
    private int[] cords;

    public Tile(char piece, int row, int col) {
        this.piece = piece;
        this.cords = cords;

    }

    public void setPiece(char piece){
        this.piece = piece;
    }

    public char getPiece() {
        return piece;
    }

    public int[] getCords() {
        return cords;
    }

    public void setCords(int[] cords) {
        this.cords = cords;
    }
}
