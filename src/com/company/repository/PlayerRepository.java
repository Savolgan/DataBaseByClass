package com.company.repository;

import com.company.model.FootballClub;
import com.company.model.Player;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepository {

    private static PlayerRepository instance;

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }


    public Player addPlayer(Player player, FootballClub footballClub) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("INSERT INTO players  " +
                " ( name_p, age, id_fc, date_of_birth) VALUES (?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, player.getNameP());
            preparedStatement.setInt(2, player.getAge());
            // preparedStatement.setInt(3, player.getFootballClub() != null ? player.getFootballClub().getIdFc() : 1);
            preparedStatement.setInt(3, footballClub.getIdFc());
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


    public Optional<Player> getByID(int id) {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT p.id_p, p.name_p, p.age, p.id_fc, p.date_of_birth, " +
                "f.id_fc, f.name_fc, f.year_birth " +
                "FROM players p " +
                "INNER JOIN foot_clubs f on p.id_fc=f.id_fc " +
                "WHERE p.id_p=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                Player player = new Player();
                player.setIdP(result.getInt("id_p"));
                player.setNameP(result.getString("name_p"));
                player.setAge(result.getInt("age"));
                player.setDateOfBirth(Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

                if (result.getString("id_fc") != null) {
                    FootballClub footballClub = new FootballClub();
                    footballClub.setIdFc(result.getInt("id_fc"));
                    footballClub.setNameFc(result.getNString("name_fc"));
                    footballClub.setYearBirth(result.getInt("year_birth"));
                    player.setFootballClub(footballClub);
                }
                return Optional.of(player);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return Optional.empty();
    }


    public Player updatePlayer(Player player) {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("UPDATE  players SET name_p=?, age=?, id_fc=? " +
                "WHERE id_p=?")) {
            preparedStatement.setString(1, player.getNameP());
            preparedStatement.setInt(2, player.getAge());
            if (player.getFootballClub() != null) {
                preparedStatement.setInt(3, player.getFootballClub().getIdFc());
            }
            preparedStatement.setInt(4, player.getIdP());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Can't execute the update query!" + e.getMessage());
        }
        return player;
    }

    public List<Player> getListOfPlayers() {
        List<Player> listOfPlayers = new ArrayList<>();
        try (ResultSet resultSet = ConnectionHolder.getConnection().createStatement().executeQuery("SELECT * FROM players")) {
            while (resultSet.next()) {
                Player player = new Player();
                player.setIdP(resultSet.getInt("id_p"));
                player.setNameP(resultSet.getString("name_p"));
                player.setAge(resultSet.getInt("age"));

                if (resultSet.getDate("date_of_birth") != null) {
                    player.setDateOfBirth(Instant.ofEpochMilli(resultSet.getDate("date_of_birth").getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                } else player.setDateOfBirth(null);

                if (resultSet.getString("id_fc") != null) {
                    Optional<FootballClub> optionalFootballClub = FootballClubRepository.getInstance().getByID(resultSet.getInt("id_fc"));
                    if (optionalFootballClub.isPresent()) {
                        player.setFootballClub(optionalFootballClub.get());
                    }

                }
                listOfPlayers.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listOfPlayers;
    }


    public List<Player> getListOfPlayersOfFootballClub(FootballClub footballClub) {
        List<Player> listOfPlayersOfFootballClub = new ArrayList<>();
        int idFootballClub = footballClub.getIdFc();

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT * FROM players WHERE id_fc=?")) {
            preparedStatement.setInt(1, idFootballClub);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Player player = new Player();
                player.setIdP(result.getInt("id_p"));
                player.setNameP(result.getString("name_p"));
                player.setAge(result.getInt("age"));

                if (result.getDate("date_of_birth") != null) {
                    player.setDateOfBirth(Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                } else player.setDateOfBirth(null);

                if (result.getString("id_fc") != null) {
                    Optional<FootballClub> optionalFootballClub = FootballClubRepository.getInstance().getByID(result.getInt("id_fc"));
                    if (optionalFootballClub.isPresent()) {
                        player.setFootballClub(optionalFootballClub.get());
                    }
                }
                listOfPlayersOfFootballClub.add(player);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfPlayersOfFootballClub;
    }

    public int getCountOfPlayersOfFootballClub(FootballClub footballClub) {
        int countOfPlayers = 0;
        int idFootballClub = footballClub.getIdFc();

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT COUNT( id_fc )" +
                "FROM players WHERE id_fc =?")) {
            preparedStatement.setInt(1, idFootballClub);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                countOfPlayers = result.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countOfPlayers;
    }

}
