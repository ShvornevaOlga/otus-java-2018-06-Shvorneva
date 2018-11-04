package ru.shoe.thread;

import ru.shoe.thread.sorters.*;

import java.util.Arrays;

public class Main {

    private static final int SIZE_ARRAY = 1_000_000;
    private static final int NANO_TO_MILLS = 1_000_000;
    private static final int COUNT = 100;

    public static void main(String[] args) {
        threadSort();
        streamSort();
        arraysSort();
    }

    private static void arraysSort() {
        long time_a = 0;
        for (int i = 0; i < COUNT; i++) {
            int[] array = ArrayCreator.getArray(SIZE_ARRAY);
            long startTime = System.nanoTime();
            Arrays.sort(array);
            time_a += System.nanoTime() - startTime;
        }
        System.out.println("Arrays sort time: " + time_a / COUNT / NANO_TO_MILLS + " ms");
    }

    private static void threadSort() {
        Sorter sorter = new Sorter(new OnThreadSorter());
        long time_t = 0;
        for (int i = 0; i < COUNT; i++) {
            int[] array = ArrayCreator.getArray(SIZE_ARRAY);
            long startTime = System.nanoTime();
            sorter.sortArray(array);
            time_t += System.nanoTime() - startTime;
        }
        System.out.println("On thread sort time: " + time_t / COUNT / NANO_TO_MILLS + " ms");
    }

    private static void streamSort() {
        Sorter sorter = new Sorter(new OnStreamSorter());
        long time_s = 0;
        for (int i = 0; i < COUNT; i++) {
            int[] array = ArrayCreator.getArray(SIZE_ARRAY);
            long startTime = System.nanoTime();
            sorter.sortArray(array);
            time_s += System.nanoTime() - startTime;
        }
        System.out.println("On stream sort time: " + time_s / COUNT / NANO_TO_MILLS + " ms");
    }

}
