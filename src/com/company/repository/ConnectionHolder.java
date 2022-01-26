package com.company.repository;

import java.sql.Connection;
import java.sql.*;

public class ConnectionHolder implements AutoCloseable {
    private static final String URl = "jdbc:mysql://localhost/football";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private ConnectionHolder() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URl, USER, PASSWORD);
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
