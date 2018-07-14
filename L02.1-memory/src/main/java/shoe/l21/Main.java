package shoe.l21;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        int size = 2_00_000;

        //System.out.println("Starting the loop");
        while (true) {
            long mem = getMem();
            System.out.println("Mem: " + mem);
            ObjectFactory factory = new ObjectFactory();
            ArrayObject arrayObject = factory.getArrayObject("list");
            Object[] array = arrayObject.getArray(size);

            long mem2 = getMem();
            System.out.println("Ref size: " + (mem2 - mem) / array.length);

          /*  for (int i = 0; i < array.length; i++) {
               // array[i] = new Object();
                //array[i] = new String(""); //String pool
                //array[i] = new String(new char[0]); // java8 or java9
                //array[i] = new String(new byte[0]); //without String pool
                //array[i] = new MyClass();
                //array[i] = new byte[1]; //16 or 24 with -XX:-UseCompressedOops
                array[i] = new boolean[1];
            }*/
            array = arrayObject.getCompletedArray();
            long mem3 = getMem();
            System.out.println("Element size: " + (mem3 - mem2) / array.length);

            array = null;
            System.out.println("Array is ready for GC");

            Thread.sleep(1000); //wait for 1 sec
        }
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private  class MyClass {
        //private byte b = 0;     // +1
        //private int i = 0;      // +4
        //private long l = 1;     // +8
    }
}