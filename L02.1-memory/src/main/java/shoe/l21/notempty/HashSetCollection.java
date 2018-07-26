package shoe.l21.notempty;

import java.util.HashSet;
import java.util.Set;

public class HashSetCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        Set<Integer> set = new HashSet<>();
        for (int j = 0; j < size; j++) {
            set.add(j);
        }
        return set;
    }
}