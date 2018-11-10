package ru.shoe.l111hibernate.base;

import ru.shoe.l111hibernate.base.datasets.UserDataSet;

import java.util.List;
public interface DBService extends AutoCloseable {
    String getMetaData();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();
}
