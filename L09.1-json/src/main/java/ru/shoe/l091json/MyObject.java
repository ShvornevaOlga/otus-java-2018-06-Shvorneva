package ru.shoe.l091json;

import java.util.*;

class MyObject {
    private float floatValue;
    private double doubleValue;
    private boolean booleanValue;
    private byte byteValue;
    private char charValue;
    private int intValue;
    private long longValue;
    private short shortValue;
    private String stringValue;
    private int[] intArrayValue;
    private long[] longArrayValue;
    private double[] doubleArrayValue;
    private String[] stringArrayValue;
    private List<String> stringListValue;
    private List<Float> floatListValue;
    private Set<Long> longSetValue;
    private Map<Long, Integer> mapValue;
    private MyClass myClass;


    MyObject() {
        floatValue = 5.1f;
        doubleValue = 2.3;
        booleanValue = false;
        byteValue = 1;
        charValue = 'a';
        longValue = 5l;
        intValue = 3;
        shortValue = 5;
        stringValue = "myString";
        intArrayValue = new int[]{1, 2, 3};
        longArrayValue = new long[]{1l, 2l, 3l};
        doubleArrayValue = new double[]{1.1, 2.2, 3.3};
        stringArrayValue = new String[]{"str1", "str2", "str3"};
        stringListValue = List.of("strList1", "strList2", "strList3");
        floatListValue = List.of(5.1f, 6f, 7f);
        longSetValue = Set.of(1l, 2l, 3l);
        mapValue = Map.of(1l, 1, 2l, 2);
        myClass = new MyClass();
    }

    static class MyClass {
        int anInt;

        MyClass() {
            anInt = 8;
        }
    }
}
