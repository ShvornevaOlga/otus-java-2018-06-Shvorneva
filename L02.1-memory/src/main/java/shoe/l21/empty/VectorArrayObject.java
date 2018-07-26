package shoe.l21.empty;

import java.util.Vector;

public class VectorArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new Vector<>();
    }
}