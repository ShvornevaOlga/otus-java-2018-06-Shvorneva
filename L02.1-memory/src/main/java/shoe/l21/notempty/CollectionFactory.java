package shoe.l21.notempty;

public class CollectionFactory {
    public Object createObject(String creator, int size) {
        switch (creator) {
            case "ArrayList":
                return new ArrayListCollection().createObject(size);
            case "LinkedList":
                return new LinkedListCollection().createObject(size);
            case "Vector":
                return new VectorCollection().createObject(size);
            case "HashSet":
                return new HashSetCollection().createObject(size);
            case "TreeSet":
                return new TreeSetCollection().createObject(size);
            case "LinkedHashSet":
                return new LinkedHashSetCollection().createObject(size);
            case "HashMap":
                return new HashMapCollection().createObject(size);
            case "LinkedHashMap":
                return new LinkedHashMapCollection().createObject(size);
            case "TreeMap":
                return new TreeMapCollection().createObject(size);
            case "HashTable":
                return new HashTableCollection().createObject(size);
            case "WeakHashMap":
                return new WeakHashMapCollection().createObject(size);
            default:
                return null;
        }
    }
}
