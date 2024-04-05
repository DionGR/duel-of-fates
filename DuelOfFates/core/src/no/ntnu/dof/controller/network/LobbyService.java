package no.ntnu.dof.controller.network;

import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback, GameLobby gameLobby);

    interface LobbyCreationCallback {
        void onSuccess(GameLobby gameLobby);
        void onFailure(Throwable throwable);
    }
}
