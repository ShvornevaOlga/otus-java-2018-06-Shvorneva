package shoe.l21.empty;

import java.util.LinkedHashSet;

public class LinkedHashSetArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new LinkedHashSet<>();
    }
}
