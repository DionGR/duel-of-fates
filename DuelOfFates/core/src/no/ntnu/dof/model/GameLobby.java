package no.ntnu.dof.model;

import java.util.List;

import lombok.Data;

@Data
public class GameLobby {
    private String lobbyId;
    private User creator;
    private User guest; // This can be null if no guest has joined
    private String gameState; // e.g., waiting, started, finished

    public GameLobby() {
        // Default constructor for serialization
    }

    public GameLobby(String lobbyId, User creator, String gameState) {
        this.lobbyId = lobbyId;
        this.creator = creator;
        this.gameState = gameState;
        this.guest = null; // Initially, no guest
    }

    // Getters and setters
}

