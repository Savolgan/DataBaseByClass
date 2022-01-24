package com.company;

import com.company.model.FootballClub;
import com.company.model.Player;
import com.company.repository.ConnectionHolder;
import com.company.repository.FootballClubRepository;
import com.company.repository.PlayerRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Get player by his id:
        try {
            ConnectionHolder.getConnection().setAutoCommit(false);
            Optional<Player> player = PlayerRepository.getInstance().getByID(5);

            ConnectionHolder.getConnection().commit();
            System.out.println(player);

        } catch (Exception e) {
            ConnectionHolder.getConnection().rollback();
        } finally {
            ConnectionHolder.getConnection().setAutoCommit(true);
        }

        // Create football club, then create player with football club id:
        try {
            ConnectionHolder.getConnection().setAutoCommit(false);

            FootballClub footballClub = new FootballClub();
            footballClub.setNameFc("Nnnnnn");
            footballClub.setYearBirth(2015);
            FootballClubRepository.getInstance().addFootballClub(footballClub);

            Player player = new Player();
            player.setNameP("Nnnnnnnn");
            player.setAge(35);
            player.setDateOfBirth(LocalDate.of(1997, 10, 16));
            PlayerRepository.getInstance().addPlayer(player, footballClub);

            ConnectionHolder.getConnection().commit();
        } catch (Exception e) {
            ConnectionHolder.getConnection().rollback();
        } finally {
            ConnectionHolder.getConnection().setAutoCommit(true);
        }

        // Get all players:
        System.out.println("All players");
        for (Player player : PlayerRepository.getInstance().getListOfPlayers()) {
            System.out.println(player);
        }

        // Get all players of specific football club:
        System.out.println("________________________________________________________________-------");
        Optional<FootballClub> footballClub = FootballClubRepository.getInstance().getByID(3);
        FootballClub footballClub2= footballClub.get();
        System.out.println("All players of football club " + footballClub2);
        for (Player player : PlayerRepository.getInstance().getListOfPlayersOfFootballClub(footballClub2)) {
            System.out.println(player);
        }

        System.out.println("________________________________________________________________-------");
        System.out.println("Count of players of football club "+footballClub+" = "+PlayerRepository.getInstance().getCountOfPlayersOfFootballClub(footballClub2));


        List<FootballClub> footballClubs = FootballClubRepository.getInstance().getFootballClubsMoreNPlayers(2);
        System.out.println("_________________Football clubs that have players > n");
        System.out.println(footballClubs);
//        updatePlayer();
//        Optional<FootballClub> footballClub = FootballClubRepository.getInstance().getByID(1);
//        System.out.println(footballClub);
    }

    private static void updatePlayer() {
        Player player = new Player();
        player.setNameP("Update_player");
        player.setAge(55);
        player.setIdP(4);
        player.setIdFootballClub(2);
        FootballClub footballClub = new FootballClub();
        footballClub.setIdFc(3);
        player.setFootballClub(footballClub);
        player.setDateOfBirth(LocalDate.of(1966, 10, 16));
        PlayerRepository.getInstance().updatePlayer(player);
    }

}
