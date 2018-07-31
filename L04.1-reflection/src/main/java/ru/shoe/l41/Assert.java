package ru.shoe.l41;

import java.util.Arrays;

public class Assert {
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Condition is not true");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Condition is not false");
        }
    }

    public static void assertNotNull(Object object) {
        assertTrue(object != null);
    }

    public static void assertEquals(java.lang.Object expected, java.lang.Object actual) {

        if (!expected.equals(actual)) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected.toString() + "\n" +
                    "Actual :" + actual.toString();
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(String expected, String actual) {

        if (!expected.equals(actual)) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(int expected, int actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(long expected, long actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(short expected, short actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(char expected, char actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(byte expected, byte actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(boolean expected, boolean actual) {

        if (expected != actual) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(float expected, float actual) {

        if (Float.compare(expected, actual) != 0) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(double expected, double actual) {

        if (Double.compare(expected, actual) != 0) {
            String message = "ComparisonFailure" + "\n" + "Expected :" +
                    expected + "\n" + "Actual :" + actual;
            throw new AssertionError(message);
        }
    }

    public static void assertEqualsArray(Object[] expecteds, Object[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(int[] expecteds, int[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(boolean[] expecteds, boolean[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(byte[] expecteds, byte[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(char[] expecteds, char[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(short[] expecteds, short[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(long[] expecteds, long[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(float[] expecteds, float[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

    public static void assertEqualsArray(double[] expecteds, double[] actuals) {
        if (!Arrays.equals(expecteds, actuals)) {
            throw new AssertionError("arrays are of different" + "\n" +
                    "expecteds: " + Arrays.toString(expecteds) + "\n" +
                    "actuals: " + Arrays.toString(actuals));
        }
    }

}
