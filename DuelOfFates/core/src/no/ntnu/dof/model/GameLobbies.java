package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import no.ntnu.dof.controller.DuelOfFates;

@Data
public class GameLobbies {
    private List<GameLobby> lobbies;

    public GameLobbies() {
        this.lobbies = new ArrayList<>();
    }

    public void addLobby(GameLobby lobby) {
        lobbies.add(lobby);
    }
}

