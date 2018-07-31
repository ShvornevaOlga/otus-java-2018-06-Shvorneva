package ru.shoe.l41;

import ru.shoe.l41.annotations.Before;
import ru.shoe.l41.annotations.Test;

import java.lang.reflect.Method;

public class ReflectionHelperTest {

    @Before
    public void beforeTheTest(){
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void instantiate() {
        TestClass testClass = ReflectionHelper.instantiate(TestClass.class);
        Assert.assertEquals(TestClass.DEFAULT_A, testClass.getA());
        Assert.assertEquals(TestClass.DEFAULT_S, testClass.getS());

        Assert.assertEquals(1, ReflectionHelper.instantiate(TestClass.class, 1).getA());
        Assert.assertEquals("A", ReflectionHelper.instantiate(TestClass.class, 1, "A").getS());
    }

    @Test
    public void getFieldValue() {
        Assert.assertEquals("A", ReflectionHelper.getFieldValue(new TestClass(1, "A"), "s"));
        Assert.assertEquals(1, ReflectionHelper.getFieldValue(new TestClass(1, "B"), "a"));
    }

    @Test
    public void setFieldValue() {
        TestClass test = new TestClass(1, "A");
        Assert.assertEquals("A", test.getS());
        ReflectionHelper.setFieldValue(test, "s", "B");
        Assert.assertEquals("B1", test.getS());
    }

    @Test
    public void callMethod() {
        Assert.assertEquals("A", ReflectionHelper.callMethod(new TestClass(1, "A"), "getS"));

        TestClass test = new TestClass(1, "A");
        ReflectionHelper.callMethod(test, "setDefault");
        Assert.assertEquals("", test.getS());
        Assert.assertTrue("1".equals(test.getS()));
    }

    @Test
    public void findMethod() throws NoSuchMethodException {
        Method method = TestClass.class.getDeclaredMethod("setDefault");
        Assert.assertEquals(method, ReflectionHelper.getAnnotatedMethod(TestClass.class, ru.shoe.l41.annotations.Test.class).get(0));
    }

}
