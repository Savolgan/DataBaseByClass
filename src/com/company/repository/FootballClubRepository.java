package com.company.repository;

import com.company.model.FootballClub;
import com.company.model.Player;

import java.sql.*;
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


    public FootballClub addFootballClub(FootballClub footballClub) {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("INSERT INTO foot_clubs  " +
                " ( name_fc,year_birth) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, footballClub.getNameFc());
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


    public Optional<FootballClub> getByID(int id) {

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id_fc,name_fc, year_birth FROM foot_clubs"
                + " WHERE id_fc=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                FootballClub footballClub = null;
                footballClub = new FootballClub();
                footballClub.setIdFc(result.getInt("id_fc"));
                footballClub.setNameFc(result.getNString("name_fc"));
                footballClub.setYearBirth(result.getInt("year_birth"));
                return Optional.of(footballClub);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return Optional.empty();
    }

    public List<FootballClub> getFootballClubsMoreNPlayers(int n) {
        List<FootballClub> footballClubsList = new ArrayList<>();
        int countOfPlayers = 0;

        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT count(p.id_fc),f.name_fc FROM players p right join foot_clubs f on p.id_fc=f.id_fc GROUP BY p.id_fc")) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                if (result.getInt(1) > n) {
                    FootballClub footballClub = null;
                    footballClub = new FootballClub();
                    footballClub.setNameFc(result.getNString("name_fc"));
                    footballClubsList.add(footballClub);
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }

       /* try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT * FROM foot_clubs")) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {

                try (PreparedStatement preparedStatement1 = ConnectionHolder.getConnection().prepareStatement("SELECT COUNT( id_fc )" +
                        "FROM players WHERE id_fc =?")) {
                    preparedStatement1.setInt(1, result.getInt(1));
                    ResultSet result1 = preparedStatement1.executeQuery();
                    while (result1.next()) {
                        countOfPlayers = result1.getInt(1);

                        if(countOfPlayers>n){

                            FootballClub footballClub = null;
                            footballClub = new FootballClub();
                            footballClub.setIdFc(result.getInt("id_fc"));
                            footballClub.setNameFc(result.getNString("name_fc"));
                            footballClub.setYearBirth(result.getInt("year_birth"));
                            footballClubsList.add(footballClub);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        return footballClubsList;
    }
}
