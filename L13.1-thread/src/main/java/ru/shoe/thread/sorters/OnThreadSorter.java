package ru.shoe.thread.sorters;

import java.util.ArrayList;
import java.util.List;

public class OnThreadSorter implements SubArraysSorter {
    @Override
    public List<int[]> sortSubArrays(List<int[]> subArrays) {
        List<Thread> threads = new ArrayList<>();
        for (int[] arr:subArrays){
            Thread thread = new SortThread(arr);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return subArrays;
    }
}
