package shoe.l21.notempty;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int j = 0; j < size; j++) {
            map.put(j, j);
        }
        return map;
    }
}