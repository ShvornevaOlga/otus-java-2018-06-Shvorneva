package ru.shoe.thread.sorters;

import ru.shoe.thread.sorters.arrayutil.ArrayMerger;
import ru.shoe.thread.sorters.arrayutil.ArraySplitter;

import java.util.List;

public class Sorter {
    private SubArraysSorter subArraysSorter;

    public Sorter(SubArraysSorter subArraysSorter) {
        this.subArraysSorter = subArraysSorter;
    }

    public int[] sortArray(int[] array){
        List<int[]> subArrays = ArraySplitter.splitArray(array);
        List<int[]> sorted = subArraysSorter.sortSubArrays(subArrays);
        return ArrayMerger.mergeArrays(sorted);
    }
}
