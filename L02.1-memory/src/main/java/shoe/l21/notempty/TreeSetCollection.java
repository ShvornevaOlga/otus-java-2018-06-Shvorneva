package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetCollection extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            Set<Integer> set = new TreeSet<>();
            for (int j = 0; j < size; j++) {
                set.add(j);
            }
            array[i] = set;
        }
        return array;
    }
}