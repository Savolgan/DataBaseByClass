package com.company;

import java.sql.*;

public class DataBaseClass implements AutoCloseable {
    private static final String URl = "jdbc:mysql://localhost/football";
    ;
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private String password;
    private Connection connection;

    private static DataBaseClass instance;

    private DataBaseClass() {
        try {
            connection = DriverManager.getConnection(URl, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(int id, String name_p, Integer age, int id_fc) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players  " +
                " (id_p, name_p, age, id_fc) VALUES (?, ?, ?,?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name_p);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, id_fc);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataBaseClass getInstance() {
        if (instance == null) {
            instance = new DataBaseClass();
        }
        return instance;
    }

    public void updatePlayer(int id, int id_fc) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  players SET id_fc=? WHERE id_p=?")) {
            preparedStatement.setInt(1, id_fc);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Can't execute the update query!"+e.getMessage());
        }

    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
