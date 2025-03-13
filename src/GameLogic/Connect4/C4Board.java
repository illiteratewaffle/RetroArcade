package gamelogic.Connect4;

public class C4Board {
    private C4Piece[][] c4Board;

    public C4Board(int rows, int col) {
        c4Board = new C4Piece[rows][col];
    }

    void setC4Piece() {
    }
    void getC4Piece() {
    }
    C4Piece[][] getC4Board() {
        return new C4Piece[0][0];
    }
    void initC4BlankBoard() {
    }

    @Override
    public String toString() {
        return "";
    }
}