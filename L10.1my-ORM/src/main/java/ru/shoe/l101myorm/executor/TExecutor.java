package ru.shoe.l101myorm.executor;

import java.sql.*;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ExecuteHandler prepare, TResultHandler<T> handler) {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)){
            prepare.accept(stmt);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(update);
            prepare.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
