package shoe.l21.empty;

public class ObjectFactory {
    public Object createObject(String creator) {
        switch (creator) {
            case "String":
                return new StringArrayObject().createObject();
            case "ArrayList":
                return new ListArrayObject().createObject();
            case "LinkedList":
                return new LinkedListArrayObject().createObject();
            case "Vector":
                return new VectorArrayObject().createObject();
            case "HashSet":
                return new HashSetArrayObject().createObject();
            case "TreeSet":
                return new TreeSetArrayObject().createObject();
            case "LinkedHashSet":
                return new LinkedHashSetArrayObject().createObject();
            case "HashMap":
                return new HashMapArrayObject().createObject();
            case "LinkedHashMap":
                return new LinkedHashMapArrayObject().createObject();
            case "TreeMap":
                return new TreeMapArrayObject().createObject();
            case "HashTable":
                return new HashTableArrayObject().createObject();
            case "WeakHashMap":
                return new WeakHashMapArrayObject().createObject();
            default:
                return null;
        }
    }
}
