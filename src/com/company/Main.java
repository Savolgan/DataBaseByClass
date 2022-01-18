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
        Player player = PlayerRepository.getInstance().getByID(1);
        System.out.println(player);
        updatePlayer();

        // createFootballClub();
        FootballClub footballClub = FootballClubRepository.getInstance().getByID(1);
        System.out.println(footballClub);


    }

    private static void createPlayer() throws SQLException {
        Player player = new Player();
        player.setNameP("Oooo");
        player.setAge(55);
        player.setDateOfBirth(LocalDate.of(1966, 10, 16));
        PlayerRepository.getInstance().addPlayer(player);
    }

    private static void createFootballClub() {
        FootballClub footballClub = new FootballClub();
        footballClub.setNameFc("New");
        footballClub.setYearBirth(5);
        FootballClubRepository.getInstance().addFootballClub(footballClub);
    }

    private static void updatePlayer() {
        Player player = new Player();
        player.setNameP("Oooo111");
        player.setAge(55);
        player.setIdP(1);
        player.setIdFootballClub(2);
        player.setDateOfBirth(LocalDate.of(1966, 10, 16));
        PlayerRepository.getInstance().updatePlayer(player);
    }

}
