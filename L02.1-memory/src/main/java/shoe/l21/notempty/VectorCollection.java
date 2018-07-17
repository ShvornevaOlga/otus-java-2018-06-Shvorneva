package shoe.l21.notempty;

import shoe.l21.EmptyArray;

import java.util.List;
import java.util.Vector;

public class VectorCollection extends EmptyArray implements ArrayCollection {
    @Override
    public Object[] getCompletedArray(int size) {
        for (int i = 0; i < array.length; i++) {
            List<Integer> list = new Vector<>();
            for (int j = 0; j < size; j++) {
                list.add(1);
            }
            array[i] = list;
        }
        return array;
    }
}
