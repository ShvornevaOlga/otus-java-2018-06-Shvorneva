package shoe.l21.empty;

import java.util.ArrayList;

public class ListArrayObject implements ObjectCreator {
    @Override
    public Object createObject() {
        return new ArrayList<>();
    }
}