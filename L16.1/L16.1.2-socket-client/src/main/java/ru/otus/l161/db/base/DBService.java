package ru.otus.l161.db.base;


import ru.otus.l161.db.base.datasets.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();
}
