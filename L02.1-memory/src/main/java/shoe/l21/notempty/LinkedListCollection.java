package shoe.l21.notempty;

import java.util.LinkedList;
import java.util.List;

public class LinkedListCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        List<Integer> list = new LinkedList<>();
        for (int j = 0; j < size; j++) {
            list.add(1);
        }
        return list;
    }
}
