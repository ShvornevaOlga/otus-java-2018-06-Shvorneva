package ru.shoe.thread;

import org.junit.Test;
import ru.shoe.thread.sorters.arrayutil.ArrayMerger;

import java.util.List;

import static org.junit.Assert.*;

public class ArrayMergerTest {

    @Test
    public void mergeArrays() {
        int[] arr1 = {1,3,5};
        int[] arr2 = {2,4,6};
        int[] arr3 = {2};
        List<int[]> list = List.of(arr1,arr3,arr2);
        assertArrayEquals(new int[]{1,2,2,3,4,5,6}, ArrayMerger.mergeArrays(list));
    }

    @Test
    public void mergeTwoArray() {
        int[] arr1 = {1,3,5};
        int[] arr2 = {2,4};
        assertArrayEquals(new int[]{1,2,3,4,5}, ArrayMerger.mergeTwoArray(arr1,arr2));
    }
}