package shoe.l21;

import shoe.l21.empty.*;
import shoe.l21.notempty.*;

import java.lang.management.ManagementFactory;

public class Main {
    private static ObjectFactory objectFactory = new ObjectFactory();
    private static CollectionFactory collectionFactory = new CollectionFactory();

    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        getEmptyObjectMem();
        getObjectMem();
    }

    private static void getObjectMem() throws InterruptedException {
        int size = 10;
        for (int i = 0; i <= 3; i++) {
            System.out.println("ArrayList for " + size + " object:");
            getCollectionMem("ArrayList", size);
            size = size + (size >> 1);
            System.out.println();
        }
        for (int i = 0; i <= 3; i++) {
            System.out.println("LinkedList for " + i + " object:");
            getCollectionMem("LinkedList", i);
            System.out.println();
        }
        size = 10;
        for (int i = 0; i <= 3; i++) {
            System.out.println("Vector for " + size + " object:");
            getCollectionMem("Vector", size);
            size = size + size;
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashSet for " + i + " object:");
            getCollectionMem("HashSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("TreeSet for " + i + " object:");
            getCollectionMem("TreeSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("LinkedHashSet for " + i + " object:");
            getCollectionMem("LinkedHashSet", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashMap for " + i + " object:");
            getCollectionMem("HashMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("LinkedHashMap for " + i + " object:");
            getCollectionMem("LinkedHashMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("TreeMap for " + i + " object:");
            getCollectionMem("TreeMap", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("HashTable for " + i + " object:");
            getCollectionMem("HashTable", i);
            System.out.println();
        }
        for (int i = 1; i <= 4; i++) {
            System.out.println("WeakHashMap for " + i + " object:");
            getCollectionMem("WeakHashMap", i);
            System.out.println();
        }

    }

    private static void getEmptyObjectMem() throws InterruptedException {
        System.out.println("Empty String:");
        getObjectMem("String");
        System.out.println("Empty ArrayList:");
        getObjectMem("ArrayList");
        System.out.println("Empty LinkedList:");
        getObjectMem("LinkedList");
        System.out.println("Empty Vector:");
        getObjectMem("Vector");
        System.out.println("Empty HashSet:");
        getObjectMem("HashSet");
        System.out.println("Empty TreeSet:");
        getObjectMem("TreeSet");
        System.out.println("Empty LinkedHashSet:");
        getObjectMem("LinkedHashSet");
        System.out.println("Empty HashMap:");
        getObjectMem("HashMap");
        System.out.println("Empty LinkedHashMap:");
        getObjectMem("LinkedHashMap");
        System.out.println("Empty TreeMap:");
        getObjectMem("TreeMap");
        System.out.println("Empty HashTable:");
        getObjectMem("HashTable");
        System.out.println("Empty WeakHashMap:");
        getObjectMem("WeakHashMap");
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void getObjectMem(String creator) throws InterruptedException {
        int size = 2_000_000;
        Object[] array = new Object[size];

        long mem2 = getMem();

        for (int i = 0; i < array.length; i++) {
            array[i] = objectFactory.createObject(creator);
        }

        long mem3 = getMem();
        System.out.println("Element size: " + (mem3 - mem2) / array.length);
        System.out.println();
        array = null;
    }

    private static void getCollectionMem(String creator, int size) throws InterruptedException {
        int arraySize = 2_000_000;
        Object[] array = new Object[arraySize];

        long mem2 = getMem();
        for (int i = 0; i < array.length; i++) {
            array[i] = collectionFactory.createObject(creator, size);
        }
        long mem3 = getMem();
        System.out.println("Element size for: " + (mem3 - mem2) / array.length);
        array = null;
    }
}