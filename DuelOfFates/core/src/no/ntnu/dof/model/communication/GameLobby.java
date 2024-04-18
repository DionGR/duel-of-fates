package no.ntnu.dof.model.communication;

import lombok.Data;

@Data
public class GameLobby {
    private String lobbyId;
    private User creator;
    private User guest; // This can be null if no guest has joined
    private String gameState; // e.g., waiting, started, finished
    private String title;
    private String gameId;

    public GameLobby() {
        // Default constructor for serialization
    }

    public GameLobby(User creator, String title) {
        this.lobbyId = null;
        this.creator = creator;
        this.gameState = "waiting";
        this.title = title;
        this.guest = null; // Initially, no guest
        this.gameId = null;
    }

    public GameLobby(String lobbyId, User creator, String title, String gameState, User guest, String gameId) {
        this.lobbyId = lobbyId;
        this.creator = creator;
        this.gameState = gameState;
        this.title = title;
        this.guest = guest;
        this.gameId = gameId;
    }
}

