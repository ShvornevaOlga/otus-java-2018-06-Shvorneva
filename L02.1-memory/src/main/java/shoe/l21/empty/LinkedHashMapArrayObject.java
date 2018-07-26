package shoe.l21.empty;

import java.util.LinkedHashMap;

public class LinkedHashMapArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new LinkedHashMap<>();
    }
}