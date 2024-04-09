package no.ntnu.dof.controller;

import java.util.List;

import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.lobby.LobbiesScreen;

public class GameLobbiesController {

    private final DuelOfFates game;
    private final LobbiesScreen lobbiesScreen;

    public GameLobbiesController(DuelOfFates game, LobbiesScreen lobbiesScreen) {
        this.game = game;
        this.lobbiesScreen = lobbiesScreen;
        lobbiesScreen.setController(this);
        initializeListeners();
    }

    private void initializeListeners() {
        // Set up the Firebase listener for lobby changes
        ServiceLocator.getLobbyService().listenForLobbyChanges(this::updateLobbiesList);
    }

    public void updateLobbiesList(List<GameLobby> gameLobbies) {
        game.getGameLobbies().setLobbies(gameLobbies);
        lobbiesScreen.updateLobbiesList();
    }

    public void transitionToLobby(GameLobby lobby) {
        ScreenController.transitionToLobby(lobby);
    }

    public void createNewLobby(String title) {
        User currentUser = game.getCurrentUser();
        GameLobby newLobby = new GameLobby(currentUser, title);
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