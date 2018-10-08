package ru.shoe.l111hibernate.dbService.myDbService.dao;

import ru.shoe.l111hibernate.base.datasets.UserDataSet;
import ru.shoe.l111hibernate.executor.TExecutor;

import java.sql.Connection;

public class UserDataSetDAO {
    private TExecutor executor;

    public UserDataSetDAO(Connection connection) {
        executor = new TExecutor(connection);
    }

    public void save(UserDataSet dataSet) {
        executor.save(dataSet);
    }

    public UserDataSet read(long id) {
        return executor.load(id, UserDataSet.class);
    }

    public UserDataSet readByName(String name) {
        return executor.loadCriteria("name", name, UserDataSet.class);
    }
}
