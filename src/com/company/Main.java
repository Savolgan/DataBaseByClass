package com.company;

import com.company.model.FootballClub;
import com.company.model.Player;
import com.company.repository.ConnectionHolder;
import com.company.repository.FootballClubRepository;
import com.company.repository.PlayerRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchFieldException {

//        Optional<FootballClub> footballClub = FootballClubRepository.getInstance().getByID(3);
//        System.out.println(footballClub);
//
//        Optional<Player> player = PlayerRepository.getInstance().getByID(5);
//        System.out.println(player);

//        List<Player> players = new ArrayList<>();
//        players = PlayerRepository.getInstance().getListOfPlayers();
//        System.out.println(players);

//       List<Player> playersOfFootClub = new ArrayList<>();
//        Optional<FootballClub> footballClub=FootballClubRepository.getInstance().getByID(3);
//        playersOfFootClub=PlayerRepository.getInstance().getListOfPlayersOfFootballClub(footballClub.get());
//       System.out.println(playersOfFootClub);

        // System.out.println(PlayerRepository.getInstance().getCountOfPlayersOfFootballClub(footballClub.get()));

        //insertPlayer();

         updateSomeFieldsPlayer();



    }

    private static void updateSomeFieldsPlayer() throws SQLException, NoSuchFieldException {
        Player player = new Player();
        player.setNameP("Player InsertAnnot");
        player.setAge(25);
        player.setIdP(4);
        player.setIdFootballClub(2);
        FootballClub footballClub = new FootballClub();
        footballClub.setIdFc(3);
        player.setFootballClub(footballClub);
        player.setDateOfBirth(LocalDate.of(1970, 10, 25));
        PlayerRepository.getInstance().updatePlayer(player);
    }

    private static void insertPlayer() throws IllegalAccessException, SQLException {
        Player player = new Player();
        player.setNameP("Annot Player");
        player.setAge(55);
        player.setIdP(4);
        player.setIdFootballClub(2);
        FootballClub footballClub = new FootballClub();
        footballClub.setIdFc(3);
        player.setFootballClub(footballClub);
        player.setDateOfBirth(LocalDate.of(1975, 10, 25));

        Optional<FootballClub> footballClub1 = FootballClubRepository.getInstance().getByID(3);
        PlayerRepository.getInstance().addPlayer(player, footballClub1.get());
    }


}
