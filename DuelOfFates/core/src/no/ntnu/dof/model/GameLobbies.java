package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.controller.DuelOfFates;

public class GameLobbies {
    private final List<GameLobby> lobbies;
    private DuelOfFates game;

    public GameLobbies(DuelOfFates game) {
        this.lobbies = new ArrayList<>();
        this.game = game;
        // Mock some data
        lobbies.add(new GameLobby("1", game.getCurrentUser(), "waiting"));
        lobbies.add(new GameLobby("2", new User("testID", ""), "started"));
    }
}

