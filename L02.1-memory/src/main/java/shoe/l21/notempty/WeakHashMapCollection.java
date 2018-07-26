package shoe.l21.notempty;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapCollection  implements CollectionCreator {

    @Override
    public Object createObject(int size) {
        Map<Integer, Integer> map = new WeakHashMap<>();
        for (int j = 0; j < size; j++) {
            map.put(j,j);
        }
        return map;
    }
}