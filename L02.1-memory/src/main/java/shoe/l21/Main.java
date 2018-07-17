package shoe.l21;

import shoe.l21.empty.ArrayObject;
import shoe.l21.empty.ObjectFactory;
import shoe.l21.notempty.ArrayCollection;
import shoe.l21.notempty.CollectionFactory;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        ObjectFactory factory = new ObjectFactory();
        getEmptyObjectMem(factory);
        CollectionFactory collectionFactory = new CollectionFactory();
        getObjectMem(collectionFactory);
    }

    private static void getObjectMem(CollectionFactory collectionFactory) throws InterruptedException {
        int size = 10;
        for (int i = 0; i <= 3; i++) {
            System.out.println("ArrayList for " + size + " object:");
            getCollectionMem(collectionFactory, "array", size);
            size = size + (size >> 1);
            System.out.println();
        }
        for (int i = 0; i <= 3; i++) {
            System.out.println("Linkedlist for " + i + " object:");
            getCollectionMem(collectionFactory, "linked", i);
            System.out.println();
        }
        size = 10;
        for (int i = 0; i <= 3; i++) {
            System.out.println("Vector for " + size + " object:");
            getCollectionMem(collectionFactory, "vector", size);
            size = size + size;
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashSet for " + i + " object:");
            getCollectionMem(collectionFactory, "hashSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("TreeSet for " + i + " object:");
            getCollectionMem(collectionFactory, "treeSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("LinkedHashSet for " + i + " object:");
            getCollectionMem(collectionFactory, "linkedHashSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashMap for " + i + " object:");
            getCollectionMem(collectionFactory, "hashMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("LinkedHashMap for " + i + " object:");
            getCollectionMem(collectionFactory, "linkedHashMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("TreeMap for " + i + " object:");
            getCollectionMem(collectionFactory, "treeMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashTable for " + i + " object:");
            getCollectionMem(collectionFactory, "hashTable", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("WeakHashMap for " + i + " object:");
            getCollectionMem(collectionFactory, "weakHashMap", i);
            System.out.println();
        }

    }

    private static void getEmptyObjectMem(ObjectFactory factory) throws InterruptedException {
        System.out.println("Empty String:");
        getObjectMem(factory, "string");
        System.out.println("Empty ArrayList:");
        getObjectMem(factory, "list");
        System.out.println("Empty LinkedList:");
        getObjectMem(factory, "linkedList");
        System.out.println("Empty Vector:");
        getObjectMem(factory, "vector");
        System.out.println("Empty HashSet:");
        getObjectMem(factory, "hashSet");
        System.out.println("Empty TreeSet:");
        getObjectMem(factory, "treeSet");
        System.out.println("Empty LinkedHashSet:");
        getObjectMem(factory, "linkedHashSet");
        System.out.println("Empty HashMap:");
        getObjectMem(factory, "hashMap");
        System.out.println("Empty LinkedHashMap:");
        getObjectMem(factory, "linkedHashMap");
        System.out.println("Empty TreeMap:");
        getObjectMem(factory, "treeMap");
        System.out.println("Empty HashTable:");
        getObjectMem(factory, "hashTable");
        System.out.println("Empty WeakHashMap:");
        getObjectMem(factory, "weakHashMap");
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void getObjectMem(ObjectFactory factory, String object) throws InterruptedException {

        int size = 2_000_000;
        ArrayObject arrayObject = factory.getArrayObject(object);
        Object[] array = arrayObject.getArray(size);
        long mem2 = getMem();
        array = arrayObject.getCompletedArray();
        long mem3 = getMem();
        System.out.println("Element size: " + (mem3 - mem2) / array.length);
        System.out.println();
        array = null;
    }

    private static void getCollectionMem(CollectionFactory factory, String object, int size) throws InterruptedException {
        int arraySize = 2_000_000;
        ArrayCollection arrayCollection = factory.getArrayObject(object);
        Object[] array = arrayCollection.getArray(arraySize);
        long mem2 = getMem();
        array = arrayCollection.getCompletedArray(size);
        long mem3 = getMem();
        System.out.println("Element size for: " + (mem3 - mem2) / array.length);
        array = null;
    }
}