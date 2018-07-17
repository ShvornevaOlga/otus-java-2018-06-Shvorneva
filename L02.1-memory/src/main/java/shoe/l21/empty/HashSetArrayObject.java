package shoe.l21.empty;

import shoe.l21.EmptyArray;

import java.util.HashSet;

public class HashSetArrayObject extends EmptyArray implements ArrayObject {
    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = new HashSet<>();
        }
        return array;
    }
}
