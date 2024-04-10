package no.ntnu.dof.controller.lobby;

import java.util.List;

import lombok.Data;
import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.lobby.LobbiesScreen;

@Data
public class GameLobbiesController implements ILobbiesViewListener {
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
        ServiceLocator.getLobbyService().listenForLobbyChanges(this::updateLobbiesList);
    }

    public void updateLobbiesList(List<GameLobby> gameLobbies) {
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
            }

            @Override
            public void onFailure(Throwable throwable) {
                // TODO: Feedback
            }
        }, newLobby);
    }
}