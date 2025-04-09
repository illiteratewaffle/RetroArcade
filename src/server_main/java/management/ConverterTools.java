package management;

import GameLogic_Client.Ivec2;

import java.util.Arrays;
import java.util.List;

public class ConverterTools {
    public static List<Integer> ivecToList(Ivec2 ivec) {
        return null;
    }

    public static Ivec2 listToIvec2(List<Integer> list) {
        return null;
    }

    public static List<Integer> convertIntArrayToList(int[] array) {
        return Arrays.stream(array).boxed().toList();
    }

    public static int[] convertListToIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    // this is fucking disgusting but needed i guess :(
    // n cubed big o motha fucka
    public static List<List<List<Integer>>> convert3dArrayToList(List<int[][]> list) {
        return null;
    }

    public static List<int[][]> convertListTo3dArray(List<List<List<Integer>>> list) {
        return null;
    }
}
