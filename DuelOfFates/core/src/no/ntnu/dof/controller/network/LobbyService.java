package no.ntnu.dof.controller.network;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback);

    interface LobbyCreationCallback {
        void onSuccess(String lobbyId);
        void onFailure(Throwable throwable);
    }
}
