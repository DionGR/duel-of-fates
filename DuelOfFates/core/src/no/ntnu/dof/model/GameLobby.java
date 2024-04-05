package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GameLobby {
    private String lobbyId;
    private User creator;
    private User guest; // This can be null if no guest has joined
    private String gameState; // e.g., waiting, started, finished
    private String title;

    public GameLobby() {
        // Default constructor for serialization
    }

    public GameLobby(String lobbyId, User creator, String gameState, String title) {
        this.lobbyId = lobbyId;
        this.creator = creator;
        this.gameState = gameState;
        this.title = title;
        this.guest = null; // Initially, no guest
    }

    public <E> GameLobby(String lobbyId, ArrayList<E> es, String waiting) {
    }

    // Getters and setters
}

