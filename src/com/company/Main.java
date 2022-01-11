package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        DataBaseClass DBClass = DataBaseClass.getInstance();
        //DBClass.addPlayer(11, "IdeaName2", 33, 3);
        DBClass.updatePlayer(2,5);
    }
}
