package no.ntnu.dof.model.communication;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

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

