package no.ntnu.dof.model;

import java.util.List;

import lombok.Data;
import lombok.Setter;

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

    public GameLobby(User creator, String title) {
        this.lobbyId = null;
        this.creator = creator;
        this.gameState = "waiting";
        this.title = title;
        this.guest = null; // Initially, no guest
    }

    public GameLobby(String lobbyId, User creator, String title, String gameState, User guest) {
        this.lobbyId = lobbyId;
        this.creator = creator;
        this.gameState = gameState;
        this.title = title;
        this.guest = guest;
    }
}

