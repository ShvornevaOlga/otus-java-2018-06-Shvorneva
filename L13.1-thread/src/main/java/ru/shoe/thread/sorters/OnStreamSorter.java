package ru.shoe.thread.sorters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OnStreamSorter implements SubArraysSorter {
    @Override
    public List<int[]> sortSubArrays(List<int[]> subArrays) {
        return subArrays
                .parallelStream()
                .map(arr -> Arrays.stream(arr)
                        .sorted()
                        .toArray()
                )
                .collect(Collectors.toList());
    }
}
