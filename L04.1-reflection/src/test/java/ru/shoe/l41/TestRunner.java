package ru.shoe.l41;

public class TestRunner {
    public static void main(String[] args) {
        MyTestFramework myTestFramework = new MyTestFramework();
        myTestFramework.run(MyAnnotationsTest.class);
        myTestFramework.run(ReflectionHelperTest.class);
    }
}
