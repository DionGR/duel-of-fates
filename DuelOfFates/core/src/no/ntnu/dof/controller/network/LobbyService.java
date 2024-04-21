package no.ntnu.dof.controller.network;

import java.util.List;

import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.model.communication.User;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback, GameLobby gameLobby);

    void listenForLobbiesChanges(LobbyChangeListener listener);

    void listenForLobbyUpdate(String lobbyId, LobbyUpdateListener listener);

    void stopListeningForLobbyUpdates(String lobbyId);

    void initializeGame(LobbyUpdateCallback callback, String lobbyId, String gameId);

    void joinLobby(LobbyJoinCallback callback, GameLobby gameLobby, User user);

    void deleteLobby(String lobbyId, LobbyDeletionCallback callback);

    void guestExitLobby(LobbyExitCallback callback, GameLobby gameLobby);

    interface LobbyCreationCallback {
        void onSuccess(GameLobby gameLobby);

        void onFailure(Throwable throwable);
    }

    interface LobbyChangeListener {
        void onLobbiesUpdated(List<GameLobby> lobbies);
    }

    interface LobbyUpdateListener {
        void onLobbyUpdated(GameLobby updatedLobby);
    }

    interface LobbyJoinCallback {
        void onSuccess();

        void onFailure(Throwable throwable);
    }

    interface LobbyExitCallback {
        void onSuccess();

        void onFailure(Throwable throwable);
    }

    interface LobbyDeletionCallback {
        void onSuccess();

        void onFailure(Throwable throwable);
    }

    interface LobbyUpdateCallback {
        void onSuccess();

        void onFailure(Throwable throwable);
    }
}
