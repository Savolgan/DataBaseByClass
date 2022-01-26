package com.company.service;

import com.company.model.Player;
import com.company.repository.PlayerRepository;

import java.sql.SQLException;

public class PlayerService {

    private static PlayerService instance;

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public Player savePlayer(Player player) throws SQLException {
        return PlayerRepository.getInstance().addPlayer(player);
    }
}
