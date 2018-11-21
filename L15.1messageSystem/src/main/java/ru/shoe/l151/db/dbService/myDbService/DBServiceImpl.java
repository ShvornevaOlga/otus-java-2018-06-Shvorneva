package ru.shoe.l151.db.dbService.myDbService;

import org.reflections.Reflections;
import ru.shoe.l151.db.base.DBService;
import ru.shoe.l151.db.base.DataSet;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.db.dbService.myDbService.dao.UserDataSetDAO;
import ru.shoe.l151.db.executor.DBServiceException;
import ru.shoe.l151.db.executor.TExecutor;
import ru.shoe.l151.db.tree.QueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DBServiceImpl implements DBService {
    private Set<Class<? extends DataSet>> dataSetClasses = (new Reflections("ru.shoe.l111hibernate.base.datasets")).getSubTypesOf(DataSet.class);

    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
        dropTables();
        prepareTables();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private void prepareTables() {
        TExecutor exec = new TExecutor(getConnection());
        for (Class clazz : dataSetClasses) {
            try {
                exec.execUpdate(QueryBuilder.createTable(clazz), PreparedStatement::execute);
            } catch (DBServiceException e) {
                System.err.println("Can not create Tables");
                e.printStackTrace();
            }
        }
        System.out.println("Tables created");
    }

    @Override
    public List<UserDataSet> readAll() {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(getConnection());
        return userDataSetDAO.readAll();
    }

    @Override
    public void shutdown() {

    }

    private void dropTables(){
        TExecutor exec = new TExecutor(getConnection());
        for (Class clazz : dataSetClasses) {
            try {
                exec.execUpdate(QueryBuilder.dropTable(clazz), PreparedStatement::execute);
            } catch (DBServiceException e) {
                System.err.println("Can not drop Tables");
                e.printStackTrace();
            }
        }
        System.out.println("Table dropped");
    }

   @Override
    public void save(UserDataSet dataSet) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(getConnection());
        userDataSetDAO.save(dataSet);
    }

     @Override
    public UserDataSet read(long id) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(getConnection());
        return userDataSetDAO.read(id);
    }

    @Override
    public UserDataSet readByName(String name) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(getConnection());
        return userDataSetDAO.readByName(name);
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    private Connection getConnection() {
        return connection;
    }

}
