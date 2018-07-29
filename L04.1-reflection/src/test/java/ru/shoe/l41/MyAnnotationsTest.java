package ru.shoe.l41;

import ru.shoe.l41.annotations.After;
import ru.shoe.l41.annotations.Before;
import ru.shoe.l41.annotations.Test;

public class MyAnnotationsTest {

    public MyAnnotationsTest() {
        System.out.println("Call of the constructor");
    }

    @Before
    public void before(){
        System.out.println("Before");
    }

    @Test
    public void testOne(){
        System.out.println("testOne");
    }

    @Test
    public void testTwo(){
        System.out.println("testTwo");
    }

    @After
    public void after(){
        System.out.println("After");
    }

}
