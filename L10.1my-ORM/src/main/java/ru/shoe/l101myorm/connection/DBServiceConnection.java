package ru.shoe.l101myorm.connection;

import org.reflections.Reflections;
import ru.shoe.l101myorm.base.DataSet;
import ru.shoe.l101myorm.base.UsersDataSet;
import ru.shoe.l101myorm.executor.LoadHandler;
import ru.shoe.l101myorm.executor.TExecutor;
import ru.shoe.l101myorm.tree.QueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
            exec.execUpdate(QueryBuilder.createTable(clazz), PreparedStatement::execute);
        }
        System.out.println("Tables created");
    }

    public <T extends DataSet> Collection<T> getAllUsers(Class<T> clazz) {
        TExecutor executor = new TExecutor(getConnection());
        String query = String.format("select * from %s", clazz.getSimpleName());
        return executor.execQueryList(query, PreparedStatement::execute, new LoadHandler<>(clazz));
    }

    /*@Override
    public <T extends DataSet> Collection<T> getAllUsers(Class<T> clazz) {
        TExecutor executor = new TExecutor(getConnection());
        String query = String.format("select * from %s", clazz.getSimpleName());
        return executor.execQueryList(query, PreparedStatement::execute, new LoadHandler<>(clazz));
       *//* return executor.execQueryList("select * from usersdataset",PreparedStatement::execute, result -> {
            List<UsersDataSet> users = new ArrayList<>();
            while (!result.isLast()) {
                result.next();
                int age = result.getInt("age");
                String name = result.getString("name");
                long id = result.getLong("id");
                UsersDataSet user = new UsersDataSet(name, age);
                user.setId(id);
                users.add(user);
            }
            return users;
        });*//*
    }*/

    @Override
    public void deleteTables() {
        TExecutor exec = new TExecutor(getConnection());
        for (Class clazz : dataSetClasses) {
            exec.execUpdate(QueryBuilder.dropTable(clazz), PreparedStatement::execute);
        }
        System.out.println("Table dropped");
    }

    @Override
    public <T extends DataSet> void save(T user) {
        TExecutor exec = new TExecutor(getConnection());
        exec.execUpdate(QueryBuilder.insertDataSet(user), statement -> {
            QueryBuilder.acceptStatment(user, statement);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
        });
        System.out.println("User added");
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        TExecutor executor = new TExecutor(getConnection());
        String query = String.format("select * from %s where id = ?", clazz.getSimpleName());
        return executor.execQuery(query,statement -> {
                statement.setLong(1, id);
            statement.execute();
        }, new LoadHandler<>(clazz));
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
