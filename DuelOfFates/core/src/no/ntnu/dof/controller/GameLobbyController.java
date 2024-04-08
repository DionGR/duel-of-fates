package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.LobbyScreen;

public class GameLobbyController {

    private final DuelOfFates game;
    private final LobbyScreen lobbyScreen;
    private final GameLobby gameLobby;
    private boolean isDeletingLobby = false;

    public GameLobbyController(DuelOfFates game, LobbyScreen lobbyScreen, GameLobby gameLobby) {
        this.game = game;
        this.lobbyScreen = lobbyScreen;
        this.gameLobby = gameLobby;
        lobbyScreen.setController(this);
    }

    public void startGame() {
        // Logic to start the game.
        System.out.println("Starting game for lobby: " + gameLobby.getTitle());
    }

    public void joinLobby() {
        // Current user attempts to join the lobby
        User currentUser = game.getCurrentUser();
        ServiceLocator.getLobbyService().joinLobby(new LobbyService.LobbyJoinCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("LobbyJoin", "Successfully joined the lobby.");
                gameLobby.setGuest(currentUser);
                lobbyScreen.updateGuestInfo(currentUser.getEmail());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyJoin", "Failed to join the lobby.", throwable);
                lobbyScreen.showError("Failed to join the lobby.");
            }
        }, gameLobby, currentUser);
    }

    public void deleteLobby() {
        if (isDeletingLobby) return; // Prevent multiple deletion processes
        isDeletingLobby = true;

        ServiceLocator.getLobbyService().deleteLobby(gameLobby.getLobbyId(), new LobbyService.LobbyDeletionCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("LobbyDeletion", "Lobby successfully deleted.");
                isDeletingLobby = false; // Reset flag
                Gdx.app.postRunnable(() -> ScreenManager.transitionToLobbies());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyDeletion", "Failed to delete the lobby.", throwable);
                isDeletingLobby = false; // Reset flag
                Gdx.app.postRunnable(() -> lobbyScreen.showError("Failed to delete the lobby."));
            }
        });
    }
}
