package com.company;

public class Main {

    public static void main(String[] args) {
        final String URL = "jdbc:mysql://localhost/football";
        final String user = "root";
        final String password = "";
        String request = "Select * from players";

        DataBaseClass DB = new DataBaseClass(URL, user, password);
        DB.dataBaseConnection(request);

    }
}
