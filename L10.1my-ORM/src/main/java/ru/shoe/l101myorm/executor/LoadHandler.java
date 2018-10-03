package ru.shoe.l101myorm.executor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadHandler<T> implements TResultHandler<T> {
    private Class<T> clazz;

    public LoadHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handle(ResultSet result) throws SQLException {

        if (result != null) {
            result.next();
            try {
                T myInstance = clazz.getConstructor().newInstance();
                for (Field field : myInstance.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Class type = field.getType();
                    if (type.equals(int.class) || type.equals(Integer.class)) {
                        field.set(myInstance, result.getInt(field.getName()));
                    }
                    if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        field.set(myInstance, result.getBoolean(field.getName()));
                    }
                    if (type.equals(byte.class) || type.equals(Byte.class)) {
                        field.set(myInstance, result.getByte(field.getName()));
                    }
                    if (type.equals(char.class) || type.equals(Character.class)) {
                        field.set(myInstance, result.getCharacterStream(field.getName()));
                    }
                    if (type.equals(short.class) || type.equals(Short.class)) {
                        field.set(myInstance, result.getShort(field.getName()));
                    }
                    if (type.equals(long.class) || type.equals(Long.class)) {
                        field.set(myInstance, result.getLong(field.getName()));
                    }
                    if (type.equals(float.class) || type.equals(Float.class)) {
                        field.set(myInstance, result.getFloat(field.getName()));
                    }
                    if (type.equals(double.class) || type.equals(Double.class)) {
                        field.set(myInstance, result.getDouble(field.getName()));
                    }
                    if (type.equals(String.class)) {
                        field.set(myInstance, result.getString(field.getName()));
                    }
                    field.setAccessible(false);

                }
                Field field = myInstance.getClass().getSuperclass().getDeclaredField("id");
                field.setAccessible(true);
                field.set(myInstance, result.getLong("id"));
                field.setAccessible(false);
                return myInstance;
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
