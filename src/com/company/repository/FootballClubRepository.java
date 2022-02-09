package com.company.repository;

import com.company.annotation.Column;
import com.company.annotation.MaxLength;
import com.company.model.FootballClub;
import com.company.model.Player;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FootballClubRepository {

    private static FootballClubRepository instance;

    public static FootballClubRepository getInstance() {
        if (instance == null) {
            instance = new FootballClubRepository();
        }
        return instance;
    }


    public FootballClub addFootballClub(FootballClub footballClub) throws NoSuchFieldException {
        String listOfColumns = getAllColumns();
        listOfColumns = listOfColumns.replace("id_fc,", "");
        String query = "INSERT INTO foot_clubs  (" + listOfColumns + ") VALUES (" + setSignsOfQuestions(listOfColumns) + ")";
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Field field = Player.class.getDeclaredField("nameP");

            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                int columnLength = maxLength.maxlength();

                try {
                    if (field.getGenericType() == String.class && footballClub.getNameFc().length() <= columnLength) {
                        preparedStatement.setString(1, footballClub.getNameFc());
                    } else {
                        throw new SQLException();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "The langth of player name is not match!");
                }

            }

            preparedStatement.setInt(2, footballClub.getYearBirth());
            preparedStatement.execute();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    footballClub.setIdFc(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating player failed. No id_p obtained");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Football club can't create");
        }
        return footballClub;
    }


    public Optional<FootballClub> getByID(int id) throws IllegalAccessException {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id_fc,name_fc, year_birth FROM foot_clubs"
                + " WHERE id_fc=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapResultSetToFootballClub(result));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return Optional.empty();
    }


    public List<FootballClub> getFootballClubsMoreNPlayers(int n) throws IllegalAccessException {
        List<FootballClub> footballClubsList = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT count(p.id_fc),f.name_fc FROM players p right " +
                "join foot_clubs f on p.id_fc=f.id_fc GROUP BY p.id_fc HAVING count(p.id_fc)>?")) {
            preparedStatement.setInt(1, n);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                footballClubsList.add(mapResultSetToFootballClub(result));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Can't execute query");
        }
        return footballClubsList;

    }

    private FootballClub mapResultSetToFootballClub(ResultSet result) throws SQLException, IllegalAccessException {

        FootballClub footballClub = new FootballClub();

        for (Field field : FootballClub.class.getDeclaredFields()) {
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
                }
                field.set(footballClub, value);
            }

        }
        return footballClub;
    }


    private String getAllColumns() {
        StringBuilder allColumns = new StringBuilder();
        for (Field field : FootballClub.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.value();
                // System.out.println(columnName);
                allColumns.append(columnName);
                allColumns.append(", ");
            }
        }
        allColumns.deleteCharAt(allColumns.length() - 2);
        System.out.println(allColumns);
        return allColumns.toString();
    }

    private String setSignsOfQuestions(String allColumns) {
        StringBuilder stringOfQuestions = new StringBuilder();
        String[] stringOfColumns = allColumns.split(",");

        for (int i = 0; i < stringOfColumns.length; ++i) {
            stringOfQuestions.append("?, ");
        }

        stringOfQuestions.deleteCharAt(stringOfQuestions.length() - 2);
        System.out.println(stringOfQuestions);
        return stringOfQuestions.toString();

    }

//    public void s() {
//        String ss = getAllColumns();
//        setSignsOfQuestions(ss);
//    }

}

