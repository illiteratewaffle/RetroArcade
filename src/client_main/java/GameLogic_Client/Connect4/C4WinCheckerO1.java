package GameLogic_Client.Connect4;
import GameLogic_Client.ivec2;

public class C4WinCheckerO1 {
    public static boolean isC4Win(ivec2 lastCoordinate, C4Piece piece, C4Piece[][] board) {
        return checkHorizontal(lastCoordinate, piece, board) ||
            checkVertical(lastCoordinate, piece, board) ||
            checkForwardSlash(lastCoordinate, piece, board) ||
            checkBackSlash(lastCoordinate, piece, board);
    }

    private static boolean checkHorizontal(ivec2 last, C4Piece piece, C4Piece[][] board) {
        int count = 1;

        // Check left
        for (int x = last.x - 1; x >= 0 && board[last.y][x] == piece; x--) count++;

        // Check right
        for (int x = last.x + 1; x < board[0].length && board[last.y][x] == piece; x++) count++;

        return count >= 4;
    }

    private static boolean checkVertical(ivec2 last, C4Piece piece, C4Piece[][] board) {
        int count = 1;

        // Check down only (it is impossible to have a piece above last played piece
        for (int y = last.y + 1; y < board.length && board[y][last.x] == piece; y++) count++;

        return count >= 4;
    }

    private static boolean checkForwardSlash(ivec2 last, C4Piece piece, C4Piece[][] board) {
        int count = 1;

        // Check top right
        for (int x = last.x + 1, y = last.y - 1;
             x < board[0].length && y >= 0 && board[y][x] == piece;
             x++, y--) {
            count++;
        }

        // Check bottom left
        for (int x = last.x - 1, y = last.y + 1;
             x >= 0 && y < board.length && board[y][x] == piece;
             x--, y++) {
            count++;
        }

        return count >= 4;
    }

    private static boolean checkBackSlash(ivec2 last, C4Piece piece, C4Piece[][] board) {
        int count = 1;

        // Check top left
        for (int x = last.x - 1, y = last.y - 1;
             x >= 0 && y >= 0 && board[y][x] == piece;
             x--, y--) {
            count++;
        }

        // Check bottom right
        for (int x = last.x + 1, y = last.y + 1;
             x < board[0].length && y < board.length && board[y][x] == piece;
             x++, y++) {
            count++;
        }

        return count >= 4;
    }

}
