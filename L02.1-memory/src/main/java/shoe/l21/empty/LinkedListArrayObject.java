package shoe.l21.empty;

import java.util.LinkedList;

public class LinkedListArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new LinkedList<> ();
    }
}
