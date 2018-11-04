package ru.shoe.thread.sorters;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OnStreamSorterTest {

    @Test
    public void sortSubArrays() {
        int[] arr1 = {5,1,3};
        int[] arr2 = {4,2,6};
        List<int[]> list = List.of(arr1,arr2);
        List<int[]> res = new OnStreamSorter().sortSubArrays(list);
        assertArrayEquals(new int[]{1,3,5}, res.get(0));
        assertArrayEquals(new int[]{2,4,6}, res.get(1));
    }
}