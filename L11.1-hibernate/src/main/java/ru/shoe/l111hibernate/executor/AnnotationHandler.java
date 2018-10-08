package ru.shoe.l111hibernate.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationHandler {
    public static boolean isAnnotated(Field field, Class annotationClass) {
        boolean isAnnotated = false;
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation.annotationType().equals(annotationClass)) {
                isAnnotated = true;
            }
        }
        return isAnnotated;
    }
}
