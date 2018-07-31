package ru.shoe.l41;

import ru.shoe.l41.annotations.Test;

@SuppressWarnings("unused")
public class TestClass {
    static final int DEFAULT_A  = 0;
    static final String DEFAULT_S = "";

    private int a;
    private String s;

    public TestClass() {
        this(DEFAULT_A, DEFAULT_S);
    }

    public TestClass(int a) {
       this(a, DEFAULT_S);
    }

    public TestClass(Integer a) {
        this(a, DEFAULT_S);
    }

    TestClass(Integer a, String s) {
        this.a = a;
        this.s = s;
    }

    int getA() {
        return a;
    }

    String getS() {
        return s;
    }
@Test
    private void setDefault(){
        a = DEFAULT_A;
        s = DEFAULT_S;
    }
}
