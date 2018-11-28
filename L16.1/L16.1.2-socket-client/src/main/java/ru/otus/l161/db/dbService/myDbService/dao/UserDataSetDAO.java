package ru.otus.l161.db.dbService.myDbService.dao;



import ru.otus.l161.db.base.datasets.UserDataSet;
import ru.otus.l161.db.executor.DBServiceException;
import ru.otus.l161.db.executor.TExecutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDataSetDAO {
    private TExecutor executor;

    public UserDataSetDAO(Connection connection) {
        executor = new TExecutor(connection);
    }

    public void save(UserDataSet dataSet) {
        try {
            executor.save(dataSet);
        } catch (DBServiceException e) {
            System.err.println("Can not save " + dataSet);
            e.printStackTrace();
        }
    }

    public UserDataSet read(long id) {
        try {
            return executor.load(id, UserDataSet.class);
        } catch (DBServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDataSet readByName(String name) {
        try {
            return executor.loadCriteria("name", name, UserDataSet.class);
        } catch (DBServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserDataSet> readAll() {
        try {
            return new ArrayList<>(executor.loadAll(UserDataSet.class));
        } catch (DBServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
