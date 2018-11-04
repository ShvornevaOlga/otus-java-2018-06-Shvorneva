package ru.shoe.thread.sorters.arrayutil;

import java.util.List;

public class ArrayMerger {
    public static int[] mergeArrays(List<int[]> arrays) {
        int[] mergedArray = arrays.get(0);
        for (int i = 1; i < arrays.size(); i++) {
            mergedArray = mergeTwoArray(mergedArray, arrays.get(i));
        }
        return mergedArray;
    }

    public static int[] mergeTwoArray(int[] arr1, int[] arr2) {
        int[] mergedArray = new int[arr1.length + arr2.length];
        int pointerArr1 = 0;
        int pointerArr2 = 0;
        int i = 0;
        while (pointerArr1 < arr1.length && pointerArr2 < arr2.length) {
            if (arr1[pointerArr1] < arr2[pointerArr2]) {
                mergedArray[i++] = arr1[pointerArr1++];
            } else {
                mergedArray[i++] = arr2[pointerArr2++];
            }
        }
        while (pointerArr1 < arr1.length) {
            mergedArray[i++] = arr1[pointerArr1++];
        }
        while (pointerArr2 < arr2.length) {
            mergedArray[i++] = arr2[pointerArr2++];
        }
        return mergedArray;
    }
}
