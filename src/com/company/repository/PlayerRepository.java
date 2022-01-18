package com.company.repository;

import com.company.model.Player;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;

public class PlayerRepository {

    private static PlayerRepository instance;

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }


    public Player addPlayer(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("INSERT INTO players  " +
                " ( name_p, age, id_fc, date_of_birth) VALUES (?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, player.getNameP());
            preparedStatement.setInt(2, player.getAge());
            preparedStatement.setInt(3, player.getFootballClub().getIdFc());
            preparedStatement.setDate(4, Date.valueOf(player.getDateOfBirth()));
            preparedStatement.execute();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.setIdP(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating player failed. No id_p obtained");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "can't create");
        }
        return player;
    }


    public Player getByID(int id) {
        Player player = null;
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id_p, name_p, age, id_fc, date_of_birth FROM players"
                + " WHERE id_p=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                player = new Player();
                player.setIdP(result.getInt("id_p"));
                player.setNameP(result.getNString("name_p"));
                player.setAge(result.getInt("age"));

                player.setDateOfBirth(Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                player.setFootballClub(FootballClubRepository.getInstance().getByID(result.getInt("id_fc")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return player;
    }


    public Player updatePlayer(Player player) {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("UPDATE  players SET name_p=?, age=?, id_fc=? " +
                "WHERE id_p=?")) {
            preparedStatement.setString(1, player.getNameP());
            preparedStatement.setInt(2, player.getAge());
            preparedStatement.setInt(3, player.getIdFootballClub());
            preparedStatement.setInt(4, player.getIdP());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Can't execute the update query!" + e.getMessage());
        }
        return player;
    }

}
