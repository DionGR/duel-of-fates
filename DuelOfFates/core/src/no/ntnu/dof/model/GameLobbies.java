package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import no.ntnu.dof.controller.DuelOfFates;

@Data
public class GameLobbies {
    private final List<GameLobby> lobbies;

    public GameLobbies() {
        this.lobbies = new ArrayList<>();
        // Mock some data
        lobbies.add(new GameLobby(new User("test2", "obiwan@gmail.com"), "Epic Match"));
        lobbies.add(new GameLobby(new User("testID", "darthmaul@gmail.com"), "Phantom Menace"));
    }

    public void addLobby(GameLobby lobby) {
        lobbies.add(lobby);
    }
}

