package GUI_client;

public class Tile {
    private char piece;
    private int row;
    private int col;

    public Tile(char piece, int row, int col) {
        this.piece = piece;
        this.row = row;
        this.col = col;
    }

    public char getPiece(){
        return this.piece;
    }
    public void setPiece(char piece){
        this.piece = piece;
    }
}
