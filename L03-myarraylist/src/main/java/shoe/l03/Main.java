package shoe.l03;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        MyArrayList<Integer> myArrayList2 = new MyArrayList<>();
        Collections.addAll(myArrayList, 8, 2, 9, 4);
        Collections.addAll(myArrayList2, 5, 6, 7, 8);
        System.out.println("Collections.addAll");
        for (Integer integer : myArrayList2) {
            System.out.println(integer);
        }
        Collections.copy(myArrayList2, myArrayList);
        System.out.println("Collections.copy");
        for (Integer integer : myArrayList2) {
            System.out.println(integer);
        }
        Collections.sort(myArrayList, Integer::compareTo);
        System.out.println("Collections.sort");
        for (Integer integer : myArrayList) {
            System.out.println(integer);
        }
    }
}
