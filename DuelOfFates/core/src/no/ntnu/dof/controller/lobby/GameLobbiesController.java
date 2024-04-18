package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import java.util.List;

import lombok.Data;
import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.communication.GameLobbies;
import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.view.screen.lobby.LobbiesScreen;

@Data
public class GameLobbiesController implements LobbiesViewListener {
    private GameLobbies gameLobbies;
    private final LobbiesScreen lobbiesScreen;
    private User currentUser;

    public GameLobbiesController(User currentUser, LobbiesScreen lobbiesScreen) {
        this.currentUser = currentUser;
        this.gameLobbies = new GameLobbies();
        this.lobbiesScreen = lobbiesScreen;
        lobbiesScreen.setListener(this);
        lobbiesScreen.setGameLobbies(this.gameLobbies);
        initializeListeners();
    }

    private void initializeListeners() {
        // Set up the Firebase listener for lobby changes
        ServiceLocator.getLobbyService().listenForLobbiesChanges(this::updateLobbiesList);
    }

    public void updateLobbiesList(List<GameLobby> gameLobbies) {
        Gdx.app.log("GameLobbies", "Updating lobbies list with " + gameLobbies.size() + " lobbies.");
        this.gameLobbies.setLobbies(gameLobbies);
        lobbiesScreen.updateLobbiesList(this.gameLobbies);
    }

    public void transitionToLobby(GameLobby lobby) {
        ScreenController.transitionToLobby(lobby);
    }


    public void createNewLobby(String title) {
        GameLobby newLobby = new GameLobby(this.currentUser, title);
        ServiceLocator.getLobbyService().createLobby(new LobbyService.LobbyCreationCallback() {
            @Override
            public void onSuccess(GameLobby lobby) {
                Gdx.app.postRunnable(() -> transitionToLobby(lobby));
                Gdx.app.log("GameLobbies", "Created lobby with ID: " + lobby.getLobbyId());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Gdx.app.error("GameLobbies", "Failed to create lobby: " + throwable.getMessage());
            }
        }, newLobby);
    }
}