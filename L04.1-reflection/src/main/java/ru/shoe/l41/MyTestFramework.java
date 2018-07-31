package ru.shoe.l41;

import ru.shoe.l41.annotations.After;
import ru.shoe.l41.annotations.Before;
import ru.shoe.l41.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

class MyTestFramework {
    <T> void run(Class<T> tClass) {
        List<Method> testMethods = ReflectionHelper.getAnnotatedMethod(tClass, Test.class);
        List<Method> beforeMethods = ReflectionHelper.getAnnotatedMethod(tClass, Before.class);
        List<Method> afterMethods = ReflectionHelper.getAnnotatedMethod(tClass, After.class);
        for (Method method : testMethods) {
            T obj = ReflectionHelper.instantiate(tClass);
            try {
                if (obj != null) {
                    for (Method beforeMethod : beforeMethods) {
                        ReflectionHelper.callMethod(obj, beforeMethod.getName());
                    }
                    ReflectionHelper.callMethod(obj, method.getName());
                }
            }finally {
                if (obj!=null)
                for (Method afterMethod : afterMethods) {
                    ReflectionHelper.callMethod(obj, afterMethod.getName());
                }
            }
        }
    }
}
