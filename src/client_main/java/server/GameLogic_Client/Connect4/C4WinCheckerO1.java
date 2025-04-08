package server.GameLogic_Client.Connect4;
import GameLogic_Client.Connect4.C4GameLogic;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Ivec2;

public class C4WinCheckerO1 {
    public static boolean isC4Win(Ivec2 lastCoordinate, GameLogic_Client.Connect4.C4Piece piece, GameLogic_Client.Connect4.C4Piece[][] board) {
        GameLogic_Client.Connect4.C4GameLogic game = new C4GameLogic();
        game.updateGameState();
        return checkHorizontal(lastCoordinate, piece, board) ||
            checkVertical(lastCoordinate, piece, board) ||
            checkForwardSlash(lastCoordinate, piece, board) ||
            checkBackSlash(lastCoordinate, piece, board);
    }

    public static boolean checkHorizontal(Ivec2 last, GameLogic_Client.Connect4.C4Piece piece, GameLogic_Client.Connect4.C4Piece[][] board) {
        int count = 1;

        // Check left
        for (int x = last.x - 1; x >= 0 && board[last.y][x] == piece; x--) count++;

        // Check right
        for (int x = last.x + 1; x < board[0].length && board[last.y][x] == piece; x++) count++;

        return count >= 4;
    }

    private static boolean checkVertical(Ivec2 last, GameLogic_Client.Connect4.C4Piece piece, GameLogic_Client.Connect4.C4Piece[][] board) {
        int count = 1;

        // Check down only (it is impossible to have a piece above last played piece
        for (int y = last.y + 1; y < board.length && board[y][last.x] == piece; y++) count++;

        return count >= 4;
    }

    private static boolean checkForwardSlash(Ivec2 last, GameLogic_Client.Connect4.C4Piece piece, GameLogic_Client.Connect4.C4Piece[][] board) {
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

    private static boolean checkBackSlash(Ivec2 last, GameLogic_Client.Connect4.C4Piece piece, C4Piece[][] board) {
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
