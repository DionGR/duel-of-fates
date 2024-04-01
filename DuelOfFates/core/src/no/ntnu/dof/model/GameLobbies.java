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
        lobbies.add(new GameLobby("1", new User("test2", "obiwan@gmail.com"), "waiting", "Epic Match"));
        lobbies.add(new GameLobby("2", new User("testID", "darthmaul@gmail.com"), "waiting", "Phantom Menace"));
    }
}

