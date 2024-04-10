package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.lobby.LobbyScreen;
import no.ntnu.dof.controller.DuelOfFates;

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
        // Only start game if there is a guest
        if (gameLobby.getGuest() == null) {
            lobbyScreen.showError("A second player is required to start the game.");
            return;
        }

        String hostPlayerClass = gameLobby.getCreator().getPlayerClassName();
        String guestPlayerClass = gameLobby.getGuest().getPlayerClassName();

        // Logic to start the game...
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
                Gdx.app.postRunnable(ScreenController::popScreen);
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
                isDeletingLobby = false; // Reset flags
                Gdx.app.postRunnable(ScreenController::popScreen);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyDeletion", "Failed to delete the lobby.", throwable);
                isDeletingLobby = false; // Reset flag
                Gdx.app.postRunnable(() -> lobbyScreen.showError("Failed to delete the lobby."));
            }
        });
    }

    public void exitLobby() {
        ServiceLocator.getLobbyService().guestExitLobby(new LobbyService.LobbyExitCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("LobbyExit", "Successfully exited the lobby.");
                lobbyScreen.updateGuestInfo("<Available>");
                Gdx.app.postRunnable(ScreenController::popScreen);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyExit", "Failed to exit the lobby", throwable);
                lobbyScreen.showError("Failed to exit the lobby.");
            }
        }, gameLobby);
    }
}
