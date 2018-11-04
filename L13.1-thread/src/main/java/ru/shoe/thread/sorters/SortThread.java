package ru.shoe.thread.sorters;

import java.util.Arrays;

public class SortThread extends Thread {
    private int[] array;
    SortThread(int[] array){
        this.array = array;
    }
    @Override
    public void run() {
        Arrays.sort(array);
    }
}
