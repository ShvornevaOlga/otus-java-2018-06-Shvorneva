package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.Hashtable;
import java.util.Map;

public class HashTableCollection  extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            Map<Integer, Integer> map = new Hashtable<>();
            for (int j = 0; j < size; j++) {
                map.put(j,j);
            }
            array[i] = map;
        }
        return array;
    }
}