package ru.shoe.l101myorm.connection;

import org.reflections.Reflections;
import ru.shoe.l101myorm.base.DataSet;
import ru.shoe.l101myorm.executor.DBServiceException;
import ru.shoe.l101myorm.executor.LoadHandler;
import ru.shoe.l101myorm.executor.TExecutor;
import ru.shoe.l101myorm.tree.QueryBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class DBServiceConnection implements DBService {
    private Set<Class<? extends DataSet>> dataSetClasses = (new Reflections("ru.shoe.l101myorm.base")).getSubTypesOf(DataSet.class);

    private final Connection connection;

    public DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
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

    @Override
    public void prepareTables() {
        TExecutor exec = new TExecutor(getConnection());
        for (Class clazz : dataSetClasses) {
            try {
                exec.execUpdate(QueryBuilder.createTable(clazz), PreparedStatement::execute);
            } catch (DBServiceException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Tables created");
    }

    public <T extends DataSet> Collection<T> getAllUsers(Class<T> clazz) {
        TExecutor executor = new TExecutor(getConnection());
        String query = String.format("select * from %s", clazz.getSimpleName());
        try {
            return executor.execQueryList(query, PreparedStatement::execute, new LoadHandler<>(clazz));
        } catch (DBServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteTables() {
        TExecutor exec = new TExecutor(getConnection());
        for (Class clazz : dataSetClasses) {
            try {
                exec.execUpdate(QueryBuilder.dropTable(clazz), PreparedStatement::execute);
            } catch (DBServiceException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Table dropped");
    }

    @Override
    public <T extends DataSet> void save(T user) {
        TExecutor exec = new TExecutor(getConnection());
        try {
            exec.execUpdate(QueryBuilder.insertDataSet(user), statement -> {
                QueryBuilder.acceptStatment(user, statement);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    try {
                        Field field = user.getClass().getSuperclass().getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(user, rs.getLong(1));
                        field.setAccessible(false);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (DBServiceException e) {
            System.err.println("Can not save " + user);
            e.printStackTrace();
        }
        System.out.println("User added");
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        TExecutor executor = new TExecutor(getConnection());
        String query = String.format("select * from %s where id = ?", clazz.getSimpleName());
        try {
            return executor.execQuery(query,statement -> {
                    statement.setLong(1, id);
                statement.execute();
            }, new LoadHandler<>(clazz));
        } catch (DBServiceException e) {
            e.printStackTrace();
            return null;
        }
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
