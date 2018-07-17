package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapCollection extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            Map<Integer, Integer> map = new LinkedHashMap<>();
            for (int j = 0; j < size; j++) {
                map.put(j, j);
            }
            array[i] = map;
        }
        return array;
    }
}