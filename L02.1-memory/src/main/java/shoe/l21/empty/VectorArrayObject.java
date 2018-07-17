package shoe.l21.empty;

import shoe.l21.EmptyArray;

import java.util.Vector;

public class VectorArrayObject extends EmptyArray implements ArrayObject {
    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = new Vector<>();
        }
        return array;
    }
}