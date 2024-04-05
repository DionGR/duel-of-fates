package no.ntnu.dof.controller.network;

import java.util.List;

import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback, GameLobby gameLobby);

    void listenForLobbyChanges(LobbyChangeListener listener);

    interface LobbyCreationCallback {
        void onSuccess(GameLobby gameLobby);
        void onFailure(Throwable throwable);
    }

    interface LobbyChangeListener {
        void onLobbiesUpdated(List<GameLobby> lobbies);
    }

}
