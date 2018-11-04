package ru.shoe.thread.sorters.arrayutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySplitter {
    private static final int NUMBER = 4;
    public static List<int[]> splitArray(int[] array) {
        int subArraySize = (int) Math.ceil(array.length / (double) NUMBER);
        int lower = 0;
        List<int[]> subArrayList = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            int max = lower + subArraySize;
            subArrayList.add(Arrays.copyOfRange(array, lower, max >= array.length ? array.length : max));
            lower += subArraySize;
        }
        return subArrayList;
    }
}
