package management;

import GameLogic_Client.Ivec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConverterTools {
    public static List<Integer> ivecToList(Ivec2 ivec) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(ivec.x);
        list.add(ivec.y);
        return list;
    }

    /**
     * converts list to Ivec, assumes that the first coordinate is x and second coordinate is y
     *
     * @param list
     * @return Ivec2 representation of a list
     */
    public static Ivec2 listToIvec(List<Integer> list) {
        return new Ivec2(list.get(0), list.get(1));
    }

    public static List<Integer> convertIntArrayToList(int[] array) {
        return Arrays.stream(array).boxed().toList();
    }

    public static int[] convertListToIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * converts a list of 2d arrays to a list of a list of a list of integers
     *
     * @param list
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
}
