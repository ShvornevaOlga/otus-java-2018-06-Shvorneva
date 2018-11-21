package ru.shoe.l151.app;

import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Addressee;

import java.util.List;


public interface DBServiceWithMessage extends Addressee {
    void init();

    String getMetaData();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();
}
