package ru.shoe.thread;

import org.junit.Test;
import ru.shoe.thread.sorters.arrayutil.ArraySplitter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArraySplitterTest {

    @Test
    public void splitArray() {
        int[] array = {1,2,3,4,5,6,7};
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1,2});
        list.add(new int[]{3,4});
        list.add(new int[]{5,6});
        list.add(new int[]{7});
        List<int[]> res = ArraySplitter.splitArray(array);
        assertArrayEquals(list.get(0), res.get(0));
        assertArrayEquals(list.get(1), res.get(1));
        assertArrayEquals(list.get(2), res.get(2));
        assertArrayEquals(list.get(3), res.get(3));
    }
}