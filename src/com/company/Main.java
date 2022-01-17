package com.company;

import com.company.model.FootballClub;
import com.company.model.Player;
import com.company.repository.FootballClubRepository;
import com.company.repository.PlayerRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException {
        //createPlayer();
        Player player = PlayerRepository.getInstance().getByID(3);
        System.out.println(player);

       // createFootballClub();
        FootballClub footballClub = FootballClubRepository.getInstance().getByID(1);
        System.out.println(footballClub);
    }

    private static void createPlayer() throws SQLException {
        Player player = new Player();
        player.setName_p("Oooo");
        player.setAge(55);
        player.setId_fc(5);
        player.setDateOfBirth(LocalDate.of(1966, 10, 16));

        PlayerRepository.getInstance().addPlayer(player);
    }

    private static void createFootballClub() {
        FootballClub footballClub = new FootballClub();
        footballClub.setName_fc("New");
        footballClub.setYear_birth(5);
        FootballClubRepository.getInstance().addFootballClub(footballClub);
    }


}
