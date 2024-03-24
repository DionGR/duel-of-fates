package no.ntnu.dof.model;

import java.util.List;

import lombok.Data;

// BASIC AND UNFINISHED GAME LOBBY CLASS
@Data
public class GameLobby {
    private String lobbyId;
    private List<String> players;
    private String gameState; // e.g., waiting, started, finished

    public GameLobby() {
        // Default constructor required for calls to DataSnapshot.getValue(GameLobby.class)
    }

    public GameLobby(String lobbyId, List<String> players, String gameState) {
        this.lobbyId = lobbyId;
        this.players = players;
        this.gameState = gameState;
    }

    // Getters and setters
}

