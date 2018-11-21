package ru.shoe.l151.db.executor;

import ru.shoe.l151.db.base.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LoadHandler<T> implements TResultHandler<T> {
    private Class<T> clazz;

    LoadHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handle(ResultSet result) throws SQLException {
        if (result != null) {
            result.next();
            try {
                T myInstance = clazz.getConstructor().newInstance();
                Field fieldId = myInstance.getClass().getSuperclass().getDeclaredField("id");
                fieldId.setAccessible(true);
                fieldId.set(myInstance, result.getLong("id"));
                fieldId.setAccessible(false);
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
                    if (DataSet.class.isAssignableFrom(type)) {
                        if (!AnnotationHandler.isAnnotated(field, javax.persistence.ManyToOne.class)) {
                            TExecutor executor = new TExecutor(result.getStatement().getConnection());
                            field.set(myInstance, executor.load(result.getLong(field.getName()+"_id"), type));
                        }
                    }
                    if (AnnotationHandler.isAnnotated(field, javax.persistence.OneToMany.class)) {
                        loadCollection(result, myInstance, field);
                    }
                    field.setAccessible(false);
                }

                return myInstance;
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | NoSuchFieldException | DBServiceException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private void loadCollection(ResultSet result, T myInstance, Field field) throws SQLException, IllegalAccessException {
        TExecutor executor = new TExecutor(result.getStatement().getConnection());
        Type t = field.getGenericType();
        if (t instanceof ParameterizedType) {
            Class genericType = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];
            if (DataSet.class.isAssignableFrom(genericType)) {
                Collection<DataSet> collection = null;
                try {
                    collection = executor.loadCriteriaList(clazz.getSimpleName()+"_id", String.valueOf(result.getLong("id")), genericType);
                } catch (DBServiceException e) {
                    e.printStackTrace();
                }
                if (collection != null) {
                    for (DataSet dataSet : collection) {
                        for (Field collField : dataSet.getClass().getDeclaredFields()) {
                            if (collField.getType().equals(clazz)) {
                                collField.setAccessible(true);
                                collField.set(dataSet, myInstance);
                                collField.setAccessible(false);
                            }
                        }
                    }
                    if (field.getType().equals(Set.class))
                        field.set(myInstance, new HashSet<>(collection));
                    if (field.getType().equals(List.class))
                        field.set(myInstance, new ArrayList<>(collection));
                }
            }
        }
    }

}
