package no.ntnu.dof.controller.network;

import no.ntnu.dof.model.User;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback, User user, String title);

    interface LobbyCreationCallback {
        void onSuccess(String lobbyId);
        void onFailure(Throwable throwable);
    }
}
