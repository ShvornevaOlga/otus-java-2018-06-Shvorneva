package shoe.l21.notempty;

public class CollectionFactory {
    public ArrayCollection getArrayObject(String type){
        switch (type) {
            case "array":
                return new ArrayListCollection();
            case "linked":
                return new LinkedListCollection();
            case "vector":
                return new VectorCollection();
            case "hashSet":
                return new HashSetCollection();
            case "treeSet":
                return new TreeSetCollection();
            case "linkedHashSet":
                return new LinkedHashSetCollection();
            case "hashMap":
                return new HashMapCollection();
            case "linkedHashMap":
                return new LinkedHashMapCollection();
            case "treeMap":
                return new TreeMapCollection();
            case "hashTable":
                return new HashTableCollection();
            case "weakHashMap":
                return new WeakHashMapCollection();
        }
        return null;
    }
}
