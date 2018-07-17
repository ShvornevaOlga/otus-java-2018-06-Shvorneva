package shoe.l21.empty;

import shoe.l21.EmptyArray;

import java.util.WeakHashMap;

public class WeakHashMapArrayObject extends EmptyArray implements ArrayObject {
    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = new WeakHashMap<>();
        }
        return array;
    }
}