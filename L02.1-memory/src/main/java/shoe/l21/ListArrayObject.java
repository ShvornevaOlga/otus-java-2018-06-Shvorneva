package shoe.l21;

import java.util.ArrayList;
import java.util.List;

public class ListArrayObject extends EmptyArray implements ArrayObject {

    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
            List<Long> list = new ArrayList<Long>();
            int count = 73;
            for (int j= 0; j<count;j++){
                list.add(1l);
            }
            array[i] = list;
           // ((List)array[i]).add(1);
        }
        return array;
    }
}