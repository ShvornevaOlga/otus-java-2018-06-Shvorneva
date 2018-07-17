package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetCollection extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            Set<Integer> set = new LinkedHashSet<>();
            for (int j = 0; j < size; j++) {
                set.add(j);
            }
            array[i] = set;
        }
        return array;
    }
}
