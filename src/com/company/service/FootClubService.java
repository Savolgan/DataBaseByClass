package com.company.service;

import com.company.model.FootballClub;
import com.company.model.Player;
import com.company.repository.FootballClubRepository;
import com.company.repository.PlayerRepository;

import java.sql.SQLException;

public class FootClubService {


    private static FootClubService instance;

    private FootClubService() {
    }

    public static FootClubService getInstance() {
        if (instance == null) {
            instance = new FootClubService();
        }
        return instance;
    }

    public FootballClub saveFootClub(FootballClub footballClub) throws SQLException {
        return FootballClubRepository.getInstance().addFootballClub(footballClub);
    }
}