package ru.shoe.l111hibernate.main;

import ru.shoe.l111hibernate.base.datasets.AdressDataSet;
import ru.shoe.l111hibernate.base.datasets.PhoneDataSet;
import ru.shoe.l111hibernate.base.datasets.UserDataSet;
import ru.shoe.l111hibernate.base.DBService;
import ru.shoe.l111hibernate.dbService.myDbService.DBServiceImpl;

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
            PhoneDataSet phone1 = new PhoneDataSet("12345");
            PhoneDataSet phone2 = new PhoneDataSet("456789");
            AdressDataSet adress = new AdressDataSet("street1");
            UserDataSet user1 = new UserDataSet("tully", 21, adress);
            user1.addPhone(phone1);
            user1.addPhone(phone2);
            dbService.save(user1);
            PhoneDataSet phone3 = new PhoneDataSet("896523");
            AdressDataSet adress1 = new AdressDataSet("street2");
            UserDataSet user2 = new UserDataSet("sully", 33, adress1);
            user2.addPhone(phone3);
            dbService.save(user2);
            System.out.println("User bu id = 1: " + dbService.read(1));
            List<UserDataSet> users = dbService.readAll();
            System.out.println("All users: " + users.toString());
            System.out.println("Sully: " + dbService.readByName("sully"));
            dbService.shutdown();
        }
    }

    private DBService getDbService() {
        return
                new DBServiceImpl();
       // new DBServiceHibernateImpl();
    }

}
