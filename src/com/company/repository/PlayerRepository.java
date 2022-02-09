package com.company.repository;

import com.company.annotation.Column;
import com.company.annotation.MaxLength;
import com.company.model.FootballClub;
import com.company.model.Player;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
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

            String settingNamePlayer = player.getNameP();

            Field[] fields = Player.class.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                if (fields[i].isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = fields[i].getAnnotation(MaxLength.class);
                    int columnLength = maxLength.maxlength();
                    try {
                        if (fields[i].getGenericType() == String.class && settingNamePlayer.length() <= columnLength) {
                            preparedStatement.setString(1, settingNamePlayer);
                        } else {
                            throw new SQLException();
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage() + "The langth of player name is not match!");
                    }
                }
            }

            preparedStatement.setInt(2, player.getAge());
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


    public Optional<Player> getByID(int id) throws IllegalAccessException {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT p.id_p, p.name_p, p.age, p.id_fc, p.date_of_birth, " +
                "f.id_fc, f.name_fc, f.year_birth " +
                "FROM players p " +
                "INNER JOIN foot_clubs f on p.id_fc=f.id_fc " +
                "WHERE p.id_p=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapResultSetToPlayer(result));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return Optional.empty();
    }


    public Player updatePlayer(Player player) throws NoSuchFieldException, SQLException {

        String query =  "UPDATE  players SET name_p=?, age=?, id_fc=?, date_of_birth=? WHERE id_p=?";

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement(String.valueOf(query))) {

            Field field = Player.class.getDeclaredField("nameP");

            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                int columnLength = maxLength.maxlength();

                try {
                    if (field.getGenericType() == String.class && player.getNameP().length() <= columnLength) {
                        preparedStatement.setString(1, player.getNameP());
                    } else {
                        throw new SQLException();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "The langth of player name is not match!");
                }

            }
            preparedStatement.setInt(2, player.getAge());
            if (player.getFootballClub() != null) {
                preparedStatement.setInt(3, player.getFootballClub().getIdFc());
            }
            preparedStatement.setDate(4, Date.valueOf(player.getDateOfBirth()));
            preparedStatement.setInt(5, player.getIdP());

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Can't execute the update query!" + e.getMessage());
        }
        return player;
    }


    public List<Player> getListOfPlayers() throws IllegalAccessException {
        List<Player> listOfPlayers = new ArrayList<>();
        String query = "SELECT" + getAllColumns() + "f.id_fc, f.name_fc, f.year_birth FROM players p INNER JOIN foot_clubs f on p.id_fc=f.id_fc ";
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement(query)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Player player = new Player();
                player = mapResultSetToPlayer(result);
                listOfPlayers.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e + "Exception!!!!");
        }
        return listOfPlayers;
    }


    public List<Player> getListOfPlayersOfFootballClub(FootballClub footballClub) throws IllegalAccessException {
        List<Player> listOfPlayersOfFootballClub = new ArrayList<>();
        int idFootballClub = footballClub.getIdFc();
        String query;
        query = "SELECT " + getAllColumns() + " f.id_fc, f.name_fc, f.year_birth FROM players p INNER JOIN foot_clubs f on p.id_fc=f.id_fc WHERE f.id_fc=?";
        // System.out.println("QQ" + query);
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, idFootballClub);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Player player = new Player();
                player = mapResultSetToPlayer(result);
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

    private Player mapResultSetToPlayer(ResultSet result) throws SQLException, IllegalAccessException {
        Player player = new Player();
        for (Field field : Player.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                String columnName = column.value();
                Object value = null;
                if (field.getGenericType() == String.class) {
                    value = result.getString(columnName);
                } else if (field.getGenericType() == Integer.class) {
                    value = result.getInt(columnName);
                } else if (field.getGenericType() == LocalDate.class) {
                    value = Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                } else {
                    FootballClub footballClub = new FootballClub();
                    footballClub.setIdFc(result.getInt("id_fc"));
                    footballClub.setNameFc(result.getNString("name_fc"));
                    footballClub.setYearBirth(result.getInt("year_birth"));
                    value = footballClub;
                }
                field.set(player, value);
            }
        }
        return player;
    }

    public void s() {
        getAllColumns();
    }

    private String getAllColumns() {
        StringBuilder result = new StringBuilder();
        for (Field field : Player.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                String columnName = column.value();

                if (!columnName.equals("footballClub")) {
                    // System.out.println(columnName);
                    result.append(" p.");
                    result.append(columnName);
                    result.append(", ");

                }
            }
        }
        //result.deleteCharAt(result.length()-2);
        System.out.println(result);
        return result.toString();
    }

}
