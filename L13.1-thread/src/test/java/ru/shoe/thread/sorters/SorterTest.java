package ru.shoe.thread.sorters;

import org.junit.Test;

import static org.junit.Assert.*;

public class SorterTest {

    @Test
    public void sortArray() {
        int[] array = new int[]{2,3,8,6,9,1,3,5,5};
        assertArrayEquals(new int[]{1,2,3,3,5,5,6,8,9}, new Sorter(new OnThreadSorter()).sortArray(array));
        assertArrayEquals(new int[]{1,2,3,3,5,5,6,8,9}, new Sorter(new OnStreamSorter()).sortArray(array));
    }
}