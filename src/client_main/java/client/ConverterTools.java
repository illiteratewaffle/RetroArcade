package client;

import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Connect4.HintResult;
import GameLogic_Client.Ivec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConverterTools {
    /**
     * Converts Ivec to List, makes it so the first coordinate is x and the second coordinate is y
     * @param ivec the Ivec2 object
     * @return
     */
    public static List<Integer> ivecToList(Ivec2 ivec) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(ivec.x);
        list.add(ivec.y);
        return list;
    }

    /**
     * Converts list to Ivec, assumes that the first coordinate is x and second coordinate is y
     * @param list the List representation of an Ivec2
     * @return Ivec2 representation of a list
     */
    public static Ivec2 listToIvec(List<Integer> list) {
        return new Ivec2(list.get(0), list.get(1));
    }

    /**
     * Converts an int array to a list
     * @param array the int array
     * @return the corresponding list
     */
    public static List<Integer> convertIntArrayToList(int[] array) {
        return Arrays.stream(array).boxed().toList();
    }

    /**
     * Converts a list to an int array
     * @param list the list
     * @return the corresponding int array
     */
    public static int[] convertListToIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Converts a list of 2d arrays to a list of a list of a list of integers
     * @param list the list of 2d int arrays
     * @return a list of a list of a list of integers
     */
    public static List<List<List<Integer>>> listOf2dArrayto3dlist(List<int[][]> list) {
        List<List<List<Integer>>> result = new ArrayList<>();

        // iterate through all 2d arrays in the list
        for (int[][] array2D : list) {
            // create a list of a list of integers for all 2d arrays
            List<List<Integer>> array2DList = new ArrayList<>();
            // iterate through all x coordinates of the 2d array
            for (int[] x : array2D) {
                // create a list of integers for every row
                List<Integer> rowList = new ArrayList<>();
                // iterate through all the integers
                for (int i : x) {
                    // add each integer to the 2nd nested list
                    rowList.add(i);
                }
                // wrap the 1d list with another layer
                array2DList.add(rowList);
            }
            // wrap the 2d list with another layer
            result.add(array2DList);
        }

        return result;
    }

    /**
     * Returns the List of a 2d array
     * @param list the input triple nested List of an Integer
     * @return the List of a 2d int array
     */
    public static List<int[][]> tripleListToListOf2dArray(List<List<List<Integer>>> list) {
        List<int[][]> result = new ArrayList<>();
        // iterate through each 2D list in the input
        for (List<List<Integer>> array2DList : list) {
            // count rows and add them to an empty 2d array
            int row = array2DList.size();
            int[][] array2D = new int[row][];
            // for each row
            for (int x = 0; x < row; x++) {
                List<Integer> rowList = array2DList.get(x);
                // count the columns
                int col = rowList.size();
                // create a new int array for each row
                array2D[x] = new int[col];
                // fill in each value in the row
                for (int y = 0; y < col; y++) {
                    array2D[x][y] = rowList.get(y);
                }
            }
            // add the 2D array to the result list
            result.add(array2D);
        }
        return result;
    }

    /**
     * Converts C4Piece to an integer for sending over json
     * @param c4Piece the input C4Piece
     * @return the integer that the C4Piece represents
     */
    public static int C4PieceToInt(C4Piece c4Piece) {
        return c4Piece.getValue();
    }

    /**
     * Converts an int to a C4Piece
     * @param value the input integer that represents the C4Piece
     * @return the corresponding C4Piece
     */
    public static C4Piece intToC4Piece(int value) {
        return C4Piece.fromInt(value);
    }

    /**
     * Converts a HintResult to a list
     * @param hintResult the input HintResult
     * @return the corresponding List
     */
    public static List<Object> hintResultToList(HintResult hintResult) {
        return List.of(hintResult);
    }

    /**
     * Converts a List to HintResult
     * @param list the input list
     * @return the corresponding HintResult
     */
    public static HintResult intToHintResult(List<Object> list) {
        return new HintResult((int) list.get(0), (String) list.get(1));
    }
}
