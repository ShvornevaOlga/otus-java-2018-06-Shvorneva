package shoe.l21.notempty;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetCollection implements CollectionCreator {
    @Override
    public Object createObject(int size) {
        Set<Integer> set = new TreeSet<>();
        for (int j = 0; j < size; j++) {
            set.add(j);
        }
        return set;
    }
}