package com.company.repository;

import com.company.model.FootballClub;

import java.sql.*;


public class FootballClubRepository implements AutoCloseable {
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
            preparedStatement.setString(1, footballClub.getName_fc());
            preparedStatement.setInt(2, footballClub.getYear_birth());
            preparedStatement.execute();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    footballClub.setId_fc(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating player failed. No id_p obtained");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Football club can't create");
        }
        return footballClub;
    }


    public FootballClub getByID(int id) {
        FootballClub footballClub = null;
        try (PreparedStatement preparedStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id_fc,name_fc, year_birth FROM foot_clubs"
                + " WHERE id_fc=?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                footballClub = new FootballClub();
                footballClub.setId_fc(result.getInt("id_fc"));
                footballClub.setName_fc(result.getNString("name_fc"));
                footballClub.setYear_birth(result.getInt("year_birth"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Not getById");
        }
        return footballClub;
    }


    @Override
    public void close() throws SQLException {
        ConnectionHolder.getConnection().close();
    }
}
