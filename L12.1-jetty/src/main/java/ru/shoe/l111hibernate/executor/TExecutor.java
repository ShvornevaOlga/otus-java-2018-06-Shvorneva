package ru.shoe.l111hibernate.executor;

import ru.shoe.l111hibernate.base.DataSet;
import ru.shoe.l111hibernate.tree.QueryBuilder;
import ru.shoe.l111hibernate.tree.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    private <T> T execQuery(String query, ExecuteHandler prepare, TResultHandler<T> handler) throws DBServiceException {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            prepare.accept(stmt);
            ResultSet result = stmt.getResultSet();
            if (!result.next()) return null;
            result.beforeFirst();
            return handler.handle(result);
        } catch (SQLException e) {
            throw new DBServiceException(e.getMessage());
        }
    }

    private <T> Collection<T> execQueryList(String query, ExecuteHandler prepare, TResultHandler<T> handler) throws DBServiceException {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            prepare.accept(stmt);
            ResultSet result = stmt.getResultSet();
            Collection<T> list = new ArrayList<>();
            if (!result.next()) return list;
            result.beforeFirst();
            while (!result.isLast()) {
                T dataset = handler.handle(result);
                list.add(dataset);
            }
            return list;
        } catch (SQLException e) {
            throw new DBServiceException(e.getMessage());
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) throws DBServiceException {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            prepare.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            throw new DBServiceException(e.getMessage());
        }
    }

    public <T extends DataSet> void save(T dataset) throws DBServiceException {
        execUpdate(QueryBuilder.insertDataSet(dataset), statement -> {
            QueryBuilder.acceptStatment(dataset, statement);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                try {
                    Field fieldId = dataset.getClass().getSuperclass().getDeclaredField("id");
                    fieldId.setAccessible(true);
                    fieldId.set(dataset, rs.getLong(1));
                    fieldId.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        setParentIdInDB(dataset);
        System.out.println("DataSet added");
    }

    private <T extends DataSet> void setParentIdInDB(T dataset) throws DBServiceException {
        for (Field field : dataset.getClass().getDeclaredFields()) {
            if (AnnotationHandler.isAnnotated(field, javax.persistence.OneToMany.class)) {
                Type t = field.getGenericType();
                Class genericType;
                if (t instanceof ParameterizedType) {
                    genericType = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];
                    if (DataSet.class.isAssignableFrom(genericType)) {
                        Collection collection = (Collection) (ReflectionHelper.getFieldValue(dataset, field.getName()));
                        if (collection != null)
                            for (Object o : collection) {
                                save((DataSet) o);
                                String update = String.format("UPDATE %s SET %s = %d where id = %d",
                                        genericType.getSimpleName(),
                                        dataset.getClass().getSimpleName() + "_id",
                                        dataset.getId(),
                                        ((DataSet) o).getId());
                                execUpdate(update, PreparedStatement::execute);
                            }
                    }
                }
            }
        }
    }


    public <T extends DataSet> T load(long id, Class<T> clazz) throws DBServiceException {
        String query = String.format("select * from %s where id = ?", clazz.getSimpleName());
        return execQuery(query, statement -> {
            statement.setLong(1, id);
            statement.execute();
        }, new LoadHandler<>(clazz));
    }

    public <T extends DataSet> Collection<T> loadAll(Class<T> clazz) throws DBServiceException {
        String query = String.format("select * from %s", clazz.getSimpleName());
        return execQueryList(query, PreparedStatement::execute, new LoadHandler<>(clazz));
    }

    <T extends DataSet> Collection<T> loadCriteriaList(String criteriaName, String criteriaData, Class<T> clazz) throws DBServiceException {
        String query = String.format("select * from %s where %s = ?", clazz.getSimpleName(), criteriaName);
        return execQueryList(query, statement -> {
            statement.setString(1, criteriaData);
            statement.execute();
        }, new LoadHandler<>(clazz));
    }

    public <T extends DataSet> T loadCriteria(String criteriaName, String criteriaData, Class<T> clazz) throws DBServiceException {
        String query = String.format("select * from %s where %s = ?", clazz.getSimpleName(), criteriaName);
        return execQuery(query, statement -> {
            statement.setString(1, criteriaData);
            statement.execute();
        }, new LoadHandler<>(clazz));
    }

    private Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
