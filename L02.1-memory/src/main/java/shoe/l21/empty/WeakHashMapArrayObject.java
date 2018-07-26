package shoe.l21.empty;

import java.util.WeakHashMap;

public class WeakHashMapArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new WeakHashMap<>();
    }
}