package shoe.l21.notempty;

import java.util.Hashtable;
import java.util.Map;

public class HashTableCollection  implements CollectionCreator {

    @Override
    public Object createObject(int size) {
        Map<Integer, Integer> map = new Hashtable<>();
        for (int j = 0; j < size; j++) {
            map.put(j,j);
        }
        return map;
    }
}