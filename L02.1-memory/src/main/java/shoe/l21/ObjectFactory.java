package shoe.l21;

public class ObjectFactory {
    public ArrayObject getArrayObject(String type){
        if(type.equals("string"))
            return new StringArrayObject();
        else if(type.equals("class"))
            return new ByteClassArray();
        else if(type.equals("list"))
            return new ListArrayObject();
        return null;
    }
}
