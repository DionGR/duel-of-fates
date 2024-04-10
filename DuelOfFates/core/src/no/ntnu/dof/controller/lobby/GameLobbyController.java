package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import javax.inject.Inject;
import javax.inject.Named;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.gameplay.di.DaggerGameLobbyControllerComponent;
import no.ntnu.dof.controller.gameplay.di.GameLobbyControllerComponent;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClassInvoker;
import no.ntnu.dof.view.screens.lobby.LobbyScreen;

public class GameLobbyController implements LobbyViewListener {

    private final User currentUser;
    private final LobbyScreen lobbyScreen;
    private final GameLobby gameLobby;
    private boolean isDeletingLobby = false;

    @Inject
    @Named("playerClassInvoker")
    PlayerClassInvoker<String, PlayerClass> playerClassInvoker;


    public GameLobbyController(User currentUser, LobbyScreen lobbyScreen, GameLobby gameLobby) {
        GameLobbyControllerComponent gameLobbyControllerComponent = DaggerGameLobbyControllerComponent.create();
        gameLobbyControllerComponent.inject(this);
        this.currentUser = currentUser;

        this.lobbyScreen = lobbyScreen;
        this.gameLobby = gameLobby;
        lobbyScreen.setListener(this);
    }

    public void startGame() {
        // Only start game if there is a guest
        if (gameLobby.getGuest() == null) {
            lobbyScreen.showError("A second player is required to start the game.");
            return;
        }

        PlayerClass hostPlayerClass = playerClassInvoker.invoke(
                gameLobby
                .getCreator()
                .getPlayerClassName());
        PlayerClass guestPlayerClass = playerClassInvoker.invoke(
                gameLobby
                .getGuest()
                .getPlayerClassName());

        // Logic to start the game...
        System.out.println("Starting game for lobby: " + gameLobby.getTitle());
        ScreenController.transitionToGame(hostPlayerClass, guestPlayerClass);
    }

    public void joinLobby() {
        // Current user attempts to join the lobby
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
