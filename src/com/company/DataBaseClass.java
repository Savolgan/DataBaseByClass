package com.company;

import java.sql.*;

public class DataBaseClass<statement> {
    private String URl;
    private String user;
    private String password;

    DataBaseClass(String URl, String user, String password) {
        this.URl = URl;
        this.user = user;
        this.password = password;
    }

    public void dataBaseConnection(String request) {

        try (Connection connect = DriverManager.getConnection(URl, user, password)) {
            try (Statement statement = connect.createStatement()) {
                try (ResultSet result = statement.executeQuery(request)) {
                    resultOfQuary(result);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        } catch (SQLException e) {
            System.out.println("Can't connect!!!");
        }

    }

    public void resultOfQuary(ResultSet result) {
        try {
            while (result.next()) {
                StringBuilder row = new StringBuilder();
                int id = result.getInt("id_p");
                row.append(id);
                row.append(" ");
                String name = result.getString("name_p");
                int age = result.getInt("age");
                row.append(name);
                row.append(" ");
                row.append(age);
                System.out.println(row);
            }
        } catch (SQLException e) {
           System.out.println("Can't show results of your quary!");
        }
    }


}
