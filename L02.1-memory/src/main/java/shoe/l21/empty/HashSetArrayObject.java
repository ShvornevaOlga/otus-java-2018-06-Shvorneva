package shoe.l21.empty;

import java.util.HashSet;

public class HashSetArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new HashSet<>();
    }
}
