package shoe.l21.notempty;

import java.util.HashMap;
import java.util.Map;

public class HashMapCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int j = 0; j < size; j++) {
            map.put(j, j);
        }
        return map;
    }
}