package misc;

import GameLogic_Client.Connect4.C4Piece;
import management.ConverterTools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class ConverterToolsTest {
    @Test

    /**
     * converts a list of 2d arrays to a tripple nested list of integers and back to a list of 2d arrays to test both methods
     */
    public void threeDTypeRoundTrip() {
        // Create a sample list of 2D arrays
        List<int[][]> original = new ArrayList<>();
        original.add(new int[][] {
                {1, 2, 3},
                {4, 5, 6}
        });

        original.add(new int[][] {
                {7, 8},
                {9, 10},
                {11, 12}
        });

        // convert
        List<List<List<Integer>>> testList = ConverterTools.listOf2dArrayto3dlist(original);

        // convert back
        List<int[][]> result = ConverterTools.tripleListToListOf2dArray(testList);

        for (int i = 0; i < original.size(); i++) {
            int[][] originalArray = original.get(i);
            int[][] resultArray = result.get(i);

            assertEquals(originalArray.length, resultArray.length);

            for (int row = 0; row < originalArray.length; row++) {
                assertArrayEquals(originalArray[row], resultArray[row]);
            }
        }
    }

    @Test
    /**
     * converts a 2d array of C4Piece to a nested 2d list of integers
     */
    public void twoDTypeRoundTrip() {
        C4Piece[][] original = {
                {C4Piece.RED, C4Piece.BLUE, C4Piece.BLANK},
                {C4Piece.RED, C4Piece.BLUE, C4Piece.BLANK},
        };

        // convert
        List<List<Integer>> testList = ConverterTools.c4Piece2dArrayTo2dList(original);

        // convert back
        C4Piece[][] result = ConverterTools.c4Piece2dListTo2dArray(testList);
        for (int i = 0; i < original.length; i++) {
            assertEquals(original[i].length, result[i].length);
            for (int j = 0; j < original[i].length; j++) {
                assertEquals(original[i][j], result[i][j]);
            }
        }

    }
}
