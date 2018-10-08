package ru.shoe.l101myorm.connection;

import ru.shoe.l101myorm.base.DataSet;
import ru.shoe.l101myorm.base.UsersDataSet;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void prepareTables() throws SQLException;

    <T extends DataSet> Collection<T> getAllUsers(Class<T> clazz) throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet>void save(T user);

    <T extends DataSet>T load(long id, Class<T> clazz);
}
