package shoe.l21;

public class StringArrayObject extends EmptyArray implements ArrayObject {

    @Override
    public Object[] getCompletedArray() {
        for (int i = 0; i < array.length; i++) {
            //array[i] = new String(""); //String pool
           // array[i] = new String(new char[0]); // java8 or java9
           // array[i] = new String(new byte[0]);
            array[i] = new Integer(1);
        }
        return array;
    }
}
