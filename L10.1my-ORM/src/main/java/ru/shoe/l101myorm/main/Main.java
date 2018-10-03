package ru.shoe.l101myorm.main;

import ru.shoe.l101myorm.base.DataSet;
import ru.shoe.l101myorm.base.UsersDataSet;
import ru.shoe.l101myorm.connection.DBService;
import ru.shoe.l101myorm.connection.DBServiceConnection;
import java.util.List;
/**
 * mysql> CREATE USER 'tully'@'localhost' IDENTIFIED BY 'tully';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'tully'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = getDbService()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables();
            DataSet dataSet = new UsersDataSet("tully", 21);
            DataSet dataSet2 = new UsersDataSet("sully", 22);
            dbService.save(dataSet);
            dbService.save(dataSet2);
            System.out.println(dbService.load(1, UsersDataSet.class));
            List<UsersDataSet> users = dbService.getAllUsers();
            System.out.println("All users: " + users.toString());
            dbService.deleteTables();
        }
    }

    private DBService getDbService() {
        return
                new DBServiceConnection();
    }

}
