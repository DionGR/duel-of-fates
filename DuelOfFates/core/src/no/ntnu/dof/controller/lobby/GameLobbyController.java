package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import java.util.List;

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
        startListeningForLobbyUpdates();
    }

    public void startListeningForLobbyUpdates() {
        ServiceLocator.getLobbyService().listenForLobbyUpdate(gameLobby.getLobbyId(), updatedLobby -> {
            gameLobby.setGuest(updatedLobby.getGuest());
            String guestInfo = updatedLobby.getGuest() != null ? updatedLobby.getGuest().getEmail() : "<Available>";
            if (lobbyScreen != null) {
                Gdx.app.postRunnable(() -> lobbyScreen.updateGuestInfo(guestInfo));
            }
        });
    }

    public void stopListeningForLobbyUpdates() {
        ServiceLocator.getLobbyService().stopListeningForLobbyUpdates(gameLobby.getLobbyId());
        initializeListeners();
    }

    public void startGame(){
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

        // update firebase game state to "started"
        ServiceLocator.getLobbyService().updateLobbyState(new LobbyService.LobbyUpdateCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("LobbyUpdate", "Successfully updated the lobby state.");
                Gdx.app.postRunnable(() -> ScreenController.transitionToGame(hostPlayerClass, guestPlayerClass));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyUpdate", "Failed to update the lobby state.", throwable);
                lobbyScreen.showError("Failed to update the lobby state.");
            }
        }, gameLobby.getLobbyId(), "started");

        // Logic to start the game...
        System.out.println("Starting game for lobby: " + gameLobby.getTitle());
    }

    private void initializeListeners() {
        ServiceLocator.getLobbyService().listenForGameStart(this::updateLobbyState);
    }

    public void updateLobbyState(GameLobby gameLobby) {
        if (gameLobby.getGameState().equals("started")) {
            lobbyScreen.showError("Game is starting...");
            this.startGame();
        }

        ServiceLocator.getLobbyService().updateLobbyState(new LobbyService.LobbyUpdateCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("LobbyUpdate", "Successfully updated the lobby state.");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("LobbyUpdate", "Failed to update the lobby state.", throwable);
                lobbyScreen.showError("Failed to update the lobby state.");
            }
        }, gameLobby.getLobbyId(), gameLobby.getGameState());
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
