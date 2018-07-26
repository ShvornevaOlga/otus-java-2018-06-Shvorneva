package shoe.l21.notempty;

import java.util.ArrayList;
import java.util.List;

public class ArrayListCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            list.add(1);
        }
        return list;
    }
}
