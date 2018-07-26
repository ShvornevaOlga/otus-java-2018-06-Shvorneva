package shoe.l21.empty;

import java.util.Hashtable;

public class HashTableArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new Hashtable<>();
    }
}
