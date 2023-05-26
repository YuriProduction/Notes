package urfu.OOP.BackEnd.Patterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final int INITIAL_POOL_SIZE = 5;
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/notes";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "admin";

    private final List<Connection> pool;

    public ConnectionPool() {
        pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating database connection", e);
        }
    }

    public synchronized Connection getConnection() {
        if (pool.isEmpty()) {
            pool.add(createConnection());
        }
        return pool.remove(0);
    }

    public synchronized void releaseConnection(Connection connection) {
        pool.add(connection);
    }

    public synchronized void closeAllConnections() {
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore exception
            }
        }
        pool.clear();
    }
}


