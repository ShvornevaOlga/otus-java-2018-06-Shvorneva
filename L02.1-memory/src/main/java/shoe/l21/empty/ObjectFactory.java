package shoe.l21.empty;

public class ObjectFactory {
    public ArrayObject getArrayObject(String type) {
        switch (type) {
            case "string":
                return new StringArrayObject();
            case "list":
                return new ListArrayObject();
            case "linkedList":
                return new LinkedListArrayObject();
            case "vector":
                return new VectorArrayObject();
            case "hashSet":
                return new HashSetArrayObject();
            case "treeSet":
                return new TreeSetArrayObject();
            case "linkedHashSet":
                return new LinkedHashSetArrayObject();
            case "hashMap":
                return new HashMapArrayObject();
            case "linkedHashMap":
                return new LinkedHashMapArrayObject();
            case "treeMap":
                return new TreeMapArrayObject();
            case "hashTable":
                return new HashTableArrayObject();
            case "weakHashMap":
                return new WeakHashMapArrayObject();
        }
        return null;
    }
}
