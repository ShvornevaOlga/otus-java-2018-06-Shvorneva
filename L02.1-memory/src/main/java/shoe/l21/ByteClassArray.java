package shoe.l21;

import java.util.ArrayList;
import java.util.List;

public class ByteClassArray extends EmptyArray implements ArrayObject {

    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
          //  array[i] = new long[1];
            array[i] = new MyClass();
        }
        return array;
    }
    private static class MyClass {
        List<Integer> list = new ArrayList<>();
        // private byte b = 0;     // +1
       // private String str = new String(new byte[0]);
        //array[i] = new String(""); //String pool
        // array[i] = new String(new char[0]); // java8 or java9
       // array[i] = new String(new byte[0]);
//private List<Integer> list = new ArrayList<>();
        //private int i = 0;      // +4
       // private long l = 1;     // +8
      //  private long l2 = 1;
       // private long l3 = 1;
    }

}
