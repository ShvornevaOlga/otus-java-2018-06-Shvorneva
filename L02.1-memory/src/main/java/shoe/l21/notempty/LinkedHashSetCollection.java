package shoe.l21.notempty;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetCollection implements CollectionCreator {
     @Override
    public Object createObject(int size) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int j = 0; j < size; j++) {
            set.add(j);
        }
        return set;
    }
}
