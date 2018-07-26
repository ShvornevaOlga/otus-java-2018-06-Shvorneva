package shoe.l21.notempty;

import java.util.List;
import java.util.Vector;

public class VectorCollection implements CollectionCreator{
    @Override
    public Object createObject(int size) {
        List<Integer> list = new Vector<>();
        for (int j = 0; j < size; j++) {
            list.add(1);
        }
        return list;
    }
}
