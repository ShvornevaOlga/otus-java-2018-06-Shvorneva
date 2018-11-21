package dbService.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.shoe.l151.db.base.DBService;
import ru.shoe.l151.db.base.datasets.AdressDataSet;
import ru.shoe.l151.db.base.datasets.PhoneDataSet;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.db.dbService.hibernate.DBServiceHibernateImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;


public class DBServiceHibernateImplTest {
    private DBService service;
    private final SessionFactory sessionFactory;
    private UserDataSet user1;
    private UserDataSet user2;

    public DBServiceHibernateImplTest() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AdressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");

        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
        service = new DBServiceHibernateImpl();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Before
    public void setUp() {
        PhoneDataSet phone1 = new PhoneDataSet("12345");
        PhoneDataSet phone2 = new PhoneDataSet("456789");
        AdressDataSet adress = new AdressDataSet("street1");
        user1 = new UserDataSet("tully", 21, adress);
        user1.addPhone(phone1);
        user1.addPhone(phone2);
        PhoneDataSet phone3 = new PhoneDataSet("12345");
        AdressDataSet adress1 = new AdressDataSet("street2");
        user2 = new UserDataSet("sully", 33, adress1);
        user2.addPhone(phone3);
    }

    @After
    public void tearDown() {
        service.shutdown();
        sessionFactory.close();
    }

    @Test
    public void getMetaData() {
        Session session = sessionFactory.openSession();
        Connection connection = ((SessionImpl) session).connection();
        String metaData = "";
        try {
            metaData = "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        assertEquals(metaData, service.getMetaData());
    }

    @Test
    public void save() {

        service.save(user1);
        UserDataSet userDataSet = runInSession(session -> session.load(UserDataSet.class, user1.getId()));
        assertEquals(user1.getAdress(), userDataSet.getAdress());
        assertEquals(user1.getAge(), userDataSet.getAge());
        assertEquals(user1.getName(), userDataSet.getName());
        assertEquals(user1.getPhones(), userDataSet.getPhones());
        assertEquals(user1.getAdress(), userDataSet.getAdress());
    }

    @Test
    public void read() {
        sessionFactory.openSession().save(user1);
        assertEquals(user1, service.read(user1.getId()));
    }

    @Test
    public void readByName() {
        runInSession(session -> session.save(user1));
        assertEquals(user1, service.readByName(user1.getName()));
    }

    @Test
    public void readAll() {
        runInSession(session -> session.save(user1));
        runInSession(session -> session.save(user2));
        List<UserDataSet> allUser = new ArrayList<>();
        allUser.add(user1);
        allUser.add(user2);
        assertEquals(allUser, service.readAll());
    }
}