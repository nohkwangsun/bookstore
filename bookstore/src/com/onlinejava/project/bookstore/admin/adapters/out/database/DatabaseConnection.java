package com.onlinejava.project.bookstore.admin.adapters.out.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:h2:./database/data";
    private static final String user = "sa";
    private static final String password = "";
    private static Connection connection;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
            }
            return connection;
        } catch (SQLException e) {
            String message = String.format("Failed to get a database connection : %s, (%s/%s)", url, user, password.replaceAll(".", "*"));
            throw new DatabaseException(message, e);
        }
    }

    public static synchronized void releaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close the database connection", e);
        }
    }
}
