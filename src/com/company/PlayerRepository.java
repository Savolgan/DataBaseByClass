package com.company;

import java.sql.*;

public class PlayerRepository implements AutoCloseable {
    private static final String URl = "jdbc:mysql://localhost/football";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    private static PlayerRepository instance;

    private PlayerRepository() {
        try {
            connection = DriverManager.getConnection(URl, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(String name_p, Integer age, int id_fc) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players  " +
                " ( name_p, age, id_fc) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name_p);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, id_fc);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }

    public void updatePlayer(int id, String name, int age, int id_fc) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  players SET name_p=?, age=?, id_fc=? " +
                "WHERE id_p=?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, id_fc);
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Can't execute the update query!" + e.getMessage());
        }

    }
    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
