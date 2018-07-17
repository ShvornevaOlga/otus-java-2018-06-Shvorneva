package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.HashSet;
import java.util.Set;

public class HashSetCollection extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < size; j++) {
                set.add(j);
            }
            array[i] = set;
        }
        return array;
    }
}