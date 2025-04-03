package GameLogic_Client.Connect4;
import GameLogic_Client.ivec2;

public class C4WinCheckerO1 {
    public static boolean isC4Win(ivec2 lastCoordinate, C4Piece piece, C4Piece[][] board) {
        int lastX = lastCoordinate.x;
        int lastY = lastCoordinate.y;

        // Define direction vectors: {dx, dy}
        int[][] directions = {
                {1, 0},   // → Horizontal
                {0, 1},   // ↑ Vertical
                {1, 1},   // ↗ Diagonal \
                {1, -1}   // ↘ Diagonal /
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            int count = 1; // Include the last placed piece

            // Check in the positive direction
            count += countInDirection(lastX, lastY, dx, dy, piece, board);

            // Check in the negative direction
            count += countInDirection(lastX, lastY, -dx, -dy, piece, board);

            if (count >= 4) {
                return true;
            }
        }

        return false;
    }

    private static int countInDirection(int x, int y, int dx, int dy, C4Piece piece, C4Piece[][] board) {
        int count = 0;
        int rows = board.length;
        int cols = board[0].length;

        x += dx;
        y += dy;

        while (x >= 0 && x < cols && y >= 0 && y < rows && board[y][x] == piece) {
            count++;
            x += dx;
            y += dy;
        }

        return count;
    }


//    private static int[][] direction() {
//
//    }


}
