package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        PlayerRepository DBClass = PlayerRepository.getInstance();
        DBClass.addPlayer("IdeaName", 33, 3);
       DBClass.updatePlayer(2,"NewIdeaName",11,1);
    }
}
