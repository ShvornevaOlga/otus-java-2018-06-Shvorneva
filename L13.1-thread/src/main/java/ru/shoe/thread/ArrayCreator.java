package ru.shoe.thread;

import java.util.Random;

class ArrayCreator {
    static int[] getArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
}
